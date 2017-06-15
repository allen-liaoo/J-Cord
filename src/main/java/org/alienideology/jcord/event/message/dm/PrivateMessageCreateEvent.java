package org.alienideology.jcord.event.message.dm;

import com.sun.istack.internal.NotNull;
import org.alienideology.jcord.handle.channel.IPrivateChannel;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.event.message.MessageCreateEvent;
import org.alienideology.jcord.internal.object.channel.MessageChannel;
import org.alienideology.jcord.internal.object.message.Message;

/**
 * @author AlienIdeology
 */
public class PrivateMessageCreateEvent extends MessageCreateEvent implements IPrivateMessageEvent {

    public PrivateMessageCreateEvent(IdentityImpl identity, int sequence, MessageChannel channel, Message message) {
        super(identity, sequence, channel, message);
    }

    @Override
    @NotNull
    public IPrivateChannel getPrivateChannel() {
        return super.getPrivateChannel();
    }

}
