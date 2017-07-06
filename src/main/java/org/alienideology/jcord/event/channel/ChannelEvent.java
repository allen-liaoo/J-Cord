package org.alienideology.jcord.event.channel;

import org.alienideology.jcord.event.Event;
import org.alienideology.jcord.handle.channel.IChannel;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.Channel;

/**
 * @author AlienIdeology
 */
public class ChannelEvent extends Event {

    protected Channel channel;

    public ChannelEvent(IdentityImpl identity, int sequence, Channel channel) {
        super(identity, sequence);
        this.channel = channel;
    }

    public IChannel getChannel() {
        return channel;
    }

}
