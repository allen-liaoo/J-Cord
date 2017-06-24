package org.alienideology.jcord.handle.permission;

import java.util.Arrays;
import java.util.Collection;

/**
 * PermCheckable - An object that can be checked with permissions.
 *
 * @author AlienIdeology
 */
public interface PermCheckable {

    /**
     * Check if this object have all the given permissions.
     *
     * @param permissions The varargs of permission enums to be checked.
     * @return True if the object have all given permissions.
     */
    boolean hasAllPermissions(Permission... permissions);

    /**
     * Check if this object have one of the given permissions.
     *
     * @param checkAdmin If true, returns true if the object have {@code Administrator} permission.
     * @param permissions The varargs of permission enums to be checked.
     * @return True if the object have one of the given permissions.
     */
    default boolean hasPermissions(boolean checkAdmin, Permission... permissions) {
        return hasPermissions(checkAdmin, Arrays.asList(permissions));
    }

    /**
     * Check if this object have one of the given permissions.
     *
     * @param checkAdmin If true, returns true if the object have {@code Administrator} permission.
     * @param permissions The collection of permission enums to be checked.
     * @return True if the object have one of the given permissions.
     */
    boolean hasPermissions(boolean checkAdmin, Collection<Permission> permissions);
    
}
