package org.alienideology.jcord.internal.event.message;

import org.alienideology.jcord.internal.Identity;
import org.alienideology.jcord.internal.object.Message;
import org.alienideology.jcord.internal.object.channel.MessageChannel;

/**
 * @author AlienIdeology
 */
public class MessageUpdateEvent extends MessageEvent {

    public MessageUpdateEvent(Identity identity, int sequence, MessageChannel channel, Message message) {
        super(identity, sequence, channel, message);
    }

}
