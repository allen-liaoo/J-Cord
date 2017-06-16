package org.alienideology.jcord.handle.guild;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.alienideology.jcord.handle.IDiscordObject;
import org.alienideology.jcord.handle.IMention;
import org.alienideology.jcord.handle.ISnowFlake;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.handle.Permission;

import java.time.OffsetDateTime;
import java.util.*;

/**
 * Member - A user representation in a guild.
 * @author AlienIdeology
 */
public interface IMember extends IDiscordObject, ISnowFlake, IMention, Comparable<IMember> {

    /**
     * Kick a member.
     * @see org.alienideology.jcord.internal.object.guild.GuildManager#kickMember(IMember)
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Kick Members} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the member is the server owner or have higher role than the identity.
     * @exception IllegalArgumentException
     *          If the identity tries to kick itself from the guild.
     *          Please use {leave()} to leave a guild.
     *
     * @return True if the member is kicked successfully.
     */
    default boolean kick() {
        return getGuild().getGuildManager().kickMember(this);
    }

    /**
     * Ban this member.
     * @see org.alienideology.jcord.internal.object.guild.GuildManager#banMember(IMember)
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Ban Member} or {@code Administrator} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the member is the server owner or have higher role than the identity.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException If the member does not belong to this guild.
     * @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_MEMBER
     *
     * @return True if the member is banned successfully.
     */
    default boolean ban() {
        return getGuild().getGuildManager().banMember(this);
    }

    /**
     * Ban this member.
     * @see org.alienideology.jcord.internal.object.guild.GuildManager#banMember(IMember)
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Ban Member} or {@code Administrator} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the member is the server owner or have higher role than the identity.
     * @exception IllegalArgumentException If the days are smaller than 0 or greater than 7.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If the member does not belong to this guild.
     * @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_MEMBER
     *
     * @param days The number of days to delete the member's message. Only valid between 0 and 7.
     * @return True if the member is banned successfully.
     */
    default boolean ban(int days) {
        return getGuild().getGuildManager().banMember(this, days);
    }

    /**
     * Modify the nickname of a specific member.
     * Use empty or null nicknames to reset the nickname.
     * @see IMemberManager#modifyMemberNickname(IMember, String)
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Change Nickname} permission to modify itself,
     *              or {@code Manage Nicknames} permission to manage other nicknames.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the member is the server owner or have higher role than the identity.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If the member does not belong to this guild.
     * @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_MEMBER
     * @exception IllegalArgumentException If the nickname is longer than 32 letters.
     *
     * @param newNickname The new nickname.
     */
    default void modifyNickname(String newNickname) {
        getGuild().getMemberManager().modifyMemberNickname(this, newNickname);
    }

    /**
     * Add roles to this member.
     * @see IMemberManager#addRolesToMember(IMember, IRole...)
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the member is the server owner or have higher role than the identity.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If the role does not belong to this guild.
     * @exception IllegalArgumentException
     *          If this member already had that role.
     *
     * @param role The roles to add.
     */
    default void addRoles(IRole... role) {
        getGuild().getMemberManager().addRolesToMember(this, role);
    }

    /**
     * Add roles to this member.
     * @see IMemberManager#addRolesToMember(IMember, Collection)
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the member is the server owner or have higher role than the identity.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If the role does not belong to this guild.
     * @exception IllegalArgumentException
     *          If this member already had that role.
     *
     * @param role The roles to add.
     */
    default void addRoles(Collection<IRole> role) {
        getGuild().getMemberManager().addRolesToMember(this, role);
    }

    /**
     * Remove roles from this member.
     * @see IMemberManager#removeRolesFromMember(IMember, IRole...)
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the member is the server owner or have higher role than the identity.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If the role does not belong to this guild.
     *
     * @param roles The roles to be removed.
     */
    default void removeRoles(IRole... roles) {
        getGuild().getMemberManager().removeRolesFromMember(this, roles);
    }

    /**
     * Remove roles from this member.
     * @see IMemberManager#removeRolesFromMember(IMember, Collection)
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the member is the server owner or have higher role than the identity.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If the role does not belong to this guild.
     *
     * @param roles The roles to be removed.
     */
    default void removeRoles(Collection<IRole> roles) {
        getGuild().getMemberManager().removeRolesFromMember(this, roles);
    }

    /**
     * Mute this member.
     * @see IMemberManager#muteMember(IMember)
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the member does not have {@code Mute Members} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the member is the server owner or have higher role than the identity.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If the member does not belong to this guild.
     * @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_MEMBER
     *
     */
    default void mute() {
        getGuild().getMemberManager().muteMember(this);
    }

    /**
     * Unmute this member.
     * @see IMemberManager#unmuteMember(IMember)
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the member does not have {@code Mute Members} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the member is the server owner or have higher role than the identity.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException If the member does not belong to this guild.
     * @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_MEMBER
     *
     */
    default void unmute() {
        getGuild().getMemberManager().unmuteMember(this);
    }

