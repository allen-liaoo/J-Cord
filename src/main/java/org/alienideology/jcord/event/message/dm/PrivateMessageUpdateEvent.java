package org.alienideology.jcord.event.message.dm;

import com.sun.istack.internal.NotNull;
import org.alienideology.jcord.handle.channel.IPrivateChannel;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.event.message.MessageUpdateEvent;
import org.alienideology.jcord.internal.object.message.Message;
import org.alienideology.jcord.internal.object.channel.MessageChannel;

/**
 * @author AlienIdeology
 */
public class PrivateMessageUpdateEvent extends MessageUpdateEvent implements IPrivateMessageEvent {

    public PrivateMessageUpdateEvent(IdentityImpl identity, int sequence, MessageChannel channel, Message message) {
        super(identity, sequence, channel, message);
    }

    @Override
    @NotNull
    public IPrivateChannel getPrivateChannel() {
        return super.getPrivateChannel();
    }
}
