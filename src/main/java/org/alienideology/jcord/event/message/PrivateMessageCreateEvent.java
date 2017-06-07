package org.alienideology.jcord.event.message;

import com.sun.istack.internal.NotNull;
import org.alienideology.jcord.Identity;
import org.alienideology.jcord.object.channel.MessageChannel;
import org.alienideology.jcord.object.channel.PrivateChannel;
import org.alienideology.jcord.object.message.Message;

/**
 * @author AlienIdeology
 */
public class PrivateMessageCreateEvent extends MessageCreateEvent {

    public PrivateMessageCreateEvent(Identity identity, int sequence, MessageChannel channel, Message message) {
        super(identity, sequence, channel, message);
    }

    @Override
    @NotNull
    public PrivateChannel getPrivateChannel() {
        return super.getPrivateChannel();
    }

}
