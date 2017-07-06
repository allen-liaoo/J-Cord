package org.alienideology.jcord.internal.object.guild;

import org.jetbrains.annotations.Nullable;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IMember;
import org.alienideology.jcord.handle.guild.IRole;
import org.alienideology.jcord.handle.managers.IMemberManager;
import org.alienideology.jcord.handle.permission.Permission;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.object.DiscordObject;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.managers.MemberManager;
import org.alienideology.jcord.internal.object.user.User;

import java.time.OffsetDateTime;
import java.util.*;

/**
 * @author AlienIdeology
 */
public final class Member extends DiscordObject implements IMember {

    private final Guild guild;

    private MemberManager memberManager;

    private final User user;
    private String nickname;
    private OffsetDateTime joinedDate;

    private List<IRole> roles;
    private List<Permission> permissions;
    private boolean isDeafened;
    private boolean isMuted;

    public Member(IdentityImpl identity, Guild guild, User user, String nickname, String joinedDate, List<IRole> roles, boolean isDeafened, boolean isMuted) {
        super(identity);
        this.guild = guild;
        this.user = user;
        this.nickname = nickname;
        this.joinedDate = OffsetDateTime.parse(joinedDate);
        this.roles = roles;
        this.roles.sort((o1, o2) -> -1 * o1.compareTo(o2));
        this.permissions = initPermissions();
        this.isDeafened = isDeafened;
        this.isMuted = isMuted;
        this.memberManager = new MemberManager(this);
    }

    @Override
    public IMemberManager getMemberManager() {
        return memberManager;
    }

    @Override
    public boolean hasAllPermissions(Permission... permissions) {
        for (Permission perm : permissions) {
            if (!this.permissions.contains(perm))
                return false;
        }
        return true;
    }

    @Override
    public boolean hasPermissions(boolean checkAdmin, Collection<Permission> permissions) {
        if (checkAdmin) {
            if (this.permissions.contains(Permission.ADMINISTRATOR) || isOwner()) return true;
        }
        for (Permission perm : permissions) {
            if (this.permissions.contains(perm))
                return true;
        }
        return false;
    }

    public List<Permission> initPermissions() {
        Set<Permission> allPerms = new TreeSet<>();
        for (IRole role : roles) {
            allPerms.addAll(role.getPermissions());
        }
        List<Permission> permissions = new ArrayList<>();
        permissions.addAll(allPerms);
        return permissions;
    }

    @Override
    public IGuild getGuild() {
        return guild;
    }

    @Override
    public IUser getUser() {
        return user;
    }

    @Override
    @Nullable
    public String getNickname() {
        return nickname;
    }

    @Override
    public OffsetDateTime getJoinedDate() {
        return joinedDate;
    }

    @Override
    public IRole getHighestRole() {
        return roles.isEmpty() ? null : roles.get(0);
    }

    @Override
    @Nullable
    public IRole getRole(String id) {
        for (IRole role : roles) {
            if (role.getId().equals(id))
                return role;
        }
        return null;
    }

    @Override
    public List<IRole> getRoles() {
        return Collections.unmodifiableList(roles);
    }

    @Override
    public List<Permission> getPermissions() {
        return Collections.unmodifiableList(permissions);
    }

    @Override
    public boolean isDeafened() {
        return isDeafened;
    }

    @Override
    public boolean isMuted() {
        return isMuted;
    }

    @Override
    public String getId() {
        return user.getId();
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Member) && user.equals(((Member) obj).getUser()) && guild.equals(((Member) obj).getGuild());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + guild.hashCode();
        result = 31 * result + user.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Member{" +
                "guild=" + guild +
                ", user=" + user +
                '}';
    }

    public Member setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public Member setRoles(List<IRole> roles) {
        this.roles = roles;
        return this;
    }

    public Member addRole(Role role) {
        if (!roles.contains(role))
            roles.add(role);
        return this;
    }

    public Member removeRoles(Collection<IRole> roles) {
        this.roles.removeAll(roles);
        return this;
    }

}
