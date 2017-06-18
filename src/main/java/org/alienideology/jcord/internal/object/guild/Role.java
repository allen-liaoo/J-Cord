package org.alienideology.jcord.internal.object.guild;

import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IRole;
import org.alienideology.jcord.handle.guild.IRoleManager;
import org.alienideology.jcord.handle.permission.Permission;
import org.alienideology.jcord.internal.object.Buildable;
import org.alienideology.jcord.internal.object.DiscordObject;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.json.JSONObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author AlienIdeology
 */
public final class Role extends DiscordObject implements IRole, Buildable {

    private final String id;
    private final Guild guild;

    private RoleManager roleManager;

    private String name;
    private Color color;
    private int position;
    private long permissionsLong;
    private List<Permission> permissions;

    private boolean isSeparateListed;
    private boolean canMention;

    public Role(IdentityImpl identity, Guild guild, String id, String name, Color color, int position, long permissions, boolean isSeparateListed, boolean canMention) {
        super(identity);
        this.guild = guild;
        this.id = id;
        this.name = name;
        this.color = color;
        this.permissionsLong = permissions;
        this.permissions = permissions == -1 ?      // -1 sent by RoleBuilder, just a place holder
                new ArrayList<>() : Permission.getPermissionsByLong(permissions);
        this.position = position;
        this.isSeparateListed = isSeparateListed;
        this.canMention = canMention;
        this.roleManager = new RoleManager(this);
    }

    @Override
    public JSONObject toJson() {
        JSONObject role = new JSONObject();
        if (name != null) role.put("name", name);
        if (permissionsLong != -1) role.put("permissions", permissionsLong);
        if (color != null) role.put("color", color.getRGB() & 0xFFFFFF);
        if (isSeparateListed) role.put("hoist", true);
        if (canMention) role.put("mentionable", true);
        return role;
    }

    @Override
    public IGuild getGuild() {
        return guild;
    }

    @Override
    public IRoleManager getRoleManager() {
        return roleManager;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public boolean hasAllPermissions(Permission... permissions) {
        for (Permission perm : permissions) {
            if (!hasPermissions(false, perm))
                return false;
        }
        return true;
    }

    @Override
    public boolean hasPermissions(boolean checkAdmin, Collection<Permission> permissions) {
        if (checkAdmin) {
            if (this.permissions.contains(Permission.ADMINISTRATOR)) return true;
        }
        for (Permission perm : permissions) {
            if (this.permissions.contains(perm))
                return true;
        }
        return false;
    }

    @Override
    public long getPermissionsLong() {
        return permissionsLong;
    }

    @Override
    public List<Permission> getPermissions() {
        return permissions;
    }

    @Override
    public boolean isSeparateListed() {
        return isSeparateListed;
    }

    @Override
    public boolean canMention() {
        return canMention;
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
        return "Role{" +
                "id='" + id + '\'' +
                ", guild=" + guild +
                ", name='" + name + '\'' +
                '}';
    }
}
