package org.alienideology.jcord.event.message;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.object.Message;
import org.alienideology.jcord.object.channel.MessageChannel;

/**
 * @author AlienIdeology
 */
public class MessageUpdateEvent extends MessageEvent {

    public MessageUpdateEvent(Identity identity, int sequence, MessageChannel channel, Message message) {
        super(identity, sequence, channel, message);
    }

}
