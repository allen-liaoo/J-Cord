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
     * @param role The role to modify.
     * @param name The new name.
     */
    void modifyRoleName(IRole role, String name);

    /**
     * Modify a role's name.
     *
     * @param roleId The role's ID.
     * @param name The new name.
     */
    void modifyRoleName(String roleId, String name);

    /**
     * Modify a role's permissions.
     *
     * @param role The role to modify.
     * @param permissions A new sets of permissions.
     */
    void modifyRolePermissions(IRole role, Permission... permissions);

    /**
     * Modify a role's permissions.
     *
     * @param role The role to modify.
     * @param permissions A new collection of permissions.
     */
    void modifyRolePermissions(IRole role, Collection<Permission> permissions);

    /**
     * Modify a role's color.
     *
     * @param role The role to modify.
     * @param color The new color.
     */
    void modifyRoleColor(IRole role, Color color);

    /**
     * Modify a role's color.
     *
     * @param roleId The role's ID.
     * @param color The new color.
     */
    void modifyRoleColor(String roleId, Color color);

    /**
     * Set if this role will be separate listed from online members.
     *
     * @param role The role to modify.
     * @param isSeparateListed If set to true, then the role will be separated listed.
     */
    void modifyIsSeparateListed(IRole role, boolean isSeparateListed);

    /**
     * Set if this role will be separate listed from online members.
     *
     * @param roleId The role's ID.
     * @param isSeparateListed If set to true, then the role will be separated listed.
     */
    void modifyIsSeparateListed(String roleId, boolean isSeparateListed);

    /**
     * Set if this role can be mentioned by anyone in the guild.
     *
     * @param role The role to modify.
     * @param canMention If set to true, then the role can be mentioned.
     */
    void modifyCanMention(IRole role, boolean canMention);

    /**
     * Set if this role can be mentioned by anyone in the guild.
     *
     * @param roleId The role's ID.
     * @param canMention If set to true, then the role can be mentioned.
     */
    void modifyCanMention(String roleId, boolean canMention);

}
