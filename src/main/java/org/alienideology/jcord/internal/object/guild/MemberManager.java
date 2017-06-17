package org.alienideology.jcord.internal.object.guild;

import org.alienideology.jcord.handle.ISnowFlake;
import org.alienideology.jcord.handle.Permission;
import org.alienideology.jcord.handle.guild.IMemberManager;
import org.alienideology.jcord.handle.guild.IRole;
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
import org.json.JSONObject;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author AlienIdeology
 */
// TODO: MemberManager#moveMember(IVoiceChannel)
public class MemberManager implements IMemberManager {

    private Member member;
    private Guild guild;
    private Member self;

    public final static int NICKNAME_LENGTH = 32;

    public MemberManager(Member member) {
        this.member = member;
        this.guild = (Guild) member.getGuild();
        this.self = (Member) guild.getSelfMember();
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

        // Validate nickname
        if (nickname.length() > NICKNAME_LENGTH) {
            throw new IllegalArgumentException("Nickname may not be longer than 32 letters!");
        }

        JSONObject json = new JSONObject().put("nick", nickname);

        /* Modify Self Nickname */
        if (guild.getSelfMember().equals(member)) {
            try {
                new Requester((IdentityImpl) getIdentity(), HttpPath.Guild.MODIFY_CURRENT_USER_NICK).request(guild.getId())
                        .updateRequestWithBody(request -> request.body(json))
                        .performRequest();
            } catch (HttpErrorException ex) {
                if (ex.isPermissionException()) {
                    throw new PermissionException(Permission.ADMINISTRATOR, Permission.CHANGE_NICKNAME);
                } else {
                    throw ex;
                }
            }

        /* Modify Other Nickname */
        } else {
            try {
                modifyMember(json);
            } catch (PermissionException ex) {
                throw new PermissionException(Permission.ADMINISTRATOR, Permission.MANAGE_NICKNAMES); // Change other's nickname
            }
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

        List<String> roleIds = modified.stream().map(ISnowFlake::getId).collect(Collectors.toList());
        try {
            modifyMember(new JSONObject().put("roles", roleIds));
        } catch (PermissionException ex) {
            throw new PermissionException(Permission.MANAGE_ROLES);
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
    public void mute(boolean mute) {
        checkPerm();

        try {
            modifyMember(new JSONObject().put("mute", mute));
        } catch (PermissionException ex) {
            throw new PermissionException(Permission.MUTE_MEMBERS);
        }

    }

    @Override
    public void deafen(boolean deafen) {
        checkPerm();
        try {
            modifyMember(new JSONObject().put("deaf", deafen));
        } catch (PermissionException ex) {
            throw new PermissionException(Permission.DEAFEN_MEMBERS);
        }
    }

    // TODO: Move members

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
        if (member.isOwner() && !self.isOwner()) {
            throw new HigherHierarchyException(HierarchyType.OWNER);
        }
        if (!self.canModify(member)) {
            throw new HigherHierarchyException(HierarchyType.MEMBER);
        }
    }

}
