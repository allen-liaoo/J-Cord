package org.alienideology.jcord.event.message.dm;

import org.jetbrains.annotations.NotNull;
import org.alienideology.jcord.handle.channel.IPrivateChannel;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.event.message.MessageDeleteEvent;
import org.alienideology.jcord.internal.object.channel.MessageChannel;

import java.time.OffsetDateTime;

/**
 * @author AlienIdeology
 */
public class PrivateMessageDeleteEvent extends MessageDeleteEvent implements IPrivateMessageEvent {

    public PrivateMessageDeleteEvent(IdentityImpl identity, int sequence, MessageChannel channel, String id, OffsetDateTime deleteTimStamp) {
        super(identity, sequence, channel, id, deleteTimStamp);
    }

    @Override
    @NotNull
    public IPrivateChannel getPrivateChannel() {
        return super.getPrivateChannel();
    }

}
