package org.alienideology.jcord.event.channel;

import com.sun.istack.internal.Nullable;
import org.alienideology.jcord.handle.channel.*;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.Channel;

/**
 * @author AlienIdeology
 */
public class ChannelCreateEvent extends ChannelEvent {

    public ChannelCreateEvent(IdentityImpl identity, int sequence, Channel channel) {
        super(identity, sequence, channel);
    }

    /**
     * Check if the channel created is a private channel or not.
     *
     * @return True if it is a private channel.
     */
    public boolean isPrivateChannel() {
        return channel.isPrivate();
    }

    /**
     * Returns the private channel created.
     *
     * @return Returns the channel, or null if the channel created is a guild channel.
     */
    @Nullable
    public IPrivateChannel getPrivateChannel() {
        return isPrivateChannel() ? (IPrivateChannel) channel : null;
    }

    /**
     * Returns the guild channel created.
     *
     * @return Returns the channel, or null if the channel created is a prvate channel.
     */
    @Nullable
    public IGuildChannel getGuildChannel() {
        return !isPrivateChannel() ? (IGuildChannel) channel : null;
    }

    /**
     * Returns the text channel created.
     *
     * @return Returns the channel, or null if the channel created is not a text channel.
     */
    @Nullable
    public ITextChannel getTextChannel() {
        return !isPrivateChannel() && channel.isType(IChannel.Type.TEXT) ? (ITextChannel) channel : null;
    }

    /**
     * Returns the voice channel created.
     *
     * @return Returns the channel, or null if the channel created is not a voice channel.
     */
    @Nullable
    public IVoiceChannel getVoiceChannel() {
        return !isPrivateChannel() && channel.isType(IChannel.Type.VOICE) ? (IVoiceChannel) channel : null;
    }

}
