package org.alienideology.jcord.handle.managers;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.Icon;
import org.alienideology.jcord.handle.builders.ChannelBuilder;
import org.alienideology.jcord.handle.builders.RoleBuilder;
import org.alienideology.jcord.handle.channel.IGuildChannel;
import org.alienideology.jcord.handle.channel.ITextChannel;
import org.alienideology.jcord.handle.channel.IVoiceChannel;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IMember;
import org.alienideology.jcord.handle.guild.IRole;
import org.alienideology.jcord.handle.guild.Region;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.handle.user.IWebhook;
import org.alienideology.jcord.internal.exception.ErrorResponseException;
import org.alienideology.jcord.internal.exception.HigherHierarchyException;
import org.alienideology.jcord.internal.exception.HttpErrorException;
import org.alienideology.jcord.internal.gateway.ErrorResponse;

import java.util.List;

/**
 * IGuildManager - The manager that manages and perform actions upon a guild.
 *
 * @author AlienIdeology
 * @since 0.0.4
 */
public interface IGuildManager {

    /**
     * Get the identity the guild belongs to.
     *
     * @return The identity.
     */
    default Identity getIdentity() {
        return getGuild().getIdentity();
    }

    /**
     * Get the guild this managers manages.
     *
     *
     * @return The guild.
     */
    IGuild getGuild();

    /*
        --------------------------
            Guild Modification
        --------------------------
     */

    /**
     * Modify the guild's name.
     * Null or empty {@code name} will be ignored.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Manager Server} or {@code Administrator} permission.
     * @exception IllegalArgumentException
     *          If the name is not valid. See {@link IGuild#isValidName(String)}.
     *
     * @param name The string name.
     */
    void modifyName(String name);

    /**
     * Modify the guild's owner. The identity must be the guild owner originally in order to modify the owner.
     * This is human only, since bots can not be a guild owner.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Manager Server} or {@code Administrator} permission.
     *
     * @param newOwner The new owner.
     */
    void modifyOwner(IMember newOwner);

    /**
     * Modify the guild's region.
     * {@link Region#UNKNOWN} will be ignored.
     *
     * @exception HttpErrorException
     *          if the region is vip, but the guild is not. See {@link Region#isVIP}
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Manager Server} or {@code Administrator} permission.
     *
     * @param region The region enumeration.
     */
    void modifyRegion(Region region);

    /**
     * Modify the guild's verification level.
     * {@link org.alienideology.jcord.handle.guild.IGuild.Verification#UNKNOWN} will be ignored.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Manager Server} or {@code Administrator} permission.
     *
     * @param verification The verification enumeration.
     */
    void modifyVerification(IGuild.Verification verification);

    /**
     * Modify the guild's notification level.
     * {@link org.alienideology.jcord.handle.guild.IGuild.Notification#UNKNOWN} will be ignored.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Manager Server} or {@code Administrator} permission.
     *
     * @param notification The notification enumeration.
     */
    void modifyNotification(IGuild.Notification notification);

    /**
     * Modify the guild's afk timeout.
     * {@link org.alienideology.jcord.handle.guild.IGuild.AFKTimeout#UNKNOWN} will be ignored.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Manager Server} or {@code Administrator} permission.
     *
     * @param afkTimeout The afk timeout.
     */
    void modifyAFKTimeout(IGuild.AFKTimeout afkTimeout);

    /**
     * Modify the guild's afk channel.
     * Null channel will be ignored.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Manager Server} or {@code Administrator} permission.
     *
     * @param afkChannel The afk channel.
     */
    void modifyAFKChannel(IVoiceChannel afkChannel);

    /**
     * Modify the guild's afk channel by ID.
     * Null or empty ID will be ignored.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Manager Server} or {@code Administrator} permission.
     *
     * @param afkChannelId The afk channel ID.
     */
    void modifyAFKChannel(String afkChannelId);

    /**
     * Modify the guild's icon.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Manager Server} or {@code Administrator} permission.
     *
     * @param icon The image file.
     */
    void modifyIcon(Icon icon);

    /**
     * Modify the guild's splash icon. This is VIP guild only.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Manager Server} or {@code Administrator} permission.
     *
     * @param icon The image.
     */
    void modifySplash(Icon icon);

