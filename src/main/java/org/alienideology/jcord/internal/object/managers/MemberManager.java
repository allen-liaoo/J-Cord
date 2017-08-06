package org.alienideology.jcord.internal.object.managers;

import org.alienideology.jcord.handle.ISnowFlake;
import org.alienideology.jcord.handle.audit.AuditAction;
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
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.guild.Member;
import org.alienideology.jcord.internal.rest.ErrorResponse;
import org.alienideology.jcord.internal.rest.HttpCode;
import org.alienideology.jcord.internal.rest.HttpPath;
import org.alienideology.jcord.internal.rest.Requester;
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
    public AuditAction<Void> modifyNickname(String nickname) {
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
            return new AuditAction<Void>((IdentityImpl) getIdentity(), HttpPath.Guild.MODIFY_CURRENT_USER_NICK, guild.getId()) {
                @Override
                protected Void request(Requester requester) {
                    requester.updateRequestWithBody(request -> request.body(json)).performRequest();
                    return null;
                }
            };

        /* Modify Other Nickname */
        } else {
            return modifyMember(json);
        }
    }

    @Override
    public AuditAction<Void> modifyRoles(Collection<IRole> add, Collection<IRole> remove) {
        Set<IRole> roles = new HashSet<>(member.getRoles());
        roles.addAll(add);
        roles.removeAll(remove);
        return modifyRoles(roles);
    }

    @Override
    public AuditAction<Void> modifyRoles(Collection<IRole> modified) {
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
            return modifyMember(new JSONObject().put("roles", roleIds));
        } catch (HttpErrorException ex) {
            if (ex.getCode().equals(HttpCode.BAD_REQUEST)) {
                throw new IllegalArgumentException("Modifying member roles without giving any changes!");
            } else {
                throw ex;
            }
        }
    }

    @Override
    public AuditAction<Void> addRoles(IRole... roles) {
        return addRoles(Arrays.asList(roles));
    }

    @Override
    public AuditAction<Void> addRoles(Collection<IRole> roles) {
        List<IRole> allRoles = new ArrayList<>(member.getRoles());
        allRoles.addAll(roles);
        return modifyRoles(allRoles);
    }

    @Override
    public AuditAction<Void> removeRoles(IRole... roles) {
        return removeRoles(Arrays.asList(roles));
    }

    @Override
    public AuditAction<Void> removeRoles(Collection<IRole> roles) {
        List<IRole> allRoles = new ArrayList<>(member.getRoles());
        allRoles.removeAll(roles);
        return modifyRoles(allRoles);
    }

    @Override
    public AuditAction<Void> moveToVoiceChannel(IVoiceChannel channel) {
        return moveToVoiceChannel(channel.getId());
    }

    @Override
    public AuditAction<Void> moveToVoiceChannel(String channelId) {
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

        return modifyMember(new JSONObject().put("channel_id", channelId));
    }

    @Override
    public AuditAction<Void> mute(boolean mute) {
        checkPerm();
        if (!member.hasPermissions(true, Permission.MUTE_MEMBERS)) {
            throw new PermissionException(Permission.MUTE_MEMBERS);
        }

        return modifyMember(new JSONObject().put("mute", mute));
    }

    @Override
    public AuditAction<Void> deafen(boolean deafen) {
        checkPerm();
        if (!member.hasPermissions(true, Permission.DEAFEN_MEMBERS)) {
            throw new PermissionException(Permission.DEAFEN_MEMBERS);
        }

        return modifyMember(new JSONObject().put("deaf", deafen));
    }

    private AuditAction<Void> modifyMember(JSONObject json) {
        try {
            return new AuditAction<Void>((IdentityImpl) getIdentity(), HttpPath.Guild.MODIFY_GUILD_MEMBER, guild.getId(), member.getId()) {
                @Override
                protected Void request(Requester requester) {
                    requester.updateRequestWithBody(request -> request.body(json)).performRequest();
                    return null;
                }
            };
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
