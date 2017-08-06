package org.alienideology.jcord.event.channel;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.channel.IChannel;

import java.time.OffsetDateTime;

/**
 * @author AlienIdeology
 */
public class ChannelDeleteEvent extends ChannelEvent {

    private final OffsetDateTime timeStamp;

    public ChannelDeleteEvent(Identity identity, int sequence, IChannel channel, OffsetDateTime timeStamp) {
        super(identity, sequence, channel);
        this.timeStamp = timeStamp;
    }

    public OffsetDateTime getTimeStamp() {
        return timeStamp;
    }

}
