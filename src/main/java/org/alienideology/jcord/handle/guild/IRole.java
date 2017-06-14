package org.alienideology.jcord.handle.guild;

import org.alienideology.jcord.handle.IDiscordObject;
import org.alienideology.jcord.handle.IMention;
import org.alienideology.jcord.handle.ISnowFlake;
import org.alienideology.jcord.internal.object.Guild;
import org.alienideology.jcord.handle.Permission;

import java.util.List;
import java.awt.Color;

/**
 * Role - A label that can be put on a set of guild members.
 * @author AlienIdeology
 */
public interface IRole extends IDiscordObject, ISnowFlake, IMention, Comparable<IRole> {

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
     * Compare the role by position.
     *
     * @param o Another role to compare with.
     * @return the integer result of the comparison.
     * @see Comparable#compareTo(Object) for the returning value.
     */
    @Override
    int compareTo(IRole o);

    @Override
    default String mention(){
        return "<#"+getId()+">";
    }
    
}
