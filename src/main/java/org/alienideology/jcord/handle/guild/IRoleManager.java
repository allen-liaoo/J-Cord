package org.alienideology.jcord.handle.guild;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.Permission;

import java.awt.*;
import java.util.Collection;

/**
 * IRoleManager - A manager that manages a role in the guild.
 * @author AlienIdeology
 */
public interface IRoleManager {

    /**
     * Get the identity the manager's guild belongs to.
     *
     * @return The identity.
     */
    default Identity getIdentity() {
        return getGuild().getIdentity();
    }

    /**
     * Get the guild this role manager manages.
     *
     * @return The guild.
     */
    IGuild getGuild();

    /**
     * Modify a role's name.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the role is at a higher hierarchy position than the identity.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If the role does not belong to this guild.
     * @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_ROLE
     *
     * @param role The role to modify.
     * @param name The new name.
     */
    void modifyRoleName(IRole role, String name);

    /**
     * Modify a role's name.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the role is at a higher hierarchy position than the identity.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If the role does not belong to this guild.
     * @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_ROLE
     *
     * @param roleId The role's ID.
     * @param name The new name.
     */
    void modifyRoleName(String roleId, String name);

    /**
     * Modify a role's permissions.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the role is at a higher hierarchy position than the identity.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If the role does not belong to this guild.
     * @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_ROLE
     *
     * @param role The role to modify.
     * @param permissions A new varargs of permissions.
     */
    void modifyRolePermissions(IRole role, Permission... permissions);

    /**
     * Modify a role's permissions.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the role is at a higher hierarchy position than the identity.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If the role does not belong to this guild.
     * @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_ROLE
     *
     * @param role The role to modify.
     * @param permissions A new collection of permissions.
     */
    void modifyRolePermissions(IRole role, Collection<Permission> permissions);

    /**
     * Add permissions to a role.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the role is at a higher hierarchy position than the identity.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If the role does not belong to this guild.
     * @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_ROLE
     *
     * @param role The role to add permissions to.
     * @param permissions The varargs of permissions to add.
     */
    void addPermissionsToRole(IRole role, Permission... permissions);

    /**
     * Add permissions to a role.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the role is at a higher hierarchy position than the identity.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If the role does not belong to this guild.
     * @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_ROLE
     *
     * @param role The role to add permissions to.
     * @param permissions The collection of permissions to add.
     */
    void addPermissionsToRole(IRole role, Collection<Permission> permissions);

    /**
     * Remove permissions from a role.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the role is at a higher hierarchy position than the identity.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If the role does not belong to this guild.
     * @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_ROLE
     *
     * @param role The role to remove permissions from.
     * @param permissions The varargs of permissions to remove.
     */
    void removePermissionsFromRole(IRole role, Permission... permissions);

    /**
     * Remove permissions from a role.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the role is at a higher hierarchy position than the identity.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If the role does not belong to this guild.
     * @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_ROLE
     *
     * @param role The role to remove permissions from.
     * @param permissions The collection of permissions to remove.
     */
    void removePermissionsFromRole(IRole role, Collection<Permission> permissions);

    /**
     * Modify a role's color.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the role is at a higher hierarchy position than the identity.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If the role does not belong to this guild.
     * @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_ROLE
     *
     * @param role The role to modify.
     * @param color The new color.
     */
    void modifyRoleColor(IRole role, Color color);

    /**
     * Modify a role's color.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the role is at a higher hierarchy position than the identity.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If the role does not belong to this guild.
     * @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_ROLE
     *
     * @param roleId The role's ID.
     * @param color The new color.
     */
    void modifyRoleColor(String roleId, Color color);

    /**
     * Set if this role will be separate listed from online members.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the role is at a higher hierarchy position than the identity.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If the role does not belong to this guild.
     * @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_ROLE
     *
     * @param role The role to modify.
     * @param isSeparateListed If set to true, then the role will be separated listed.
     */
    void modifyIsSeparateListed(IRole role, boolean isSeparateListed);

    /**
     * Set if this role will be separate listed from online members.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the role is at a higher hierarchy position than the identity.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If the role does not belong to this guild.
     * @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_ROLE
     *
     * @param roleId The role's ID.
     * @param isSeparateListed If set to true, then the role will be separated listed.
     */
    void modifyIsSeparateListed(String roleId, boolean isSeparateListed);

    /**
     * Set if a role can be mentioned by anyone in the guild.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the role is at a higher hierarchy position than the identity.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If the role does not belong to this guild.
     * @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_ROLE
     *
     * @param role The role to modify.
     * @param canMention If set to true, then the role can be mentioned.
     */
    void modifyCanMention(IRole role, boolean canMention);

    /**
     * Set if a role can be mentioned by anyone in the guild.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the role is at a higher hierarchy position than the identity.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If the role does not belong to this guild.
     * @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_ROLE
     *
     * @param roleId The role's ID.
     * @param canMention If set to true, then the role can be mentioned.
     */
    void modifyCanMention(String roleId, boolean canMention);

    /**
     * Change the position of a role.
     * The position is calculated by counting up the position.
     * The @everyone would be position 0, and the higher the position is, the higher the role is in the hierarchy.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException 
     *          <ul>
     *              <li>If the roles is at a higher hierarchy position than the identity.</li>
     *              <li>If identity attempt to move the role to a position higher than the highest
     *                  position of the identity's role.</li>
     *          </ul>
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If the role does not belong to this guild.
     * @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_ROLE
     * @exception IllegalArgumentException
     *          <ul>
     *              <li>If the role is @everyone (default) role.</li>
     *              <li>If the position is <ul>
     *                                      <li>smaller or equal to zero</li> or
     *                                      <li>greater than the total number of roles.</li>
     *                                      </ul></li>
     *          </ul>
     *
     * @param role The role to change position.
     * @param newPosition The new position.
     */
    void changeRolePosition(IRole role, int newPosition);

    /**
     * Change the position of a role by ID.
     * The position is calculated by counting up the position.
     * The @everyone would be position 0, and the higher the position is, the higher the role is in the hierarchy.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          <ul>
     *              <li>If the roles is at a higher hierarchy position than the identity.</li>
     *              <li>If identity attempt to move the role to a position higher than the highest
     *                  position of the identity's role.</li>
     *          </ul>
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If the role does not belong to this guild.
     * @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_ROLE
     * @exception IllegalArgumentException
     *          <ul>
     *              <li>If the role is @everyone (default) role.</li>
     *              <li>If the position is <ul>
     *                                      <li>smaller or equal to zero</li> or
     *                                      <li>greater than the total number of roles.</li>
     *                                      </ul></li>
     *          </ul>
     *
     * @param roleId The role's ID.
     * @param newPosition The new position.
     */
    void changeRolePosition(String roleId, int newPosition);

}
