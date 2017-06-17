package org.alienideology.jcord.handle.guild;

import org.alienideology.jcord.handle.IDiscordObject;
import org.alienideology.jcord.handle.IMention;
import org.alienideology.jcord.handle.ISnowFlake;
import org.alienideology.jcord.handle.Permission;

import java.awt.*;
import java.util.List;

/**
 * Role - A label that can be put on a set of guild members.
 * @author AlienIdeology
 */
public interface IRole extends IDiscordObject, ISnowFlake, IMention, Comparable<IRole> {

    /**
     * Get the IRoleManager of his guild.
     * The role manager is used to change attributes or a role.
     *
     * @return The role manager.
     */
    IRoleManager getRoleManager();

    /**
     * Deletes this role from the guild.
     * @see IGuildManager#deleteRole(IRole)
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     *
     */
    default void delete() {
        getGuild().getGuildManager().deleteRole(this);
    }

    /**
     * Get the guild this role belongs to.
     *
     * @return The guild.
     */
    IGuild getGuild();

    /**
     * Get the name of this role.
     *
     * @return The string name.
     */
    String getName();

    /**
     * Get the color of this role.
     *
     * @return The color.
     */
    Color getColor();

    /**
     * Get the position of this role.
     * Higher position means higher role hierarchy.
     *
     * @return The integer value of the role position.
     */
    int getPosition();

    /**
     * Check if this role have the given permission.
     * @deprecated See {@link #hasAllPermissions(Permission...)}
     *
     * @param permission The specified permission.
     * @return True if the role have this permission.
     */
    @Deprecated
    boolean hasPermission(Permission permission);

    /**
    * Check if this role have all the given permissions
    * @param permissions The varargs of permission enums to be checked
    * @return True if the role have all given permissions
    */
    boolean hasAllPermissions (Permission... permissions);

    /**
    * Check if this role have one of the given permissions
    * To check if this member have all the permissions, see #hasAllPermissions(Permission...)
     *
    * @param permissions The varargs of permission enums to be checked
    * @return True if the role have one of the given permissions
    */
    boolean hasPermissions (Permission... permissions);

    /**
     * Get the raw value of this role's permissions.
     *
     * @return The long value
     */
    long getPermissionsLong();

    /**
     * @return A list of permissions this role has.
     */
    List<Permission> getPermissions();

    /**
     * @return True if this role is listed separately from online members.
     */
    boolean isSeparateListed();

    /**
     * @return True if this role is mentionable.
     */
    boolean canMention();

    /**
     * @return True if this role is the @everyone (default) role.
     */
    default boolean isEveryone() {
        return getPosition() == 0;
    }

    /**
     * Check if this role can modify attributes of another member.
     * A role can only manage members that have a lower role.
     * Note that this method does not checks the role's permissions.
     *
     * @param member The member to check with.
     * @return True if the other member is modifiable.
     */
    default boolean canModify(IMember member) {
        return this.compareTo(member.getHighestRole()) > 0;
    }

    /**
     * Check if this role can modify attributes of another role.
     * A role can only manages roles that are lower than this role.
     * Note that this method checks the role's permissions. ({@code Manage Roles} permission)
     * @see IMember#getHighestRole()
     *
     * @param role The role to check with.
     * @return True if the role is modifiable.
     */
    default boolean canModify(IRole role) {
        return this.hasPermissions(Permission.ADMINISTRATOR, Permission.MANAGE_ROLES) && this.compareTo(role) > 0;
    }

    /**
     * Check if this role can modify attributes of a guild emoji.
     * Note that this method checks the role's permissions. ({@code Manage Emojis} permission)
     *
     * @param emoji The emoji to check with.
     * @return True if the emoji is modifiable.
     */
    default boolean canModify(IGuildEmoji emoji) {
        return this.hasPermissions(Permission.ADMINISTRATOR, Permission.MANAGE_EMOJIS) && emoji.canBeUseBy(this);
    }

    @Override
    default String mention(){
        return "<#"+getId()+">";
    }

    /**
     * Compare the role by position.
     *
     * @param o Another role to compare with.
     * @return the integer result of the comparison.
     * @see Comparable#compareTo(Object) for the returning value.
     */
    @Override
    default int compareTo(IRole o) {
        return (o.getPosition() > getPosition()) ? -1 : ((o.getPosition() == getPosition()) ? 0 : 1);
    }
    
}
