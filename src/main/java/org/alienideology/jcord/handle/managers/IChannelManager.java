package org.alienideology.jcord.handle.managers;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.channel.IGuildChannel;
import org.alienideology.jcord.handle.channel.ITextChannel;
import org.alienideology.jcord.handle.channel.IVoiceChannel;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IMember;
import org.alienideology.jcord.handle.guild.IRole;
import org.alienideology.jcord.handle.permission.PermOverwrite;
import org.alienideology.jcord.handle.permission.Permission;

import java.util.Collection;
import java.util.List;

/**
 * IChannelManager - A manager for both text and voice channels.
 *
 * @author AlienIdeology
 */
public interface IChannelManager {

    /**
     * The minimum length of the channel's name.
     */
    int CHANNEL_NAME_LENGTH_MIN = 2;
    /**
     * The maximum length of the channel's name.
     */
    int CHANNEL_NAME_LENGTH_MAX = 100;
    /**
     * The maximum length of the text channel's topic.
     */
    int TEXT_CHANNEL_TOPIC_LENGTH_MAX = 2014;
    /**
     * The minimum bitrate of a voice channel.
     */
    int VOICE_CHANNEL_BITRATE_MIN = 8000;
    /**
     * The maximum bitrate of a voice channel in a normal server.
     */
    int VOICE_CHANNEL_BITRATE_MAX = 96000;
    /**
     * The maximum bitrate of a voice channel in a VIP server.
     */
    int VOICE_CHANNEL_BITRATE_VIP_MAX = 128000;
    /**
     * The minimum user limit of a voice channel.
     * {code 0} user limit refers to no limit.
     */
    int VOICE_CHANNEL_USER_LIMIT_MIN = 0;
    /**
     * The maximum user limit of a voice channel.
     */
    int VOICE_CHANNEL_USER_LIMIT_MAX = 99;

    /**
     * Get the identity this channel managers belongs to.
     *
     * @return The identity.
     */
    Identity getIdentity();

    /**
     * Get the guild this channel managers manages channel from.
     *
     * @return The guild.
     */
    IGuild getGuild();

    /**
     * Get the channel this channel managers manages.
     *
     * @return The channel. Can be {@link ITextChannel} or {@link IVoiceChannel}.
     */
    IGuildChannel getGuildChannel();

    /**
     * Modify the name of this channel.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          <ul>
     *              <li>If the identity do not have {@code Manage Channels} permission.</li>
     *              <li>If the name is shorter than {@value #CHANNEL_NAME_LENGTH_MIN} or longer than {@value #CHANNEL_NAME_LENGTH_MAX}.</li>
     *          </ul>
     *
     * @param name The new name.
     */
    void modifyName(String name);

    /**
     * Modify the position of this channel.
     * The position of a channel can be calculated by counting down the channel list, starting from 1.
     * Note that voice channels is a separate list than text channels in a guild.
     * @see IGuildChannel#getPosition()
     *
     * If the {@code position} is 0, then it will be the first in the channel list.
     * Use {@link IGuild#getTextChannels()} or {@link IGuild#getVoiceChannels()}, then {@link List#size()} to get the last
     * position in the channel list.
     *
     * Note that there are no limits to the position. If the position is too small or large, no exception will be thrown.
     * The channel will simply by placed at the start or the end of the channel list, respectively.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity do not have {@code Manage Channels} permission.
     *
     * @param position The new position.
     */
    void modifyPosition(int position);

    /**
     * Moves the channel by an amount of positions.
     * Use a negative amount to move the channel up.
     * @see #modifyPosition(int)
     *
     * @param amount The offset, or amount of channels to move by.
     */
    void moveChannelBy(int amount);

