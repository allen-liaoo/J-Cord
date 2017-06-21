package org.alienideology.jcord.event.message;

import org.alienideology.jcord.handle.message.IMessage;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.MessageChannel;
import org.alienideology.jcord.internal.object.message.Message;

import java.time.OffsetDateTime;

/**
 * @author AlienIdeology
 */
public class MessagePinUpdateEvent extends MessageEvent {

    private OffsetDateTime timeStamp;

    public MessagePinUpdateEvent(IdentityImpl identity, int sequence, MessageChannel channel, Message message, String timeStamp) {
        super(identity, sequence, channel, message);
        this.timeStamp = timeStamp == null ? null : OffsetDateTime.parse(timeStamp);
    }

    /**
     * The message will always be null.
     *
     * @return null.
     */
    @Override
    public IMessage getMessage() {
        return super.getMessage();
    }

    public OffsetDateTime getTimeStamp() {
        return timeStamp;
    }

}
