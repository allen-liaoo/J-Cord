package org.alienideology.jcord.internal.object.guild;

import com.sun.istack.internal.Nullable;
import org.alienideology.jcord.handle.Permission;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IMember;
import org.alienideology.jcord.handle.guild.IMemberManager;
import org.alienideology.jcord.handle.guild.IRole;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.*;
import org.alienideology.jcord.internal.object.user.User;

import java.time.OffsetDateTime;
import java.util.*;

/**
 * @author AlienIdeology
 */
public final class Member extends DiscordObject implements IMember {

    private final Guild guild;
    private final User user;
    private String nickname;
    private OffsetDateTime joinedDate;

    private List<Role> roles;
    private List<Permission> permissions;
    private boolean isDeafened;
    private boolean isMuted;

    public Member(IdentityImpl identity, Guild guild, User user, String nickname, String joinedDate, List<Role> roles, boolean isDeafened, boolean isMuted) {
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
    }

    @Override
    public boolean hasAllPermissions (Permission... permissions) {
        for (Permission perm : permissions) {
            if (!this.permissions.contains(perm))
                return false;
        }

        return true;
    }

    @Override
    public boolean hasPermissions (boolean checkAdmin, Permission... permissions) {
        if (checkAdmin && hasPermissions(Permission.ADMINISTRATOR)) return true;
        return hasPermissions(permissions);
    }

    @Override
    @Deprecated
    public boolean hasPermissions (Permission... permissions) {
        for (Permission perm : permissions) {
            if (this.permissions.contains(perm))
                return true;
        }
        return false;
    }

    private List<Permission> initPermissions() {
        Set<Permission> allPerms = new TreeSet<>();
        for (Role role : roles) {
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
        return "ID: "+user.getId()+"\tNickName: "+nickname;
    }

}