    /*
        ---------------------
            Member Action
        ---------------------
     */

    /**
     * Kick a member.
     * To let the identity leave a guild, see {@link ISelfManager#leaveGuild(IGuild)}
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Kick Members} permission.
     * @exception IllegalArgumentException
     *          If the identity tries to kick itself from the guild.
     *          Please use {leave()} to leave a guild.
     *
     * @param member The member.
     * @return True if the member is kicked successfully.
     */
    boolean kickMember(IMember member);

    /**
     * Kick a member by ID.
     * To let the identity leave a guild, see {@link ISelfManager#leaveGuild(IGuild)}
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Kick Members} permission.
     * @exception HigherHierarchyException
     *          If the member is at a higher hierarchy than the identity (ie.e server owner)
     * @exception IllegalArgumentException
     *          If the identity tries to kick itself from the guild.
     *          Please use {leave()} to leave a guild.
     *
     * @param memberId The member's ID.
     * @return True if the member is kicked successfully.
     */
    boolean kickMember(String memberId);

    /**
     * Get the value of members than can be pruned.
     * The default days of this event is 7.
     * @see #pruneMembers(int) For more information on pruning members.
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Kick Members} {@code Administrator} permissions.
     *
     * @return The integer value of prunable members.
     */
    int getPrunableCount();

    /**
     * Get the value of members than can by pruned.
     * @see #pruneMembers(int) For more information on pruning members.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Kick Members} {@code Administrator} permissions.
     * @exception IllegalArgumentException If the days provided are smaller than 1 or greater than 30.
     *
     * @param days The amount of days to start pruning.
     * @return The integer value of prunable members.
     */
    int getPrunableCount(int days);

    /**
     * Prune members that are not active for a certain days.
     * @see #pruneMembers(int) For more information on pruning members.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Kick Members} {@code Administrator} permissions.
     *
     * The default days of this event is 7.
     */
    void pruneMembers();

    /**
     * Prune members that are not active for the given days.
     * Prune:
     * <p>Pruning will kick members than has not been seen in an amount of days and are not assigned to any roles.
     * The kicked members can rejoin the guild using a new instant invite.</p>
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Kick Members} {@code Administrator} permissions.
     * @exception IllegalArgumentException If the days provided are smaller than 1 or greater than 30.
     *
     * @param days The amount of days.
     */
    void pruneMembers(int days);

    /**
     * Get a list of members that are banned from this guild.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Ban Members} permission.
     *
     * @return A list of banned members.
     */
    List<IMember> getBans();

    /**
     * Ban a member.
     * The number of days to delete messages is 7 by default.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Ban Member} or {@code Administrator} permission.
     * @exception HigherHierarchyException
     *          If the member is at a higher hierarchy than the identity (ie.e server owner)
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException If the member does not belong to this guild.
     * @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_MEMBER
     *
     * @param member The member.
     * @return True if the member is banned successfully.
     */
    boolean banMember(IMember member);

    /**
     * Ban a member.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Ban Member} or {@code Administrator} permission.
     * @exception HigherHierarchyException
     *          If the member is at a higher hierarchy than the identity (ie.e server owner)
     * @exception IllegalArgumentException If the days are smaller than 0 or greater than 7.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException If the member does not belong to this guild.
     * @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_MEMBER
     *
     * @param member The member.
     * @param days The number of days to delete the member's message. Only valid between 0 and 7.
     * @return True if the member is banned successfully.
     */
    boolean banMember(IMember member, int days);

    /**
     * Ban a member by ID.
     * The number of days to delete messages is 7 by default.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Ban Member} or {@code Administrator} permission.
     * @exception HigherHierarchyException
     *          If the member is at a higher hierarchy than the identity (ie.e server owner)
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException If the member does not belong to this guild.
     * @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_MEMBER
     *
     * @param memberId The member's ID.
     * @return True if the member is banned successfully.
     */
    boolean banMember(String memberId);

