package org.alienideology.jcord.handle.guild;

import org.alienideology.jcord.handle.Permission;
import org.alienideology.jcord.internal.object.guild.Role;

import java.awt.*;
import java.util.Arrays;
import java.util.Collection;

/**
 * RoleBuilder - A builder for creating a role in a guild.
 *
 * @author AlienIdeology
 * @since 0.0.5
 */
public class RoleBuilder {

    private String name = null;
    private long permissions;
    private Color color = null;
    private boolean isSeparateListed = false;
    private boolean canMention = false;

    public RoleBuilder() {
    }

    /**
     * Build a role.
     * The role's position will be the lowest position above the {@code @everyone} role.
     * @see IGuildManager#createRole(IRole)
     *
     * @return the role, used to put into {@link IGuildManager#createRole(IRole)}'s parameter.
     */
    public IRole build() {
        return new Role(null, null, null, name, color,  -1, permissions, isSeparateListed, canMention);
    }

    /**
     * Set the name of this role.
     * The default name will be {@code "new role"}.
     * @see IRole#getName()
     *
     * @param name The string name.
     * @return RoleBuilder for chaining.
     */
    public RoleBuilder setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Set the permissions of this role by a long value.
     * Please use a {@code Permission Calculator} for this.
     * The default permission will be the {@code @everyone} role's permissions.
     * @see Permission
     * @see IRole#getPermissions()
     *
     * @param permissionsLong The long of permissions.
     * @return RoleBuilder for chaining.
     */
    public RoleBuilder setPermissions(long permissionsLong) {
        this.permissions = permissionsLong;
        return this;
    }

    /**
     * Set the permissions of this role.
     * The default permission will be the {@code @everyone} role's permissions.
     * @see Permission
     * @see IRole#getPermissions()
     *
     * @param permissions The varargs of permissions.
     * @return RoleBuilder for chaining.
     */
    public RoleBuilder setPermissions(Permission... permissions) {
        this.permissions = Permission.getLongByPermissions(permissions);
        return this;
    }

    /**
     * Set the permissions of this role.
     * The default permission will be the {@code @everyone} role's permissions.
     * @see Permission
     * @see IRole#getPermissions()
     *
     * @param permissions A enum set of permissions.
     * @return RoleBuilder for chaining.
     */
    public RoleBuilder setPermissions(Collection<Permission> permissions) {
        this.permissions = Permission.getLongByPermissions(permissions);
        return this;
    }

    /**
     * This uses the pre-set permissions, and adding on the specified permissions.
     * @see Permission#PRE_SET_PERM_LARGE For informations on the pre-set permissions.
     *
     * @param permissions The specified permissions to add on.
     * @return RoleBuilder for chaining.
     */
    public RoleBuilder withAdditionalPermissions(Permission... permissions) {
        Collection<Permission> perms = Permission.PRE_SET_PERM_LARGE;
        perms.addAll(Arrays.asList(permissions));
        this.permissions = Permission.getLongByPermissions(perms);
        return this;
    }

    /**
     * Set the color of this role.
     * The default RGB color will be 0.
     * @see IRole#getColor()
     *
     * @param color The color.
     * @return RoleBuilder for chaining.
     */
    public RoleBuilder setColor(Color color) {
        this.color = color;
        return this;
    }

    /**
     * Set if this role will be separate listed from online members.
     * Default value is {@code false}.
     * @see IRole#isSeparateListed()
     *
     * @param isSeparateListed The boolean, true for separate listing the role.
     * @return RoleBuilder for chaining.
     */
    public RoleBuilder setSeparateListed(boolean isSeparateListed) {
        this.isSeparateListed = isSeparateListed;
        return this;
    }

    /**
     * Set if the role can be mentioned.
     * Default value is {@code false}.
     * @see IRole#canMention()
     *
     * @param canMention The boolean, true for making the role mentionable to everyone.
     * @return RoleBuilder for chaining.
     */
    public RoleBuilder setCanMention(boolean canMention) {
        this.canMention = canMention;
        return this;
    }
}
