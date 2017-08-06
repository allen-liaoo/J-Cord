package org.alienideology.jcord.event.channel.guild.text.update;

import org.alienideology.jcord.event.channel.guild.text.TextChannelUpdateEvent;
import org.alienideology.jcord.handle.channel.IChannel;
import org.alienideology.jcord.internal.object.IdentityImpl;

/**
 * @author AlienIdeology
 */
public class TextChannelNameUpdateEvent extends TextChannelUpdateEvent {

    private final String oldName;

    public TextChannelNameUpdateEvent(IdentityImpl identity, int sequence, IChannel channel, String oldName) {
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
