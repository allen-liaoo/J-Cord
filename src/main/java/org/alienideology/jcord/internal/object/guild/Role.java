package org.alienideology.jcord.internal.object.guild;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IRole;
import org.alienideology.jcord.handle.managers.IRoleManager;
import org.alienideology.jcord.handle.modifiers.IRoleModifier;
import org.alienideology.jcord.handle.permission.Permission;
import org.alienideology.jcord.internal.object.DiscordObject;
import org.alienideology.jcord.internal.object.Jsonable;
import org.alienideology.jcord.internal.object.managers.RoleManager;
import org.alienideology.jcord.internal.object.modifiers.RoleModifier;
import org.json.JSONObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author AlienIdeology
 */
public final class Role extends DiscordObject implements IRole, Jsonable {

    private final String id;
    private final IGuild guild;

    private String name;
    private Color color;
    private int position;
    private long permissionsLong;
    private List<Permission> permissions;

    private boolean isSeparateListed;
    private boolean canMention;

    private final RoleManager manager;
    private final RoleModifier modifier;

    public Role(Identity identity, IGuild guild, String id) {
        super(identity);
        this.guild = guild;
        this.id = id;
        this.manager = new RoleManager(this);
        this.modifier = new RoleModifier(this);
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

    private void initPermissions(Long permissionsLong) {
        this.permissions = permissionsLong == -1 ?      // -1 sent by RoleBuilder, just a place holder
                new ArrayList<>() : Permission.getPermissionsByLong(permissionsLong);
    }

    @Override
    public IGuild getGuild() {
        return guild;
    }

    @Override
    public IRoleManager getManager() {
        return manager;
    }

    @Override
    public IRoleModifier getModifier() {
        return modifier;
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
    public boolean hasAllPermissions(Collection<Permission> permissions) {
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

    public Role setName(String name) {
        this.name = name;
        return this;
    }

    public Role setColor(Color color) {
        this.color = color;
        return this;
    }

    public Role setPosition(int position) {
        this.position = position;
        return this;
    }

    public Role setPermissionsLong(long permissionsLong) {
        this.permissionsLong = permissionsLong;
        initPermissions(permissionsLong);
        return this;
    }

    public Role setSeparateListed(boolean separateListed) {
        isSeparateListed = separateListed;
        return this;
    }

    public Role setCanMention(boolean canMention) {
        this.canMention = canMention;
        return this;
    }
}
