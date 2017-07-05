package org.alienideology.jcord.event.message;


import org.jetbrains.annotations.Nullable;
import org.alienideology.jcord.handle.message.IMessage;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.MessageChannel;

import java.time.OffsetDateTime;

/**
 * @author AlienIdeology
 */
public class MessageDeleteEvent extends MessageEvent {

    private String id;
    private OffsetDateTime deleteTimeStamp;

    public MessageDeleteEvent(IdentityImpl identity, int sequence, MessageChannel channel, String id, OffsetDateTime deleteTimeStamp) {
        super(identity, sequence, channel, null);
        this.id = id;
        this.deleteTimeStamp = deleteTimeStamp;
    }

    @Override
    public String getMessageId() {
        return id;
    }

    public OffsetDateTime getDeleteTimeStamp() {
        return deleteTimeStamp;
    }

    /**
     * @return Always null because the message is deleted.
     */
    @Override
    @Nullable
    public IMessage getMessage() {
        return super.getMessage();
    }
}
