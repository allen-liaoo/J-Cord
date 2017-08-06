package org.alienideology.jcord.event.channel;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.channel.IChannel;

/**
 * @author AlienIdeology
 */
public class ChannelCreateEvent extends ChannelEvent {

    public ChannelCreateEvent(Identity identity, int sequence, IChannel channel) {
        super(identity, sequence, channel);
    }

}
