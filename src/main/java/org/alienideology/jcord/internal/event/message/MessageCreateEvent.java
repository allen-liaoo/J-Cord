package org.alienideology.jcord.internal.event.message;

import org.alienideology.jcord.internal.Identity;
import org.alienideology.jcord.internal.object.channel.MessageChannel;
import org.alienideology.jcord.internal.object.Message;

/**
 * @author AlienIdeology
 */
public class MessageCreateEvent extends MessageEvent {

    public MessageCreateEvent(Identity identity, int sequence, MessageChannel channel, Message message) {
        super(identity, sequence, channel, message);
    }

}
