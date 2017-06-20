package org.alienideology.jcord.handle.builders;

import org.alienideology.jcord.handle.channel.ITextChannel;
import org.alienideology.jcord.handle.channel.IVoiceChannel;
import org.alienideology.jcord.internal.object.channel.TextChannel;
import org.alienideology.jcord.internal.object.channel.VoiceChannel;
import org.alienideology.jcord.internal.object.managers.ChannelManager;

import static org.alienideology.jcord.handle.managers.IChannelManager.VOICE_CHANNEL_BITRATE_VIP_MAX;

/**
 * ChannelBuilder - A GuildChannel builder for creating text and voice channels.
 *
 * @author AlienIdeology
 * @since 0.0.6
 */
public final class ChannelBuilder {

    private String name;

    private int bitrate;
    private int userLimit;

//    private List<PermOverwrite> overwrites = new ArrayList<>();

    // TODO: PermOverwrites
    public ChannelBuilder() {
    }

    /**
     * Built a text channel.
     *
     * @return The text channel built.
     */
    public ITextChannel buildTextChannel() {
        return new TextChannel(null, null, null, name, 0, null, null);
    }

    /**
     * Built a voice channel.
     *
     * @return The voice channel built.
     */
    public IVoiceChannel buildVoiceChannel() {
        return new VoiceChannel(null, null, null, name, 0, bitrate, userLimit);
    }

    /**
     * Set the name of this {@link org.alienideology.jcord.handle.channel.IGuildChannel}.
     * The name can be set by both {@link ITextChannel} or {@link IVoiceChannel}.
     *
     * @exception IllegalArgumentException
     *          If the name is shorter than {@value org.alienideology.jcord.handle.managers.IChannelManager#CHANNEL_NAME_LENGTH_MIN} or longer than
     *          {@value org.alienideology.jcord.handle.managers.IChannelManager#CHANNEL_NAME_LENGTH_MAX}.
     *
     * @param name The name of the channel.
     */
    public ChannelBuilder setName(String name) {
        if (name == null) name = "";
        ChannelManager.checkName(name);
        this.name = name;
        return this;
    }

    /**
     * Set the bitrate of this {@link IVoiceChannel}.
     * Setting the bitrate will not affect anything if this builder is used to build a {@link ITextChannel}.
     * @see IVoiceChannel#getBitrate()
     *
     * @exception IllegalArgumentException
     *          <ul>
     *              <li>If the bitrate is smaller than {@value org.alienideology.jcord.handle.managers.IChannelManager#VOICE_CHANNEL_BITRATE_MIN}.</li>
     *              <li>The bitrate is greater than {@value org.alienideology.jcord.handle.managers.IChannelManager#VOICE_CHANNEL_BITRATE_VIP_MAX}.
     *                  Note that the builder does not knows if the guild this channel will be created in is VIP or not, so the limit here is for VIP.
     *                  You must check if the guild is vip or not, otherwise there might be exception thrown when creating a channel via this builder.</li>
     *          </ul>
     *
     * @param bitrate The integer bitrate.
     */
    public ChannelBuilder setBitrate(int bitrate) {
        if (bitrate > VOICE_CHANNEL_BITRATE_VIP_MAX) {
            throw new IllegalArgumentException("The bitrate of a vip guild can not be greater than "+VOICE_CHANNEL_BITRATE_VIP_MAX+"!");
        }
        try { ChannelManager.checkBitrate(bitrate, null); } catch (NullPointerException ignored) {}
        this.bitrate = bitrate;
        return this;
    }

    /**
     * Set the user limit of this {@link IVoiceChannel}.
     * Setting the user limit will not affect anything if this builder is used to build a {@link ITextChannel}.
     * @see IVoiceChannel#getUserLimit()
     *
     * @exception IllegalArgumentException
     *          If the bitrate is smaller than {@value org.alienideology.jcord.handle.managers.IChannelManager#VOICE_CHANNEL_USER_LIMIT_MIN}
     *          or greater than {@value org.alienideology.jcord.handle.managers.IChannelManager#VOICE_CHANNEL_USER_LIMIT_MAX}.
     *
     * @param userLimit The user limit.
     */
    public ChannelBuilder setUserLimit(int userLimit) {
        ChannelManager.checkUserLimit(userLimit);
        this.userLimit = userLimit;
        return this;
    }

}
