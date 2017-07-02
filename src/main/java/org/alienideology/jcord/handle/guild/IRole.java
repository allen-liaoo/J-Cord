package org.alienideology.jcord.handle.guild;

import org.alienideology.jcord.handle.IDiscordObject;
import org.alienideology.jcord.handle.IMention;
import org.alienideology.jcord.handle.ISnowFlake;
import org.alienideology.jcord.handle.managers.IGuildManager;
import org.alienideology.jcord.handle.managers.IRoleManager;
import org.alienideology.jcord.handle.permission.PermCheckable;
import org.alienideology.jcord.handle.permission.Permission;

import java.awt.*;
import java.util.List;

/**
 * Role - A label that can be put on a set of guild members.
 * @author AlienIdeology
 */
public interface IRole extends IDiscordObject, ISnowFlake, IMention, PermCheckable, Comparable<IRole> {

    /**
     * Get the IRoleManager of his guild.
     * The role managers is used to change attributes or a role.
     *
     * @return The role managers.
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
     * Note that this event does not checks the role's permissions.
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
     * Note that this event checks the role's permissions. ({@code Manage Roles} permission)
     * @see IMember#getHighestRole()
     *
     * @param role The role to check with.
     * @return True if the role is modifiable.
     */
    default boolean canModify(IRole role) {
        return hasPermissions(true, Permission.MANAGE_ROLES) && this.compareTo(role) > 0;
    }

    /**
     * Check if this role can modify attributes of a guild emoji.
     * Note that this event checks the role's permissions. ({@code Manage Emojis} permission)
     *
     * @param emoji The emoji to check with.
     * @return True if the emoji is modifiable.
     */
    default boolean canModify(IGuildEmoji emoji) {
        return this.hasPermissions(true, Permission.MANAGE_EMOJIS) && emoji.canBeUseBy(this);
    }

    @Override
    default String mention(){
        return "<#"+getId()+">";
    }

    /**
     * Compare the role by position.
     *
     * @param o Another role to compare with.
     * @return the value {@code 0} if the roles' positions are the same;
     *         a value less than {@code 0} if this role's position is smaller than the other role's position; and
     *         a value greater than {@code 0} if this role's position is greater than the role's position
     * @see Comparable#compareTo(Object) for the returning value.
     */
    @Override
    default int compareTo(IRole o) {
        return (o.getPosition() > getPosition()) ? -1 : ((o.getPosition() == getPosition()) ? 0 : 1);
    }
    
}
