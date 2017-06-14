package org.alienideology.jcord.event.message;

import org.alienideology.jcord.handle.channel.IMessageChannel;
import org.alienideology.jcord.internal.object.Identity;
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
