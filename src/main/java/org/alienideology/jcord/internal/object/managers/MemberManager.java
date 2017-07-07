package org.alienideology.jcord.internal.object.managers;

import org.alienideology.jcord.handle.ISnowFlake;
import org.alienideology.jcord.handle.channel.IVoiceChannel;
import org.alienideology.jcord.handle.guild.IMember;
import org.alienideology.jcord.handle.guild.IRole;
import org.alienideology.jcord.handle.managers.IMemberManager;
import org.alienideology.jcord.handle.permission.Permission;
import org.alienideology.jcord.internal.exception.ErrorResponseException;
import org.alienideology.jcord.internal.exception.HigherHierarchyException;
import org.alienideology.jcord.internal.exception.HigherHierarchyException.HierarchyType;
import org.alienideology.jcord.internal.exception.HttpErrorException;
import org.alienideology.jcord.internal.exception.PermissionException;
import org.alienideology.jcord.internal.gateway.ErrorResponse;
import org.alienideology.jcord.internal.gateway.HttpCode;
import org.alienideology.jcord.internal.gateway.HttpPath;
import org.alienideology.jcord.internal.gateway.Requester;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.guild.Member;
import org.json.JSONObject;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author AlienIdeology
 */
public final class MemberManager implements IMemberManager {

    private Member member;
    private Guild guild;

    public MemberManager(Member member) {
        this.member = member;
        this.guild = (Guild) member.getGuild();
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    @Override
    public Member getMember() {
        return member;
    }

    @Override
    public void modifyNickname(String nickname) {
        if (nickname == null) nickname = "";
        checkPerm();
        if (!IMember.isValidNickname(nickname)) {
            throw new IllegalArgumentException("The nickname is not valid!");
        }

        // Validate nickname
        if (nickname.length() > IMember.NICKNAME_LENGTH_MAX) {
            throw new IllegalArgumentException("Nickname may not be longer than 32 letters!");
        }

        if (member.equals(guild.getSelfMember()) && !member.hasPermissions(true, Permission.CHANGE_NICKNAME)) {
            throw new PermissionException(Permission.ADMINISTRATOR, Permission.CHANGE_NICKNAME);
        } else if (!member.hasPermissions(true, Permission.MANAGE_NICKNAMES)) {
            throw new PermissionException(Permission.ADMINISTRATOR, Permission.MANAGE_NICKNAMES);
        }

        JSONObject json = new JSONObject().put("nick", nickname);

        /* Modify Self Nickname */
        if (member.equals(guild.getSelfMember())) {
            new Requester((IdentityImpl) getIdentity(), HttpPath.Guild.MODIFY_CURRENT_USER_NICK).request(guild.getId())
                    .updateRequestWithBody(request -> request.body(json))
                    .performRequest();

        /* Modify Other Nickname */
        } else {
            modifyMember(json);
        }
    }

    @Override
    public void modifyRoles(Collection<IRole> add, Collection<IRole> remove) {
        Set<IRole> roles = new HashSet<>(member.getRoles());
        roles.addAll(add);
        roles.removeAll(remove);
        modifyRoles(roles);
    }

    @Override
    public void modifyRoles(Collection<IRole> modified) {
        for (IRole role : modified) {
            if (role == null || !role.getGuild().equals(guild)) {
                throw new ErrorResponseException(ErrorResponse.UNKNOWN_ROLE);
            }
        }

        checkPerm();
        if (!member.hasPermissions(true, Permission.MANAGE_ROLES)) {
            throw new PermissionException(Permission.MANAGE_ROLES);
        }

        List<String> roleIds = modified.stream().map(ISnowFlake::getId).collect(Collectors.toList());
        try {
            modifyMember(new JSONObject().put("roles", roleIds));
        } catch (HttpErrorException ex) {
            if (ex.getCode().equals(HttpCode.BAD_REQUEST)) {
                throw new IllegalArgumentException("Modifying member roles without giving any changes!");
            } else {
                throw ex;
            }
        }
    }

    @Override
    public void addRoles(IRole... roles) {
        List<IRole> allRoles = new ArrayList<>(member.getRoles());
        allRoles.addAll(Arrays.asList(roles));
        modifyRoles(allRoles);
    }

    @Override
    public void addRoles(Collection<IRole> roles) {
        List<IRole> allRoles = new ArrayList<>(member.getRoles());
        allRoles.addAll(roles);
        modifyRoles(allRoles);
    }

    @Override
    public void removeRoles(IRole... roles) {
        List<IRole> allRoles = new ArrayList<>(member.getRoles());
        allRoles.removeAll(Arrays.asList(roles));
        modifyRoles(allRoles);
    }

    @Override
    public void removeRoles(Collection<IRole> roles) {
        List<IRole> allRoles = new ArrayList<>(member.getRoles());
        allRoles.removeAll(roles);
        modifyRoles(allRoles);
    }

    @Override
    public void moveToVoiceChannel(IVoiceChannel channel) {
        moveToVoiceChannel(channel.getId());
    }

    @Override
    public void moveToVoiceChannel(String channelId) {
        // TODO: Check if the member is in a voice channel
        IVoiceChannel channel = guild.getVoiceChannel(channelId);
        if (channel == null) {
            throw new ErrorResponseException(ErrorResponse.UNKNOWN_CHANNEL);
        }
        if (member.hasPermissions(true, Permission.MOVE_MEMBERS)) {
            throw new PermissionException(Permission.ADMINISTRATOR, Permission.MOVE_MEMBERS);
        }
        if (!channel.hasPermission(getGuild().getSelfMember(), Permission.ADMINISTRATOR, Permission.CONNECT)) {
            throw new PermissionException(Permission.ADMINISTRATOR, Permission.CONNECT);
        }

        modifyMember(new JSONObject().put("channel_id", channelId));
    }

    @Override
    public void mute(boolean mute) {
        checkPerm();
        if (!member.hasPermissions(true, Permission.MUTE_MEMBERS)) {
            throw new PermissionException(Permission.MUTE_MEMBERS);
        }

        modifyMember(new JSONObject().put("mute", mute));
    }

    @Override
    public void deafen(boolean deafen) {
        checkPerm();
        if (!member.hasPermissions(true, Permission.DEAFEN_MEMBERS)) {
            throw new PermissionException(Permission.DEAFEN_MEMBERS);
        }

        modifyMember(new JSONObject().put("deaf", deafen));
    }

    private void modifyMember(JSONObject json) {
        try {
            new Requester((IdentityImpl) getIdentity(), HttpPath.Guild.MODIFY_GUILD_MEMBER).request(guild.getId(), member.getId())
                    .updateRequestWithBody(request -> request.body(json))
                    .performRequest();
        } catch (HttpErrorException ex) {
            if (ex.isPermissionException()) {
                throw new PermissionException();
            } else {
                throw ex;
            }
        }
    }

    private void checkPerm() {
        // Checks hierarchy
        if (member.isOwner() && !guild.getSelfMember().isOwner()) {
            throw new HigherHierarchyException(HierarchyType.OWNER);
        }
        if (!guild.getSelfMember().canModify(member)) {
            throw new HigherHierarchyException(HierarchyType.MEMBER);
        }
    }

}
