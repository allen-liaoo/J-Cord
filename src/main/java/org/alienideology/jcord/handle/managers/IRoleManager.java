package org.alienideology.jcord.handle.managers;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.audit.AuditAction;
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
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the role is at a higher hierarchy position than the identity.
     *
     * @param name The new name.
     * @return A {@link Void} {@link AuditAction}, used to attach audit log reason.
     */
    default AuditAction<Void> modifyName(String name) {
        return getRole().getModifier().name(name).modify();
    }

    /**
     * Modify the role color.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the role is at a higher hierarchy position than the identity.
     *
     * @param color The new color.
     * @return A {@link Void} {@link AuditAction}, used to attach audit log reason.
     */
    default AuditAction<Void> modifyColor(Color color) {
        return getRole().getModifier().color(color).modify();
    }

    /**
     * Modify this role's permissions.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the role is at a higher hierarchy position than the identity.
     *
     * @param permissions A new varargs of permissions.
     * @return A {@link Void} {@link AuditAction}, used to attach audit log reason.
     */
    default AuditAction<Void> modifyPermissions(Permission... permissions) {
        return getRole().getModifier().permissions(permissions).modify();
    }

    /**
     * Modify this role's permissions.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the role is at a higher hierarchy position than the identity.
     *
     * @param permissions A new collection of permissions.
     * @return A {@link Void} {@link AuditAction}, used to attach audit log reason.
     */
    default AuditAction<Void> modifyPermissions(Collection<Permission> permissions) {
        return getRole().getModifier().permissions(permissions).modify();
    }

    /**
     * Add permissions to this role.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the role is at a higher hierarchy position than the identity.
     *
     * @param permissions The varargs of permissions to add.
     * @return A {@link Void} {@link AuditAction}, used to attach audit log reason.
     */
    AuditAction<Void> addPermissions(Permission... permissions);

    /**
     * Add permissions to this role.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the role is at a higher hierarchy position than the identity.
     *
     * @param permissions The collection of permissions to add.
     * @return A {@link Void} {@link AuditAction}, used to attach audit log reason.
     */
    AuditAction<Void> addPermissions(Collection<Permission> permissions);

    /**
     * Remove permissions from a role.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the role is at a higher hierarchy position than the identity.
     *
     * @param permissions The varargs of permissions to remove.
     * @return A {@link Void} {@link AuditAction}, used to attach audit log reason.
     */
    AuditAction<Void> removePermissions(Permission... permissions);

    /**
     * Remove permissions from a role.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the role is at a higher hierarchy position than the identity.
     *
     * @param permissions The collection of permissions to remove.
     * @return A {@link Void} {@link AuditAction}, used to attach audit log reason.
     */
    AuditAction<Void> removePermissions(Collection<Permission> permissions);

    /**
     * Set if this role will be separate listed from online members.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the role is at a higher hierarchy position than the identity.
     *
     * @param isSeparateListed If set to true, then the role will be separated listed.
     * @return A {@link Void} {@link AuditAction}, used to attach audit log reason.
     */
    default AuditAction<Void> modifyIsSeparateListed(boolean isSeparateListed) {
        return getRole().getModifier().isSeparateListed(isSeparateListed).modify();
    }

    /**
     * Set if this role can be mentioned by anyone in the guild.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the role is at a higher hierarchy position than the identity.
     *
     * @param canMention If set to true, then the role can be mentioned.
     * @return A {@link Void} {@link AuditAction}, used to attach audit log reason.
     */
    default AuditAction<Void> modifyCanMention(boolean canMention) {
        return getRole().getModifier().canMention(canMention).modify();
    }

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
     * @return A {@link Void} {@link AuditAction}, used to attach audit log reason.
     */
    AuditAction<Void> changePosition(int position);

}
