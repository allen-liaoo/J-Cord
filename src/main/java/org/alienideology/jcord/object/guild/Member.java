package org.alienideology.jcord.object.guild;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.object.*;
import org.alienideology.jcord.object.user.User;

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
        Collections.sort(roles, (o1, o2) -> -1 * o1.compareTo(o2));
        this.roles = roles;
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
     * Check if this member have one of the given permissions
     * To check if this member have all the permissions, see #hasAllPermissions(Permission...)
     * @param permissions The varargs of permission enums to be checked
     * @return True if the member have one of the given permissions
     */
    public boolean hasPermissions (Permission... permissions) {
        for (Role role : roles) {
            if (role.hasPermissions(permissions))
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
