package org.alienideology.jcord.internal.event.message.dm;

import com.sun.istack.internal.NotNull;
import org.alienideology.jcord.internal.Identity;
import org.alienideology.jcord.internal.event.message.MessageDeleteEvent;
import org.alienideology.jcord.internal.object.channel.MessageChannel;
import org.alienideology.jcord.internal.object.channel.PrivateChannel;

import java.time.OffsetDateTime;

/**
 * @author AlienIdeology
 */
public class PrivateMessageDeleteEvent extends MessageDeleteEvent implements IPrivateMessageEvent {

    public PrivateMessageDeleteEvent(Identity identity, int sequence, MessageChannel channel, String id, OffsetDateTime deleteTimStamp) {
        super(identity, sequence, channel, id, deleteTimStamp);
    }

    @Override
    @NotNull
    public PrivateChannel getPrivateChannel() {
        return super.getPrivateChannel();
    }

}
