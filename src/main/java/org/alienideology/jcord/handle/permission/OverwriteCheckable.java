package org.alienideology.jcord.handle.permission;

import org.alienideology.jcord.handle.guild.IMember;
import org.alienideology.jcord.handle.guild.IRole;

import java.util.Arrays;
import java.util.Collection;

/**
 * OverwriteCheckable - An object that can be checked with {@link PermOverwrite}.
 * @author AlienIdeology
 */
public interface OverwriteCheckable {

    /**
     * Check if the member have all the given permissions, including permission override.
     *
     * @param member The member.
     * @param permissions The varargs of permission enums to be checked.
     * @return True if the member have all given permissions in a guild channel.
     */
    default boolean hasAllPermission(IMember member, Permission... permissions) {
        return hasAllPermission(member, Arrays.asList(permissions));
    }

    /**
     * Check if the member have all the given permissions, including permission override.
     *
     * @param member The member.
     * @param permissions The collection of permission enums to be checked.
     * @return True if the member have all given permissions in a guild channel.
     */
    boolean hasAllPermission(IMember member, Collection<Permission> permissions);

    /**
     * Check if the role have all the given permissions, including permission override.
     *
     * @param role The role.
     * @param permissions The varargs of permission enums to be checked.
     * @return True if the role have all given permissions in a guild channel.
     */
    default boolean hasAllPermission(IRole role, Permission... permissions) {
        return hasAllPermission(role, Arrays.asList(permissions));
    }

    /**
     * Check if the role have all the given permissions, including permission override.
     *
     * @param role The role.
     * @param permissions The collection of permission enums to be checked.
     * @return True if the role have all given permissions in a guild channel.
     */
    boolean hasAllPermission(IRole role, Collection<Permission> permissions);

    /**
     * Check if the member have one of the given permissions.
     *
     * @param member The member.
     * @param permissions The varargs of permission enums to be checked.
     * @return True if the member have one of the given permissions.
     */
    default boolean hasPermission(IMember member, Permission... permissions) {
        return hasPermission(member, Arrays.asList(permissions));
    }

    /**
     * Check if the member have one of the given permissions.
     *
     * @param member The member.
     * @param permissions The collection of permission enums to be checked.
     * @return True if the member have one of the given permissions.
     */
    boolean hasPermission(IMember member, Collection<Permission> permissions);

    /**
     * Check if the role have one of the given permissions.
     *
     * @param role The role.
     * @param permissions The varargs of permission enums to be checked.
     * @return True if the role have one of the given permissions.
     */
    default boolean hasPermission(IRole role, Permission... permissions) {
        return hasAllPermission(role, Arrays.asList(permissions));
    }

    /**
     * Check if the role have one of the given permissions.
     *
     * @param role The role.
     * @param permissions The collection of permission enums to be checked.
     * @return True if the role have one of the given permissions.
     */
    boolean hasPermission(IRole role, Collection<Permission> permissions);

}
