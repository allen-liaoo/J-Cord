package org.alienideology.jcord.internal.object.managers;

import org.alienideology.jcord.handle.audit.AuditAction;
import org.alienideology.jcord.handle.guild.IRole;
import org.alienideology.jcord.handle.managers.IMemberManager;
import org.alienideology.jcord.internal.exception.HigherHierarchyException;
import org.alienideology.jcord.internal.exception.HigherHierarchyException.HierarchyType;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.guild.Member;
import org.alienideology.jcord.internal.rest.HttpPath;
import org.alienideology.jcord.internal.rest.Requester;
import org.json.JSONObject;

import java.util.*;

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
        /* Modify Self Nickname */
        if (member.equals(guild.getSelfMember())) {
            if (nickname == null) nickname = "";

            // Borrow the attribute to validate the nickname
            // Automatically throw errors
            getMember().getModifier().getNicknameAttr().checkValue(nickname);

            // Hierarchy checks is at IMemberModifier#modify, so we need to do it manually
            if (member.isOwner() && !guild.getSelfMember().isOwner()) {
                throw new HigherHierarchyException(HierarchyType.OWNER);
            }
            if (!guild.getSelfMember().canModify(member)) {
                throw new HigherHierarchyException(HierarchyType.MEMBER);
            }

            JSONObject json = new JSONObject().put("nick", nickname);

            return new AuditAction<Void>((IdentityImpl) getIdentity(), HttpPath.Guild.MODIFY_CURRENT_USER_NICK, guild.getId()) {
                @Override
                protected Void request(Requester requester) {
                    requester.updateRequestWithBody(request -> request.body(json)).performRequest();
                    return null;
                }
            };

        /* Modify Other Nickname */
        } else {
            return getMember().getModifier().nickname(nickname).modify();
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

}
