package org.alienideology.jcord.event.channel;

import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.Channel;

import java.time.OffsetDateTime;

/**
 * @author AlienIdeology
 */
public class ChannelDeleteEvent extends ChannelEvent {

    private OffsetDateTime timeStamp;

    public ChannelDeleteEvent(IdentityImpl identity, int sequence, Channel channel, OffsetDateTime timeStamp) {
        super(identity, sequence, channel);
        this.timeStamp = timeStamp;
    }

    public OffsetDateTime getTimeStamp() {
        return timeStamp;
    }

}
