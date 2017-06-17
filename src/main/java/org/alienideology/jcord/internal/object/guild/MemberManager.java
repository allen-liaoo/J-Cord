package org.alienideology.jcord.internal.object.guild;

import org.alienideology.jcord.handle.ISnowFlake;
import org.alienideology.jcord.handle.Permission;
import org.alienideology.jcord.handle.guild.IMember;
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

    private Guild guild;
    private Member self;

    public final static int NICKNAME_LENGTH = 32;

    public MemberManager(Guild guild) {
        this.guild = guild;
        this.self = (Member) guild.getSelfMember();
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
        IMember member = guild.getMember(memberId);
        if (newNickname == null) newNickname = "";

        // Validate member
        if (member == null) {
            throw new ErrorResponseException(ErrorResponse.UNKNOWN_MEMBER, "Unknown member to modify nickname! ID: "+memberId);
        }

        // Checks hierarchy
        if (member.isOwner() && !self.isOwner()) {
            throw new HigherHierarchyException(HierarchyType.OWNER);
        }
        if (!self.canModify(member)) {
            throw new HigherHierarchyException(HierarchyType.MEMBER);
        }

        // Validate nickname
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
                if (ex.isPermissionException()) {
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
                throw new PermissionException(Permission.ADMINISTRATOR, Permission.MANAGE_NICKNAMES); // Change other's nickname
            }
        }
    }

    @Override
    public void modifyMemberRoles(IMember member, Collection<IRole> add, Collection<IRole> remove) {
        Set<IRole> roles = new HashSet<>(member.getRoles());
        roles.addAll(add);
        roles.removeAll(remove);
        modifyMemberRoles(member, roles);
    }

    @Override
    public void modifyMemberRoles(IMember member, Collection<IRole> modified) {
        if (member == null || !member.getGuild().equals(guild)) {
            throw new ErrorResponseException(ErrorResponse.UNKNOWN_MEMBER);
        }
        for (IRole role : modified) {
            if (role == null || !role.getGuild().equals(guild)) {
                throw new ErrorResponseException(ErrorResponse.UNKNOWN_ROLE);
            }
        }

        // Checks hierarchy
        if (member.isOwner() && !self.isOwner()) {
            throw new HigherHierarchyException(HierarchyType.OWNER);
        }
        if (!guild.getSelfMember().canModify(member)) {
            throw new HigherHierarchyException(HierarchyType.MEMBER);
        }

        List<String> roleIds = modified.stream().map(ISnowFlake::getId).collect(Collectors.toList());
        try {
            modifyMember(member.getId(), new JSONObject().put("roles", roleIds));
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
    public void addRolesToMember(IMember member, IRole... roles) {
        List<IRole> allRoles = new ArrayList<>(member.getRoles());
        allRoles.addAll(Arrays.asList(roles));
        modifyMemberRoles(member, allRoles);
    }

    @Override
    public void addRolesToMember(IMember member, Collection<IRole> roles) {
        List<IRole> allRoles = new ArrayList<>(member.getRoles());
        allRoles.addAll(roles);
        modifyMemberRoles(member, allRoles);
    }

    @Override
    public void removeRolesFromMember(IMember member, IRole... roles) {
        List<IRole> allRoles = new ArrayList<>(member.getRoles());
        allRoles.removeAll(Arrays.asList(roles));
        modifyMemberRoles(member, allRoles);
    }

    @Override
    public void removeRolesFromMember(IMember member, Collection<IRole> roles) {
        List<IRole> allRoles = new ArrayList<>(member.getRoles());
        allRoles.removeAll(roles);
        modifyMemberRoles(member, allRoles);
    }

    @Override
    public void muteMember(IMember member) {
        if (member == null) return;
        muteMember(member.getId());
    }

    @Override
    public void muteMember(String memberId) {
        IMember member = guild.getMember(memberId);
        // Validate member
        if (member == null) {
            throw new ErrorResponseException(ErrorResponse.UNKNOWN_MEMBER, "Unknown member to modify nickname! ID: "+memberId);
        }

        // Checks hierarchy
        if (member.isOwner() && !self.isOwner()) {
            throw new HigherHierarchyException(HierarchyType.OWNER);
        }
        if (!self.canModify(member)) {
            throw new HigherHierarchyException(HierarchyType.MEMBER);
        }

        try {
            modifyMember(memberId, new JSONObject().put("mute", true));
        } catch (PermissionException ex) {
            throw new PermissionException(Permission.MUTE_MEMBERS);
        }
    }

    @Override
    public void unmuteMember(IMember member) {
        if (member == null) return;
        unmuteMember(member.getId());
    }

    @Override
    public void unmuteMember(String memberId) {
        IMember member = guild.getMember(memberId);
        // Validate member
        if (member == null) {
            throw new ErrorResponseException(ErrorResponse.UNKNOWN_MEMBER, "Unknown member to modify nickname! ID: "+memberId);
        }

        // Checks hierarchy
        if (member.isOwner() && !self.isOwner()) {
            throw new HigherHierarchyException(HierarchyType.OWNER);
        }
        if (!self.canModify(member)) {
            throw new HigherHierarchyException(HierarchyType.MEMBER);
        }

        try {
            modifyMember(memberId, new JSONObject().put("mute", false));
        } catch (PermissionException ex) {
            throw new PermissionException(Permission.MUTE_MEMBERS);
        }
    }

    @Override
    public void deafenMember(IMember member) {
        if (member == null) return;
        deafenMember(member.getId());
    }

    @Override
    public void deafenMember(String memberId) {
        IMember member = guild.getMember(memberId);
        // Validate member
        if (member == null) {
            throw new ErrorResponseException(ErrorResponse.UNKNOWN_MEMBER, "Unknown member to modify nickname! ID: "+memberId);
        }

        // Checks hierarchy
        if (member.isOwner() && !self.isOwner()) {
            throw new HigherHierarchyException(HierarchyType.OWNER);
        }
        if (!self.canModify(member)) {
            throw new HigherHierarchyException(HierarchyType.MEMBER);
        }

        try {
            modifyMember(memberId, new JSONObject().put("deaf", true));
        } catch (PermissionException ex) {
            throw new PermissionException(Permission.DEAFEN_MEMBERS);
        }
    }

    @Override
    public void undeafenMember(IMember member) {
        if (member == null) return;
        undeafenMember(member.getId());
    }

    @Override
    public void undeafenMember(String memberId) {
        IMember member = guild.getMember(memberId);
        // Validate member
        if (member == null) {
            throw new ErrorResponseException(ErrorResponse.UNKNOWN_MEMBER, "Unknown member to modify nickname! ID: "+memberId);
        }

        // Checks hierarchy
        if (member.isOwner() && !self.isOwner()) {
            throw new HigherHierarchyException(HierarchyType.OWNER);
        }
        if (!self.canModify(member)) {
            throw new HigherHierarchyException(HierarchyType.MEMBER);
        }

        try {
            modifyMember(memberId, new JSONObject().put("deaf", false));
        } catch (PermissionException ex) {
            throw new PermissionException(Permission.DEAFEN_MEMBERS);
        }
    }

    private void modifyMember(String memberId, JSONObject json) {
        try {
            new Requester((IdentityImpl) getIdentity(), HttpPath.Guild.MODIFY_GUILD_MEMBER).request(guild.getId(), memberId)
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

}
