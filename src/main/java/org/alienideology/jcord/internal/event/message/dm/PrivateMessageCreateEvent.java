package org.alienideology.jcord.internal.event.message.dm;

import com.sun.istack.internal.NotNull;
import org.alienideology.jcord.internal.Identity;
import org.alienideology.jcord.internal.event.message.MessageCreateEvent;
import org.alienideology.jcord.internal.object.channel.MessageChannel;
import org.alienideology.jcord.internal.object.channel.PrivateChannel;
import org.alienideology.jcord.internal.object.Message;

/**
 * @author AlienIdeology
 */
public class PrivateMessageCreateEvent extends MessageCreateEvent implements IPrivateMessageEvent {

    public PrivateMessageCreateEvent(Identity identity, int sequence, MessageChannel channel, Message message) {
        super(identity, sequence, channel, message);
    }

    @Override
    @NotNull
    public PrivateChannel getPrivateChannel() {
        return super.getPrivateChannel();
    }

}