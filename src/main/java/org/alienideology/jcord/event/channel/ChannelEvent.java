package org.alienideology.jcord.event.channel;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.Event;
import org.alienideology.jcord.handle.channel.IChannel;

/**
 * @author AlienIdeology
 */
public class ChannelEvent extends Event {

    protected IChannel channel;

    public ChannelEvent(Identity identity, int sequence, IChannel channel) {
        super(identity, sequence);
        this.channel = channel;
    }

    public IChannel getChannel() {
        return channel;
    }

}
