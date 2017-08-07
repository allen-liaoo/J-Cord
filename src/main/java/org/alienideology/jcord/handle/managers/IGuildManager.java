package org.alienideology.jcord.handle.managers;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.Icon;
import org.alienideology.jcord.handle.Region;
import org.alienideology.jcord.handle.audit.AuditAction;
import org.alienideology.jcord.handle.builders.ChannelBuilder;
import org.alienideology.jcord.handle.builders.RoleBuilder;
import org.alienideology.jcord.handle.channel.IGuildChannel;
import org.alienideology.jcord.handle.channel.ITextChannel;
import org.alienideology.jcord.handle.channel.IVoiceChannel;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IMember;
import org.alienideology.jcord.handle.guild.IRole;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.handle.user.IWebhook;
import org.alienideology.jcord.internal.exception.ErrorResponseException;
import org.alienideology.jcord.internal.exception.HigherHierarchyException;
import org.alienideology.jcord.internal.exception.HttpErrorException;
import org.alienideology.jcord.internal.rest.ErrorResponse;

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
     * Null or empty {@code name} will be result in a {@link org.alienideology.jcord.handle.audit.AuditAction.EmptyAuditAction} being returned.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Manager Server} or {@code Administrator} permission.
     * @exception IllegalArgumentException
     *          If the name is not valid. See {@link IGuild#isValidName(String)}.
     *
     * @param name The string name.
     * @return A void {@link AuditAction}, used to attach reason (or not) to the modify action.
     */
    AuditAction<Void> modifyName(String name);

    /**
     * Modify the guild's owner. The identity must be the guild owner originally in order to modify the owner.
     * This is for client only, since bots can not be a guild owner.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Manager Server} or {@code Administrator} permission.
     *
     * @param newOwner The new owner.
     * @return A void {@link AuditAction}, used to attach reason (or not) to the modify action.
     */
    AuditAction<Void> modifyOwner(IMember newOwner);

    /**
     * Modify the guild's region.
     * {@link Region#UNKNOWN} will be result in a {@link org.alienideology.jcord.handle.audit.AuditAction.EmptyAuditAction} being returned.
     *
     * @exception HttpErrorException
     *          if the region is vip, but the guild is not. See {@link Region#isVIP}
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Manager Server} or {@code Administrator} permission.
     *
     * @param region The region enumeration.
     * @return A void {@link AuditAction}, used to attach reason (or not) to the modify action.
     */
    AuditAction<Void> modifyRegion(Region region);

    /**
     * Modify the guild's verification level.
     * {@link org.alienideology.jcord.handle.guild.IGuild.Verification#UNKNOWN} will be result in a {@link org.alienideology.jcord.handle.audit.AuditAction.EmptyAuditAction} being returned.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Manager Server} or {@code Administrator} permission.
     *
     * @param verification The verification enumeration.
     * @return A void {@link AuditAction}, used to attach reason (or not) to the modify action.
     */
    AuditAction<Void> modifyVerification(IGuild.Verification verification);

    /**
     * Modify the guild's notification level.
     * {@link org.alienideology.jcord.handle.guild.IGuild.Notification#UNKNOWN} will be result in a {@link org.alienideology.jcord.handle.audit.AuditAction.EmptyAuditAction} being returned.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Manager Server} or {@code Administrator} permission.
     *
     * @param notification The notification enumeration.
     * @return A void {@link AuditAction}, used to attach reason (or not) to the modify action.
     */
    AuditAction<Void> modifyNotification(IGuild.Notification notification);

    /**
     * Modify the guild's afk timeout.
     * {@link org.alienideology.jcord.handle.guild.IGuild.AFKTimeout#UNKNOWN} will be result in a {@link org.alienideology.jcord.handle.audit.AuditAction.EmptyAuditAction} being returned.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Manager Server} or {@code Administrator} permission.
     *
     * @param afkTimeout The afk timeout.
     * @return A void {@link AuditAction}, used to attach reason (or not) to the modify action.
     */
    AuditAction<Void> modifyAFKTimeout(IGuild.AFKTimeout afkTimeout);

    /**
     * Modify the guild's afk channel.
     * Null channel will be result in a {@link org.alienideology.jcord.handle.audit.AuditAction.EmptyAuditAction} being returned.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Manager Server} or {@code Administrator} permission.
     *
     * @param afkChannel The afk channel.
     * @return A void {@link AuditAction}, used to attach reason (or not) to the modify action.
     */
    AuditAction<Void> modifyAFKChannel(IVoiceChannel afkChannel);

    /**
     * Modify the guild's afk channel by ID.
     * Null or empty ID will be result in a {@link org.alienideology.jcord.handle.audit.AuditAction.EmptyAuditAction} being returned.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Manager Server} or {@code Administrator} permission.
     *
     * @param afkChannelId The afk channel ID.
     * @return A void {@link AuditAction}, used to attach reason (or not) to the modify action.
     */
    AuditAction<Void> modifyAFKChannel(String afkChannelId);

    /**
     * Modify the guild's icon.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Manager Server} or {@code Administrator} permission.
     *
     * @param icon The image file.
     * @return A void {@link AuditAction}, used to attach reason (or not) to the modify action.
     */
    AuditAction<Void> modifyIcon(Icon icon);

    /**
     * Modify the guild's splash icon. This is VIP guild only.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Manager Server} or {@code Administrator} permission.
     *
     * @param icon The image.
     * @return A void {@link AuditAction}, used to attach reason (or not) to the modify action.
     */
    AuditAction<Void> modifySplash(Icon icon);

    /**
     * Enable or disable the guild embed.
     *
     * @param enableEmbed The boolean value, to either enable or disable guild embed.
     * @return A void {@link AuditAction}, used to attach reason (or not) to the modify action.
     */
    AuditAction<Void> enableGuildEmbed(boolean enableEmbed);

    /**
     * Modify the embed channel of this guild.
     *
     * @param embedChannel The channel to embed.
     * @return A void {@link AuditAction}, used to attach reason (or not) to the modify action.
     */
    AuditAction<Void> modifyEmbedChannel(ITextChannel embedChannel);

    /**
     * Modify the embed channel of this guild by ID.
     *
     * @param embedChannelId The channel's ID to embed.
     * @return A void {@link AuditAction}, used to attach reason (or not) to the modify action.
     */
    AuditAction<Void> modifyEmbedChannel(String embedChannelId);

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
     * @return A boolean {@link AuditAction}, used to attach reason (or not).
     * The boolean value will be true if the member is kicked successfully.
     */
    AuditAction<Boolean> kickMember(IMember member);

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
     * @return A boolean {@link AuditAction}, used to attach reason (or not).
     * The boolean value will be true if the member is kicked successfully.
     */
    AuditAction<Boolean> kickMember(String memberId);

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
     * @return A void {@link AuditAction}. Used to attach audit log reason to the pruning action.
     */
    AuditAction<Void> pruneMembers(int days);

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
     * @see ErrorResponse#UNKNOWN_MEMBER
     *
     * @param member The member.
     * @return A boolean {@link AuditAction}, used to attach reason (or not).
     * The boolean value will be true if the member is banned successfully.
     */
    AuditAction<Boolean> banMember(IMember member);

    /**
     * Ban a member.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Ban Member} or {@code Administrator} permission.
     * @exception HigherHierarchyException
     *          If the member is at a higher hierarchy than the identity (ie.e server owner)
     * @exception IllegalArgumentException If the days are smaller than 0 or greater than 7.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException If the member does not belong to this guild.
     * @see ErrorResponse#UNKNOWN_MEMBER
     *
     * @param member The member.
     * @param days The number of days to delete the member's message. Only valid between 0 and 7.
     * @return A boolean {@link AuditAction}, used to attach reason (or not).
     * The boolean value will be true if the member is banned successfully.
     */
    AuditAction<Boolean> banMember(IMember member, int days);

    /**
     * Ban a member by ID.
     * The number of days to delete messages is 7 by default.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Ban Member} or {@code Administrator} permission.
     * @exception HigherHierarchyException
     *          If the member is at a higher hierarchy than the identity (ie.e server owner)
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException If the member does not belong to this guild.
     * @see ErrorResponse#UNKNOWN_MEMBER
     *
     * @param memberId The member's ID.
     * @return A boolean {@link AuditAction}, used to attach reason (or not).
     * The boolean value will be true if the member is banned successfully.
     */
    AuditAction<Boolean> banMember(String memberId);

    /**
     * Ban a member by ID.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Ban Member} or {@code Administrator} permission.
     * @exception HigherHierarchyException
     *          If the member is at a higher hierarchy than the identity (ie.e server owner)
     * @exception IllegalArgumentException If the days are smaller than 0 or greater than 7.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException If the member does not belong to this guild.
     * @see ErrorResponse#UNKNOWN_MEMBER
     *
     * @param memberId The member's ID.
     * @param days The number of days to delete the member's message. Only valid between 0 and 7.
     * @return A boolean {@link AuditAction}, used to attach reason (or not).
     * The boolean value will be true if the member is banned successfully.
     */
    AuditAction<Boolean> banMember(String memberId, int days);

    /**
     * Unban a user.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Ban Member} or {@code Administrator} permission.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException If the user is not in the guild ban list.
     * @see ErrorResponse#UNKNOWN_USER
     *
     * @param user The user.
     * @return A boolean {@link AuditAction}, used to attach reason (or not).
     * The boolean value will be true if the member is unbanned successfully.
     */
    AuditAction<Boolean> unbanUser(IUser user);

    /**
     * Unban a member by ID.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Ban Member} or {@code Administrator} permission.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException If the user is not in the guild ban list.
     * @see ErrorResponse#UNKNOWN_USER
     *
     * @param memberId The user's ID.
     * @return A boolean {@link AuditAction}, used to attach reason (or not).
     * The boolean value will be true if the member is unbanned successfully.
     */
    AuditAction<Boolean> unbanUser(String memberId);

    /**
     * Create a text channel in this guild.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Channels} permission.
     *
     * @param channel The channel built by {@link org.alienideology.jcord.handle.builders.ChannelBuilder},
     *                or another channel instance to clone from.
     * @see ChannelBuilder#buildTextChannel()
     * @return A {@link ITextChannel} {@link AuditAction}. The generic text channel will be the channel created.
     */
    AuditAction<ITextChannel> createTextChannel(ITextChannel channel);

    /**
     * Create a voice channel in this guild.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Channels} permission.
     *
     * @param channel The channel built by {@link org.alienideology.jcord.handle.builders.ChannelBuilder},
     *                or another channel instance to clone from.
     * @see ChannelBuilder#buildVoiceChannel()
     * @return A {@link IVoiceChannel} {@link AuditAction}. The generic text channel will be the channel created.
     */
    AuditAction<IVoiceChannel> createVoiceChannel(IVoiceChannel channel);

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
     * @return A {@link Void} {@link AuditAction}, used to attach audit log reason.
     */
    AuditAction<Void> deleteGuildChannel(IGuildChannel channel);

    /**
     * Create a role in this guild.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     *
     * @param role The role built by {@link RoleBuilder}, or another role instance to clone from.
     * @return A {@link IRole} {@link AuditAction}. The generic role will be the role created.
     */
    AuditAction<IRole> createRole(IRole role);

    /**
     * Delete a existed role in this guild.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If the role is null or if the role is not from this guild.
     * @see ErrorResponse#UNKNOWN_ROLE
     *
     * @param role The role to be deleted.
     * @return A {@link Void} {@link AuditAction}, used to attach audit log reason.
     */
    AuditAction<Void> deleteRole(IRole role);

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
     * @see ErrorResponse#UNKNOWN_CHANNEL
     * @return An {@link IWebhook} {@link AuditAction}, used to attach reason for creating this webhook.
     */
    default AuditAction<IWebhook> createWebhook(ITextChannel channel, String defaultName, Icon defaultAvatar) {
        if (!channel.getGuild().equals(getGuild())) {
            throw new ErrorResponseException(ErrorResponse.UNKNOWN_CHANNEL);
        }
        return channel.getManager().createWebhook(defaultName, defaultAvatar);
    }

    /**
     * Delete a webhook.
     *
     * @param webhook The webhook be to deleted.
     *
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If the webhook does not belong to this guild.
     * @see ErrorResponse#UNKNOWN_USER
     * @return A {@link Void} {@link AuditAction}, used to attach audit log reason.
     */
    default AuditAction<Void> deleteWebhook(IWebhook webhook) {
        if (!webhook.getGuild().equals(getGuild())) {
            throw new ErrorResponseException(ErrorResponse.UNKNOWN_USER);
        }
        return webhook.getManager().delete();
    }

}
