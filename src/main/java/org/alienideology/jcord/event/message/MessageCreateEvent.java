package org.alienideology.jcord.event.message;

import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.MessageChannel;
import org.alienideology.jcord.internal.object.message.Message;

/**
 * @author AlienIdeology
 */
public class MessageCreateEvent extends MessageEvent {

    public MessageCreateEvent(IdentityImpl identity, int sequence, MessageChannel channel, Message message) {
        super(identity, sequence, channel, message);
    }

}
