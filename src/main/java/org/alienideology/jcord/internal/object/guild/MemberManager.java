package org.alienideology.jcord.internal.object.guild;

import org.alienideology.jcord.event.ExceptionEvent;
import org.alienideology.jcord.handle.Permission;
import org.alienideology.jcord.handle.guild.IMember;
import org.alienideology.jcord.handle.guild.IMemberManager;
import org.alienideology.jcord.handle.guild.IRole;
import org.alienideology.jcord.internal.exception.ErrorResponseException;
import org.alienideology.jcord.internal.exception.HttpErrorException;
import org.alienideology.jcord.internal.exception.PermissionException;
import org.alienideology.jcord.internal.gateway.ErrorResponse;
import org.alienideology.jcord.internal.gateway.HttpCode;
import org.alienideology.jcord.internal.gateway.HttpPath;
import org.alienideology.jcord.internal.gateway.Requester;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.json.JSONObject;

/**
 * @author AlienIdeology
 */
// TODO: MemberManager#moveMember(IVoiceChannel)
public class MemberManager implements IMemberManager {

    private Guild guild;

    public final static int NICKNAME_LENGTH = 32;

    public MemberManager(Guild guild) {
        this.guild = guild;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    @Override
    public void modifySelfNickname(String newNickname) {
        modifyMemberNickname(getIdentity().getSelf().getId(), newNickname);
    }

    @Override
    public void modifyMemberNickname(IMember member, String newNickname) {
        if (member == null) return;
        modifyMemberNickname(member.getId(), newNickname);
    }

    @Override
    public void modifyMemberNickname(String memberId, String newNickname) {
        if (newNickname == null) newNickname = "";
        if (guild.getMember(memberId) == null) {
            throw new ErrorResponseException(ErrorResponse.UNKNOWN_MEMBER, "Unknown member to modify nickname! ID: "+memberId);
        }
        if (newNickname.length() > NICKNAME_LENGTH) {
            throw new IllegalArgumentException("Nickname may not be longer than 32 letters!");
        }

        JSONObject json = new JSONObject().put("nick", newNickname);

        /* Modify Self Nickname */
        if (getIdentity().getSelf().getId().equals(memberId)) {
            try {
                new Requester((IdentityImpl) getIdentity(), HttpPath.Guild.MODIFY_CURRENT_USER_NICK).request(guild.getId())
                        .updateRequestWithBody(request -> request.body(json))
                        .performRequest();
            } catch (HttpErrorException ex) {
                if (ex.getCode().equals(HttpCode.FORBIDDEN)) {
                    throw new PermissionException(Permission.ADMINISTRATOR, Permission.CHANGE_NICKNAME);
                } else {
                    throw ex;
                }
            }

        /* Modify Other Nickname */
        } else {
            try {
                modifyMember(memberId, json);
            } catch (PermissionException ex) {
                if (memberId.equals(guild.getOwner().getId())) { // Modify owner's nickname
                    throw new PermissionException("Cannot modify the owner's nickname!");
                } else { // Change other's nickname
                    throw new PermissionException(Permission.ADMINISTRATOR, Permission.MANAGE_NICKNAMES);
                }
            }
        }
    }

    @Override
    public void addRoleToMember(IMember member, IRole role) {
        addRoleToMember(member.getId(), role.getId());
    }

    @Override
    public void addRoleToMember(String memberId, IRole role) {
        addRoleToMember(memberId, role.getId());
    }

    @Override
    public void addRoleToMember(IMember member, String roleId) {
        addRoleToMember(member.getId(), roleId);
    }

    @Override
    public void addRoleToMember(String memberId, String roleId) {
        IMember member = guild.getMember(memberId);
        if (member == null) {
            throw new ErrorResponseException(ErrorResponse.UNKNOWN_MEMBER);
        }
        if (guild.getRole(roleId) == null) {
            throw new ErrorResponseException(ErrorResponse.UNKNOWN_ROLE);
        }
        if (member.getRole(roleId) != null) {
            throw new IllegalArgumentException("Cannot add a role from a member that already have the role!");
        }

        // Fires guild member update event
        try {
            new Requester((IdentityImpl) getIdentity(), HttpPath.Guild.ADD_GUILD_MEMBER_ROLE).request(guild.getId(), memberId, roleId)
                .performRequest();
        } catch (HttpErrorException ex) {
            if (ex.getCode().equals(HttpCode.FORBIDDEN)) {
                throw new PermissionException(Permission.MANAGE_ROLES);
            } else {
                throw ex;
            }
        }
    }

    @Override
    public void removeRoleFromMember(IMember member, IRole role) {
        removeRoleFromMember(member.getId(), role.getId());
    }

    @Override
    public void removeRoleFromMember(String memberId, IRole role) {
        removeRoleFromMember(memberId, role.getId());
    }

    @Override
    public void removeRoleFromMember(IMember member, String roleId) {
        removeRoleFromMember(member.getId(), roleId);
    }

    @Override
    public void removeRoleFromMember(String memberId, String roleId) {
        IMember member = guild.getMember(memberId);
        if (member == null) {
            throw new ErrorResponseException(ErrorResponse.UNKNOWN_MEMBER);
        }
        if (guild.getRole(roleId) == null) {
            throw new ErrorResponseException(ErrorResponse.UNKNOWN_ROLE);
        }
        if (member.getRole(roleId) == null) {
            throw new IllegalArgumentException("Cannot remove a role from a member that does not have the role!");
        }

        // Fires guild member update event
        try {
            new Requester((IdentityImpl) getIdentity(), HttpPath.Guild.REMOVE_GUILD_MEMBER_ROLE).request(guild.getId(), memberId, roleId)
                    .performRequest();
        } catch (HttpErrorException ex) {
            if (ex.getCode().equals(HttpCode.FORBIDDEN)) {
                throw new PermissionException(Permission.MANAGE_ROLES);
            } else {
                throw ex;
            }
        }
    }

    @Override
    public void muteMember(IMember member) {
        if (member == null) return;
        muteMember(member.getId());
    }

    @Override
    public void muteMember(String memberId) {
        if (guild.getMember(memberId) == null) {
            throw new ErrorResponseException(ErrorResponse.UNKNOWN_MEMBER);
        }

        try {
            modifyMember(memberId, new JSONObject().put("mute", true));
        } catch (PermissionException ex) {
            if (memberId.equals(guild.getOwner().getId())) { // Mute server owner
                throw new PermissionException("Cannot mute the server owner!");
            } else {
                throw new PermissionException(Permission.MUTE_MEMBERS);
            }
        }
    }

    @Override
    public void unmuteMember(IMember member) {
        if (member == null) return;
        unmuteMember(member.getId());
    }

    @Override
    public void unmuteMember(String memberId) {
        if (guild.getMember(memberId) == null) {
            throw new ErrorResponseException(ErrorResponse.UNKNOWN_MEMBER);
        }
        try {
            modifyMember(memberId, new JSONObject().put("mute", false));
        } catch (PermissionException ex) {
            if (memberId.equals(guild.getOwner().getId())) { // Unmute server owner
                throw new PermissionException("Cannot unmute the server owner!");
            } else {
                throw new PermissionException(Permission.MUTE_MEMBERS);
            }
        }
    }

    @Override
    public void deafenMember(IMember member) {
        if (member == null) return;
        deafenMember(member.getId());
    }

    @Override
    public void deafenMember(String memberId) {
        if (guild.getMember(memberId) == null) {
            throw new ErrorResponseException(ErrorResponse.UNKNOWN_MEMBER);
        }

        try {
            modifyMember(memberId, new JSONObject().put("deaf", true));
        } catch (PermissionException ex) {
            if (memberId.equals(guild.getOwner().getId())) { // Deafen server owner
                throw new PermissionException("Cannot Deafen the server owner!");
            } else {
                throw new PermissionException(Permission.DEAFEN_MEMBERS);
            }
        }
    }

    @Override
    public void undeafenMember(IMember member) {
        if (member == null) return;
        undeafenMember(member.getId());
    }

    @Override
    public void undeafenMember(String memberId) {
        if (guild.getMember(memberId) == null) {
            throw new ErrorResponseException(ErrorResponse.UNKNOWN_MEMBER);
        }
        try {
            modifyMember(memberId, new JSONObject().put("deaf", false));
        } catch (PermissionException ex) {
            if (memberId.equals(guild.getOwner().getId())) { // Undeafen server owner
                throw new PermissionException("Cannot Undeafen the server owner!");
            } else {
                throw new PermissionException(Permission.DEAFEN_MEMBERS);
            }
        }
    }

    private void modifyMember(String memberId, JSONObject json) {
        try {
            new Requester((IdentityImpl) getIdentity(), HttpPath.Guild.MODIFY_GUILD_MEMBER).request(guild.getId(), memberId)
                    .updateRequestWithBody(request -> request.body(json))
                    .performRequest();
        } catch (HttpErrorException ex) {
            if (ex.getCode().equals(HttpCode.FORBIDDEN)) {
                throw new PermissionException();
            } else {
                throw ex;
            }
        }
    }

}
