package org.alienideology.jcord.handle.modifiers;

import org.alienideology.jcord.handle.audit.AuditAction;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IRole;
import org.alienideology.jcord.handle.permission.Permission;

import java.awt.*;
import java.util.Arrays;
import java.util.Collection;

/**
 * IRoleModifier - The modifier for a role.
 *
 * @author AlienIdeology
 */
public interface IRoleModifier extends IModifier<AuditAction<Void>> {

    /**
     * Get the guild this modifier belongs to.
     *
     * @return The guild.
     */
    default IGuild getGuild() {
        return getRole().getGuild();
    }

    /**
     * Get the role this modifier modifies.
     *
     * @return The role.
     */
    IRole getRole();

    /**
     * Modify the role's name.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the role is at a higher hierarchy position than the identity.
     *
     * @param name The name.
     * @return IRoleModifier for chaining.
     */
    IRoleModifier name(String name);

    /**
     * Modify the role's color.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the role is at a higher hierarchy position than the identity.
     *
     * @param color The color.
     * @return IRoleModifier for chaining.
     */
    IRoleModifier color(Color color);

    /**
     * Modify the role's permission.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the role is at a higher hierarchy position than the identity.
     *
     * @param permissions The varargs of permissions.
     * @return IRoleModifier for chaining.
     */
    default IRoleModifier permissions(Permission[] permissions) {
        return permissions(Arrays.asList(permissions));
    }

    /**
     * Modify the role's permission.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the role is at a higher hierarchy position than the identity.
     *
     * @param permissions The collection of permissions.
     * @return IRoleModifier for chaining.
     */
    IRoleModifier permissions(Collection<Permission> permissions);

    /**
     * Modify is the role separate listed.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the role is at a higher hierarchy position than the identity.
     *
     * @param isSeparateListed The boolean value, is the role separate listed or not.
     * @return IRoleModifier for chaining.
     */
    IRoleModifier isSeparateListed(boolean isSeparateListed);

    /**
     * Modify is the role mentionable.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the role is at a higher hierarchy position than the identity.
     *
     * @param canMention The boolean value, is the role mentionable.
     * @return IRoleModifier for chaining.
     */
    IRoleModifier canMention(boolean canMention);

    /**
     * Get the name attribute, used to modify the role's name.
     *
     * @return The name attribute.
     */
    Attribute<IRoleModifier, String> getNameAttr();

    /**
     * Get the color attribute, used to modify the role's color.
     *
     * @return The color attribute.
     */
    Attribute<IRoleModifier, Color> getColorAttr();

    /**
     * Get the permissions attribute, used to modify the role's permissions.
     *
     * @return The permissions attribute.
     */
    Attribute<IRoleModifier, Collection<Permission>> getPermissionsAttr();

    /**
     * Get the separate listed attribute, used to modify if the role role's is separate listed.
     *
     * @return The separate listed attribute.
     */
    Attribute<IRoleModifier, Boolean> getIsSeparateListedAttr();

    /**
     * Get the can mention attribute, used to modify if the role role's is mentionable.
     *
     * @return The can mention attribute.
     */
    Attribute<IRoleModifier, Boolean> getCanMentionAttr();

}
