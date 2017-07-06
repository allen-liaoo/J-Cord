package org.alienideology.jcord.event.message;

import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.message.Message;
import org.alienideology.jcord.internal.object.channel.MessageChannel;

/**
 * @author AlienIdeology
 */
public class MessageUpdateEvent extends MessageEvent {

    public MessageUpdateEvent(IdentityImpl identity, int sequence, MessageChannel channel, Message message) {
        super(identity, sequence, channel, message);
    }

}