    /**
     * Modify the topic of a {@link ITextChannel}.
     * It is important to understand that this method will only work for text channels.
     * Use empty or null topic parameter to reset the topic to none.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity do not have {@code Manage Channels} permission.
     * @exception IllegalArgumentException
     *          <ul>
     *              <li>If the channel managers manages a {@link IVoiceChannel}.</li>
     *              <li>If the topic is longer than {@value #TEXT_CHANNEL_TOPIC_LENGTH_MAX}.</li>
     *          </ul>
     *
     * @param topic The new topic.
     */
    void modifyTopic(String topic);

    /**
     * Modify the bitrate of a {@link IVoiceChannel}.
     * Note that this method will only work for voice channels.
     *
     * If the guild is a normal guild, the bitrate cannot be higher than {@value #VOICE_CHANNEL_BITRATE_MAX}.
     * If the guild is a VIP guild, then the bitrate limit is {@value #VOICE_CHANNEL_BITRATE_VIP_MAX}.
     * @see IVoiceChannel#getBitrate() For more information on bitrate.
     * @see IGuild#getSplash() Normal guilds' splash will always be null.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity do not have {@code Manage Channels} permission.
     * @exception IllegalArgumentException
     *          <ul>
     *              <li>If the channel managers manages a {@link ITextChannel}.</li>
     *              <li>If the bitrate is smaller than {@value #VOICE_CHANNEL_BITRATE_MIN}.</li>
     *              <li>The bitrate exceeds the limit. (For normal guild, the limit is {@value #VOICE_CHANNEL_BITRATE_MAX}.
     *                  For VIP guild, the limit is {@value #VOICE_CHANNEL_BITRATE_VIP_MAX}.</li>
     *          </ul>
     *
     * @param bitrate The new bitrate.
     */
    void modifyBitrate(int bitrate);

    /**
     * Modify the user limit of a {@link IVoiceChannel}.
     * This method will only work for voice channels.
     * Use 0 for no user limits. Note that negative limits will throw {@link IllegalArgumentException}.
     * @see IVoiceChannel#getUserLimit() For more information on user limit.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity do not have {@code Manage Channels} permission.
     * @exception IllegalArgumentException
     *          <ul>
     *              <li>If the channel managers manages a {@link ITextChannel}.</li>
     *              <li>If the bitrate is smaller than {@value #VOICE_CHANNEL_USER_LIMIT_MIN} or greater than {@value #VOICE_CHANNEL_USER_LIMIT_MAX}.</li>
     *          </ul>
     *
     * @param limit The new limit.
     */
    void modifyUserLimit(int limit);

    /**
     * Deletes the guild channel.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity do not have {@code Manage Channels} permission.
     * @exception IllegalArgumentException
     *          If the channel is a text channel, and the text channel is a default channel.
     *          @see IGuild#getDefaultChannel() For more information.
     */
    default void deleteChannel() {
        getGuild().getGuildManager().deleteGuildChannel(getGuildChannel());
    }

    /**
     * Edit or add {@link PermOverwrite} to a member in this channel.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manager Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the member is at a higher or same hierarchy than the identity.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If the member does not belong to this guild.
     *          @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_MEMBER
     *
     * @param member The member to add permission overwrites.
     * @param allowed The allowed permissions.
     * @param denied The denied permissions.
     */
    void editPermOverwrite(IMember member, Collection<Permission> allowed, Collection<Permission> denied);

    /**
     * Edit or add {@link PermOverwrite} to a role in this channel.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manager Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the role is at a higher or same hierarchy than the identity.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If the role does not belong to this guild.
     *          @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_ROLE
     *
     * @param role The role to add permission overwrites.
     * @param allowed The allowed permissions.
     * @param denied The denied permissions.
     */
    void editPermOverwrite(IRole role, Collection<Permission> allowed, Collection<Permission> denied);

    /**
     * Deletes a permission overwrite by ID.
     * The ID can be a member id, role id or the {@link PermOverwrite} id.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manager Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the member or role is at a higher or same hierarchy than the identity.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If the member or role does not belong to this guild.
     *          @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_OVERWRITE
     *
     * @param id The id.
     */
    void deletePermOverwrite(String id);

}
