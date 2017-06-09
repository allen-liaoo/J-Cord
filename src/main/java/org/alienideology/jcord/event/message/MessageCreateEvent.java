package org.alienideology.jcord.event.message;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.object.channel.MessageChannel;
import org.alienideology.jcord.object.Message;

/**
 * @author AlienIdeology
 */
public class MessageCreateEvent extends MessageEvent {

    public MessageCreateEvent(Identity identity, int sequence, MessageChannel channel, Message message) {
        super(identity, sequence, channel, message);
    }

}
