package org.alienideology.jcord.handle.modifiers;

import org.alienideology.jcord.handle.audit.AuditAction;
import org.alienideology.jcord.handle.channel.IGuildChannel;
import org.alienideology.jcord.handle.channel.ITextChannel;
import org.alienideology.jcord.handle.channel.IVoiceChannel;
import org.alienideology.jcord.handle.guild.IGuild;

import java.util.List;

/**
 * IChannelModifier - A modifier that modifies both {@link ITextChannel} and {@link IVoiceChannel}.
 *
 * @author AlienIdeology
 */
public interface IChannelModifier extends IModifier<AuditAction<Void>> {

    /**
     * Get the guild this channel modifier belongs to.
     *
     * @return The guild.
     */
    default IGuild getGuild() {
        return getGuildChannel().getGuild();
    }

    /**
     * Get the guild channel this channel modifier modifies.
     *
     * @return The guild channel.
     */
    IGuildChannel getGuildChannel();

    /**
     * Modify the channel's name.
     * Use empty or {@code null} string to reset the name.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          <ul>
     *              <li>If the identity do not have {@code Manage Channels} permission.</li>
     *              <li>If the name is shorter than {@value IGuildChannel#NAME_LENGTH_MIN} or longer than {@value IGuildChannel#NAME_LENGTH_MAX}.</li>
     *          </ul>
     * @exception IllegalArgumentException
     *          If the name is not valid. See {@link IGuildChannel#isValidChannelName(String)}.
     *
     * @param name The name.
     * @return IChannelModifier for chaining.
     */
    IChannelModifier name(String name);

    /**
     * Modify the channel's position.
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
     * @param position The position.
     * @return IChannelModifier for chaining.
     */
    IChannelModifier position(int position);

    /**
     * Modify the {@link ITextChannel}'s topic.
     * Use empty or {@code null} string to reset the topic.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity do not have {@code Manage Channels} permission.
     * @exception IllegalArgumentException
     *          <ul>
     *              <li>If the channel managers manages a {@link IVoiceChannel}.</li>
     *              <li>If the topic is longer than {@value ITextChannel#TOPIC_LENGTH_MAX}.</li>
     *          </ul>
     *
     * @param topic The topic.
     * @return IChannelModifier for chaining.
     */
    IChannelModifier topic(String topic);

    /**
     * Modify the {@link IVoiceChannel}'s bitrate.
     *
     * If the guild is a normal guild, the bitrate cannot be higher than {@value IVoiceChannel#BITRATE_MAX}.
     * If the guild is a VIP guild, then the bitrate limit is {@value IVoiceChannel#VOICE_CHANNEL_BITRATE_VIP_MAX}.
     * @see IVoiceChannel#getBitrate() For more information on bitrate.
     * @see IGuild#getSplash() Normal guilds' splash will always be null.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity do not have {@code Manage Channels} permission.
     * @exception IllegalArgumentException
     *          <ul>
     *              <li>If the channel managers manages a {@link ITextChannel}.</li>
     *              <li>If the bitrate is not valid. See {@link IVoiceChannel#isValidBitrate(int, IGuild)} with VIP guild..</li>
     *          </ul>
     *
     * @param bitrate The new bitrate.
     * @return IChannelModifier for chaining.
     */
    IChannelModifier bitrate(int bitrate);

    /**
     * Modify the {@link IVoiceChannel}'s user limit.
     * Use 0 for no user limits. Note that negative limits will throw {@link IllegalArgumentException}.
     * @see IVoiceChannel#getUserLimit() For more information on user limit.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity do not have {@code Manage Channels} permission.
     * @exception IllegalArgumentException
     *          If the user limit is not valid. See {@link IVoiceChannel#isValidUserLimit(int)}.
     *
     * @param limit The new limit.
     * @return A void {@link AuditAction}, used to attach reason to the modify action.
     */
    IChannelModifier userLimit(int limit);

    /**
     * Get the name attribute, used to modify the channel's name.
     *
     * @return The name attribute.
     */
    Attribute<IChannelModifier, String> getNameAttr();

    /**
     * Get the position attribute, used to modify the channel's position.
     *
     * @return The position attribute.
     */
    Attribute<IChannelModifier, Integer> getPositionAttr();

    /**
     * Get the topic attribute, used to modify the {@link ITextChannel}'s topic.
     *
     * @return The topic attribute.
     */
    Attribute<IChannelModifier, String> getTopicAttr();

    /**
     * Get the bitrate attribute, used to modify the {@link IVoiceChannel}'s bitrate.
     *
     * @return The bitrate attribute.
     */
    Attribute<IChannelModifier, Integer> getBitrateAttr();

    /**
     * Get the user limit attribute, used to modify the {@link IVoiceChannel}'s user limit.
     *
     * @return The user limit attribute.
     */
    Attribute<IChannelModifier, Integer> getUserLimitAttr();

}
