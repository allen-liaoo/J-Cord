package org.alienideology.jcord.handle.guild;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.alienideology.jcord.handle.IDiscordObject;
import org.alienideology.jcord.handle.IMention;
import org.alienideology.jcord.handle.ISnowFlake;
import org.alienideology.jcord.handle.managers.IMemberManager;
import org.alienideology.jcord.handle.permission.PermCheckable;
import org.alienideology.jcord.handle.permission.Permission;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.object.managers.GuildManager;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Member - A user representation in a guild.
 * @author AlienIdeology
 */
public interface IMember extends IDiscordObject, ISnowFlake, IMention, PermCheckable, Comparable<IMember> {

    /**
     * Get the IMemberManager of this guild.
     * The member managers is used to change nicknames, mute, and deafen members.
     *
     * @return The member managers.
     */
    IMemberManager getMemberManager();

    /**
     * Kick this member.
     * @see GuildManager#kickMember(IMember)
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
     * @see GuildManager#banMember(IMember)
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
     * @see GuildManager#banMember(IMember)
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
        return this.hasPermissions(true, Permission.MANAGE_EMOJIS) && emoji.canBeUseBy(this);
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
     * Compare this to another member by highest role's position.
     * @see #getHighestRole()
     * @see IRole#compareTo(IRole)
     *
     * @param o Another member to compare with.
     * @return the value {@code 0} if the members' role positions are the same;
     *         a value less than {@code 0} if this member's role position is smaller than the other member's role position; and
     *         a value greater than {@code 0} if this member's role position is greater than the other member's role position
     * @see Comparable#compareTo(Object) for the returning value.
     */
    @Override
    default int compareTo(IMember o) {
        return o.getHighestRole().compareTo(getHighestRole());
    }

}
