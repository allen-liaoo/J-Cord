package org.alienideology.jcord.handle.managers;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IRole;
import org.alienideology.jcord.handle.permission.Permission;

import java.awt.*;
import java.util.Collection;

/**
 * IRoleManager - A managers that manages a role in the guild.
 * @author AlienIdeology
 */
public interface IRoleManager {

    /**
     * Get the identity the managers's guild belongs to.
     *
     * @return The identity.
     */
    default Identity getIdentity() {
        return getGuild().getIdentity();
    }

    /**
     * Get the guild this role belongs to.
     *
     * @return The guild.
     */
    IGuild getGuild();

    /**
     * Gt the role this managers manages.
     *
     * @return The role.
     */
    IRole getRole();

    /**
     * Modify the name of this role.
     * Null or empty name will be ignored.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the role is at a higher hierarchy position than the identity.
     *
     * @param name The new name.
     */
    void modifyName(String name);

    /**
     * Modify this role's permissions.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the role is at a higher hierarchy position than the identity.
     *
     * @param permissions A new varargs of permissions.
     */
    void modifyPermissions(Permission... permissions);

    /**
     * Modify this role's permissions.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the role is at a higher hierarchy position than the identity.
     *
     * @param permissions A new collection of permissions.
     */
    void modifyPermissions(Collection<Permission> permissions);

    /**
     * Add permissions to this role.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the role is at a higher hierarchy position than the identity.
     *
     * @param permissions The varargs of permissions to add.
     */
    void addPermissions(Permission... permissions);

    /**
     * Add permissions to this role.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the role is at a higher hierarchy position than the identity.
     *
     * @param permissions The collection of permissions to add.
     */
    void addPermissions(Collection<Permission> permissions);

    /**
     * Remove permissions from a role.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the role is at a higher hierarchy position than the identity.
     *
     * @param permissions The varargs of permissions to remove.
     */
    void removePermissions(Permission... permissions);

    /**
     * Remove permissions from a role.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the role is at a higher hierarchy position than the identity.
     *
     * @param permissions The collection of permissions to remove.
     */
    void removePermissions(Collection<Permission> permissions);

    /**
     * Modify the role color.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the role is at a higher hierarchy position than the identity.
     *
     * @param color The new color.
     */
    void modifyColor(Color color);

    /**
     * Set if this role will be separate listed from online members.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the role is at a higher hierarchy position than the identity.
     *
     * @param isSeparateListed If set to true, then the role will be separated listed.
     */
    void modifyIsSeparateListed(boolean isSeparateListed);

    /**
     * Set if this role can be mentioned by anyone in the guild.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the role is at a higher hierarchy position than the identity.
     *
     * @param canMention If set to true, then the role can be mentioned.
     */
    void modifyCanMention(boolean canMention);

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
     * @exception IllegalArgumentException
     *          <ul>
     *              <li>If the role is @everyone (default) role.</li>
     *              <li>If the position is <ul>
     *                                      <li>smaller or equal to zero</li> or
     *                                      <li>greater than the total number of roles.</li>
     *                                      </ul></li>
     *          </ul>
     *
     * @param position The new position.
     */
    void changePosition(int position);

}
