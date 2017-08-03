package org.alienideology.jcord.event.channel;

import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.Channel;

/**
 * @author AlienIdeology
 */
public class ChannelCreateEvent extends ChannelEvent {

    public ChannelCreateEvent(IdentityImpl identity, int sequence, Channel channel) {
        super(identity, sequence, channel);
    }

}
