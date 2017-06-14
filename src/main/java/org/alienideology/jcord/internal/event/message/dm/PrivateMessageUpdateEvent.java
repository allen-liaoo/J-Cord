package org.alienideology.jcord.internal.event.message.dm;

import com.sun.istack.internal.NotNull;
import org.alienideology.jcord.internal.Identity;
import org.alienideology.jcord.internal.event.message.MessageUpdateEvent;
import org.alienideology.jcord.internal.object.Message;
import org.alienideology.jcord.internal.object.channel.MessageChannel;
import org.alienideology.jcord.internal.object.channel.PrivateChannel;

/**
 * @author AlienIdeology
 */
public class PrivateMessageUpdateEvent extends MessageUpdateEvent implements IPrivateMessageEvent {

    public PrivateMessageUpdateEvent(Identity identity, int sequence, MessageChannel channel, Message message) {
        super(identity, sequence, channel, message);
    }

    @Override
    @NotNull
    public PrivateChannel getPrivateChannel() {
        return super.getPrivateChannel();
    }
}
