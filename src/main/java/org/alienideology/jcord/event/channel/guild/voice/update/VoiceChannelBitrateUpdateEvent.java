package org.alienideology.jcord.event.channel.guild.voice.update;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.channel.guild.voice.VoiceChannelUpdateEvent;
import org.alienideology.jcord.handle.channel.IChannel;

/**
 * @author AlienIdeology
 */
public class VoiceChannelBitrateUpdateEvent extends VoiceChannelUpdateEvent {

    private final int oldBitrate;

    public VoiceChannelBitrateUpdateEvent(Identity identity, int sequence, IChannel channel, int oldBitrate) {
        super(identity, sequence, channel);
        this.oldBitrate = oldBitrate;
    }

    public int getNewBitrate() {
        return channel.getBitrate();
    }

    public int getOldBitrate() {
        return oldBitrate;
    }

}
