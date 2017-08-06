package org.alienideology.jcord.event.channel.guild.voice.update;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.channel.guild.voice.VoiceChannelUpdateEvent;
import org.alienideology.jcord.handle.channel.IChannel;

/**
 * @author AlienIdeology
 */
public class VoiceChannelNameUpdateEvent extends VoiceChannelUpdateEvent {

    private final String oldName;

    public VoiceChannelNameUpdateEvent(Identity identity, int sequence, IChannel channel, String oldName) {
        super(identity, sequence, channel);
        this.oldName = oldName;
    }

    public String getNewName() {
        return channel.getName();
    }

    public String getOldName() {
        return oldName;
    }

}