    /**
     * Ban a member by ID.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Ban Member} or {@code Administrator} permission.
     * @exception HigherHierarchyException
     *          If the member is at a higher hierarchy than the identity (ie.e server owner)
     * @exception IllegalArgumentException If the days are smaller than 0 or greater than 7.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException If the member does not belong to this guild.
     * @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_MEMBER
     *
     * @param memberId The member's ID.
     * @param days The number of days to delete the member's message. Only valid between 0 and 7.
     * @return True if the member is banned successfully.
     */
    boolean banMember(String memberId, int days);

    /**
     * Unban a user.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Ban Member} or {@code Administrator} permission.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException If the user is not in the guild ban list.
     * @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_USER
     *
     * @param user The user.
     * @return True if the member is unbanned successfully.
     */
    boolean unbanUser(IUser user);

    /**
     * Unban a member by ID.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Ban Member} or {@code Administrator} permission.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException If the user is not in the guild ban list.
     * @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_USER
     *
     * @param memberId The user's ID.
     * @return True if the user is unbanned successfully.
     */
    boolean unbanUser(String memberId);

    /**
     * Create a text channel in this guild.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Channels} permission.
     *
     * @param channel The channel built by {@link org.alienideology.jcord.handle.builders.ChannelBuilder}.
     * @see ChannelBuilder#buildTextChannel()
     * @return The text channel created.
     * This returning channel is not the same instance as the parameter.
     */
    ITextChannel createTextChannel(ITextChannel channel);

    /**
     * Copy and create a new text channel with the same properties.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Channels} permission.
     *
     * @param channel The channel to clone.
     * @return The channel cloned and created.
     */
    ITextChannel cloneTextChannel(ITextChannel channel);

    /**
     * Create a voice channel in this guild.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Channels} permission.
     *
     * @param channel The channel built by {@link org.alienideology.jcord.handle.builders.ChannelBuilder}.
     * @see ChannelBuilder#buildVoiceChannel()
     * @return The voice channel created.
     */
    IVoiceChannel createVoiceChannel(IVoiceChannel channel);

    /**
     * Copy and create a new voice channel with the same properties.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Channels} permission.
     *
     * @param channel The channel to clone.
     * @return The channel cloned and created.
     */
    IVoiceChannel cloneVoiceChannel(IVoiceChannel channel);

    /**
     * Deletes a guild channel.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity do not have {@code Manage Channels} permission.
     * @exception IllegalArgumentException
     *          If the channel is a text channel, and the text channel is a default channel.
     *          @see IGuild#getDefaultChannel() For more information.
     *
     * @param channel The channel.
     */
    void deleteGuildChannel(IGuildChannel channel);

    /**
     * Create a role in this guild.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     *
     * @param role The role built by {@link RoleBuilder}
     * @return The role created.
     */
    IRole createRole(IRole role);

    /**
     * Copy and create a role in this guild.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     *
     * @param role The role to clone.
     * @return The role cloned and created.
     */
    IRole cloneRole(IRole role);

    /**
     * Delete a existed role in this guild.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If the role is null or if the role is not from this guild.
     * @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_ROLE
     *
     * @param role The role to be deleted.
     */
    void deleteRole(IRole role);

    /**
     * Create a new webhook for a text channel in this guild.
     * @see IChannelManager#createWebhook(String, Icon)
     *
     * @param channel The text channel.
     * @param defaultName The default name for the webhook. Null or empty for no name.
     * @param defaultAvatar The default avatar.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manager Webhooks} permission.
     * @exception IllegalArgumentException
     *          If the default name is not valid. See {@link IWebhook#isValidWebhookName(String)}.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If the channel does not belong to this guild.
     * @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_CHANNEL
     */
    default void createWebhook(ITextChannel channel, String defaultName, Icon defaultAvatar) {
        if (!channel.getGuild().equals(getGuild())) {
            throw new ErrorResponseException(ErrorResponse.UNKNOWN_CHANNEL);
        }
        channel.getChannelManager().createWebhook(defaultName, defaultAvatar);
    }

    /**
     * Delete a webhook.
     *
     * @param webhook The webhook be to deleted.
     *
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If the webhook does not belong to this guild.
     * @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_USER
     */
    default void deleteWebhook(IWebhook webhook) {
        if (!webhook.getGuild().equals(getGuild())) {
            throw new ErrorResponseException(ErrorResponse.UNKNOWN_USER);
        }
        webhook.getWebhookManager().delete();
    }

}