    /**
     * Deafen this member.
     * @see IMemberManager#deafenMember(IMember)
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the member does not have {@code Deafen Members} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the member is the server owner or have higher role than the identity.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException If the member does not belong to this guild.
     * @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_MEMBER
     *
     */
    default void deafen() {
        getGuild().getMemberManager().deafenMember(this);
    }

    /**
     * Undeafen a member.
     * @see IMemberManager#undeafenMember(IMember)
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the member does not have {@code Deafen Members} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the member is the server owner or have higher role than the identity.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException If the member does not belong to this guild.
     * @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_MEMBER
     *
     */
    default void unDeafen() {
        getGuild().getMemberManager().undeafenMember(this);
    }

    /**
    * Check if this member have all the given permissions
     *
    * @param permissions The varargs of permission enums to be checked
    * @return True if the member have all given permissions
    */
    boolean hasAllPermissions (Permission... permissions);

    /**
    * Check if this member have one of the given permissions.
     *
    * @param checkAdmin If true, returns true if the member have administrator permission.
    * @param permissions The varargs of permission enums to be checked.
    * @return True if the member have one of the given permissions.
    */
    boolean hasPermissions (boolean checkAdmin, Permission... permissions) ;

    /**
    * Check if this member have one of the given permissions
     *
    * @deprecated #hasPermissions(boolean, Permission...) To check with the member having administrator permission.
    * @see #hasAllPermissions(Permission...) To check if this member have all the permissions.
    * @param permissions The varargs of permission enums to be checked
    * @return True if the member have one of the given permissions
    */
    @Deprecated
    boolean hasPermissions (Permission... permissions);

    /**
     * Get the guild this member belongs to.
     *
     * @return The guild.
     */
    IGuild getGuild();

    /**
     * Get the user instance of the member.
     *
     * @return Theuser
     */
    IUser getUser();

    /**
     * Get the nickname of this member.
     *
     * @return The string nickname, or null if no nickname is found.
     */
    @Nullable
    String getNickname();

    /**
     * Get the name of this member.
     * If this member has a nickname, this method will return the nickname. If not, this will returns the username.
     *
     * @return A not-null string of this member's name.
     */
    @NotNull
    default String getNameNotNull() {
        return getNickname() == null ? getUser().getName() : getNickname();
    }

    /**
     * Get the offset datetime of the date this member join the guild.
     *
     * @return The datetime.
     */
    OffsetDateTime getJoinedDate();

    /**
     * Get the highest role this member has.
     *
     * @return The highest role, or the default (@everyone) role if the member does not have any role.
     */
    @NotNull
    IRole getHighestRole();

    /**
     * Get a role by ID.
     *
     * @param id The id of a role.
     * @return The role or null if no role is found.
     */
    @Nullable
    IRole getRole(String id);

    /**
     * @return A list of roles this member has.
     */
    List<IRole> getRoles();

    /**
     * @return A list of permissions this member has.
     */
    List<Permission> getPermissions();

    /**
     * @return True if the member is the owner of the guild.
     */
    default boolean isOwner() {
        return this.equals(getGuild().getOwner());
    }

    /**
     * @return True if this member is deafened (Does not have permission to listen in a voice channel)
     */
    boolean isDeafened();

    /**
     * @return True if this member is muted (Does not have permission to speak in a voice channel)
     */
    boolean isMuted();

    /**
     * Check if this member can modify attributes of another member.
     * A member can only manage members that have a lower role.
     * Note that this method does not checks the member's permissions.
     *
     * @param member The member to check with.
     * @return True if the other member is modifiable.
     */
    default boolean canModify(IMember member) {
        return this.compareTo(member) > 0;
    }

    /**
     * Check if this member can modify attributes of another role.
     * A member can only manages roles that are lower than the member's highest role.
     * Note that this method checks the member's permissions. ({@code Manage Roles} permission)
     * @see #getHighestRole()
     *
     * @param role The role to check with.
     * @return True if the role is modifiable.
     */
    default boolean canModify(IRole role) {
        return this.hasPermissions(true, Permission.MANAGE_ROLES) && this.getHighestRole().compareTo(role) > 0;
    }

    /**
     * Check if this member can modify attributes of a guild emoji.
     * Note that this method checks the member's permissions. ({@code Manage Emojis} permission)
     *
     * @param emoji The emoji to check with.
     * @return True if the emoji is modifiable.
     */
    default boolean canModify(IGuildEmoji emoji) {
        return this.hasPermissions(Permission.MANAGE_EMOJIS) && emoji.canBeUseBy(this);
    }

    /**
     * Mention this member by nickname (Or username if there is no nickname)
     * @see IUser#mention(boolean)
     *
     * @return The string mention.
     */
    @Override
    default String mention() {
        return "<!@"+getId()+">";
    }

    /**
     * Compare the member by highest role's position.
     * @see #getHighestRole()
     * @see IRole#compareTo(IRole)
     *
     * @param o Another member to compare with.
     * @return the integer result of the comparison.
     * @see Comparable#compareTo(Object) for the returning value.
     */
    @Override
    default int compareTo(IMember o) {
        return o.getHighestRole().compareTo(getHighestRole());
    }

}
