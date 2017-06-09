package org.alienideology.jcord.object.guild;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.object.*;

import java.awt.Color;
import java.util.List;

/**
 * Role - A label that can be put on a set of guild members.
 * @author AlienIdeology
 */
public class Role extends DiscordObject implements Comparable<Role>, SnowFlake, Mention {

    private final String id;
    private final Guild guild;

    private String name;
    private Color color;
    private int position;
    private long permissionsLong;
    private List<Permission> permissions;

    private boolean isSeparateListed;
    private boolean canMention;

    public Role(Identity identity, Guild guild, String id, String name, Color color, int position, long permissions, boolean isSeparateListed, boolean canMention) {
        super(identity);
        this.guild = guild;
        this.id = id;
        this.name = name;
        this.color = color;
        this.permissionsLong = permissions;
        this.permissions = Permission.getPermissionsByLong(permissions);
        this.position = position;
        this.isSeparateListed = isSeparateListed;
        this.canMention = canMention;
    }

    public Guild getGuild() {
        return guild;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public int getPosition() {
        return position;
    }

    public boolean hasPermission (Permission permission) {
        return Permission.hasPermission (permissionsLong, permission);
    }

    /**
     * Check if this role have all the given permissions
     * @param permissions The varargs of permission enums to be checked
     * @return True if the role have all given permissions
     */
    public boolean hasAllPermissions (Permission... permissions) {
        for (Permission perm : permissions) {
            if (!hasPermission(perm)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if this role have one of the given permissions
     * To check if this member have all the permissions, see #hasAllPermissions(Permission...)
     * @param permissions The varargs of permission enums to be checked
     * @return True if the role have one of the given permissions
     */
    public boolean hasPermissions (Permission... permissions) {
        for (Permission perm : permissions) {
            if (hasPermission(perm)) {
                return true;
            }
        }
        return false;
    }

    public long getPermissionsLong() {
        return permissionsLong;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public boolean isSeparateListed() {
        return isSeparateListed;
    }

    public boolean canMention() {
        return canMention;
    }

    public boolean isEveryone() {
        return position == 0;
    }

    @Override
    public int compareTo(Role o) {
        return (o.position > this.position) ? -1 : ((o.position == this.position) ? 0 : 1);
    }

    @Override
    public String mention() {
        return "<@&"+id+">";
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Role) && ((Role )obj).getId().equals(id);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + guild.hashCode();
        result = 31 * result + (int) (permissionsLong ^ (permissionsLong >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "ID: "+id+"\tName: "+name+"  Position: "+position;
    }

}
