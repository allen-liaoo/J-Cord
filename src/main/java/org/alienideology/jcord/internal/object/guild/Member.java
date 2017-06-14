package org.alienideology.jcord.internal.object.guild;

import org.alienideology.jcord.internal.Identity;
import org.alienideology.jcord.internal.object.*;
import org.alienideology.jcord.internal.object.user.User;

import java.time.OffsetDateTime;
import java.util.*;

/**
 * Member - A user representation in a guild.
 * @author AlienIdeology
 */
public class Member extends DiscordObject implements SnowFlake, Mention {

    private final Guild guild;
    private final User user;
    private String nickname;
    private OffsetDateTime joinedDate;

    private List<Role> roles;
    private List<Permission> permissions;
    private boolean isDeafened;
    private boolean isMuted;

    public Member(Identity identity, Guild guild, User user, String nickname, String joinedDate, List<Role> roles, boolean isDeafened, boolean isMuted) {
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

    /**
     * Check if this member have all the given permissions
     * @param permissions The varargs of permission enums to be checked
     * @return True if the member have all given permissions
     */
    public boolean hasAllPermissions (Permission... permissions) {
        for (Permission perm : permissions) {
            if (!this.permissions.contains(perm))
                return false;
        }

        return true;
    }

    /**
     * Check if this member have one of the given permissions.
     * @param checkAdmin If true, returns true if the member have administrator permission.
     * @param permissions The varargs of permission enums to be checked.
     * @return True if the member have one of the given permissions.
     */
    public boolean hasPermissions (boolean checkAdmin, Permission... permissions) {
        if (checkAdmin && hasPermissions(Permission.ADMINISTRATOR)) return true;
        return hasPermissions(permissions);
    }

    /**
     * Check if this member have one of the given permissions
     * @deprecated #hasPermissions(boolean, Permission...) To check with the member having administrator permission.
     * @see #hasAllPermissions(Permission...) To check if this member have all the permissions.
     * @param permissions The varargs of permission enums to be checked
     * @return True if the member have one of the given permissions
     */
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

    public Guild getGuild() {
        return guild;
    }

    public User getUser() {
        return user;
    }

    public String getNickname() {
        return nickname;
    }

    public OffsetDateTime getJoinedDate() {
        return joinedDate;
    }

    public Role getHighestRole() {
        return roles.isEmpty() ? null : roles.get(0);
    }

    public List<Role> getRoles() {
        return Collections.unmodifiableList(roles);
    }

    public List<Permission> getPermissions() {
        return Collections.unmodifiableList(permissions);
    }

    public boolean isOwner() {
        return guild.getOwner().equals(this);
    }

    public boolean isDeafened() {
        return isDeafened;
    }

    public boolean isMuted() {
        return isMuted;
    }

    @Override
    public String mention() {
        return "<@!"+user.getId()+">";
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
