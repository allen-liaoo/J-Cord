package org.alienideology.jcord.event.channel.guild.voice.update;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.channel.guild.voice.VoiceChannelUpdateEvent;
import org.alienideology.jcord.handle.channel.IChannel;

/**
 * @author AlienIdeology
 */
public class VoiceChannelUserLimitUpdateEvent extends VoiceChannelUpdateEvent {

    private final int oldUserLimit;

    public VoiceChannelUserLimitUpdateEvent(Identity identity, int sequence, IChannel channel, int oldUserLimit) {
        super(identity, sequence, channel);
        this.oldUserLimit = oldUserLimit;
    }

    public int getNewUserLimit() {
        return channel.getUserLimit();
    }

    public int getOldUserLimit() {
        return oldUserLimit;
    }

}
