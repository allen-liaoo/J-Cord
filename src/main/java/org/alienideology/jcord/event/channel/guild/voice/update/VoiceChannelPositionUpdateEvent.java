package org.alienideology.jcord.event.channel.guild.voice.update;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.channel.guild.voice.VoiceChannelUpdateEvent;
import org.alienideology.jcord.handle.channel.IChannel;

/**
 * @author AlienIdeology
 */
public class VoiceChannelPositionUpdateEvent extends VoiceChannelUpdateEvent {

    private final int oldPosition;

    public VoiceChannelPositionUpdateEvent(Identity identity, int sequence, IChannel channel, int oldPosition) {
        super(identity, sequence, channel);
        this.oldPosition = oldPosition;
    }

    public int getNewPosition() {
        return channel.getPosition();
    }

    public int getOldPosition() {
        return oldPosition;
    }

}
