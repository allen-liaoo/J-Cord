package org.alienideology.jcord.event.channel.dm;

import org.alienideology.jcord.event.channel.ChannelDeleteEvent;
import org.alienideology.jcord.handle.channel.IPrivateChannel;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.Channel;

import java.time.OffsetDateTime;

/**
 * @author AlienIdeology
 */
public class PrivateChannelDeleteEvent extends ChannelDeleteEvent implements IPrivateChannelEvent {

    public PrivateChannelDeleteEvent(IdentityImpl identity, int sequence, Channel channel, OffsetDateTime timeStamp) {
        super(identity, sequence, channel, timeStamp);
    }

    @Override
    public IPrivateChannel getPrivateChannel() {
        return (IPrivateChannel) channel;
    }

}
