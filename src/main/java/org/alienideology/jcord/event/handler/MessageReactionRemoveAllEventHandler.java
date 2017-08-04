package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.event.message.MessageReactionRemoveAllEvent;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.MessageChannel;
import org.alienideology.jcord.internal.object.message.Message;
import org.alienideology.jcord.util.log.LogLevel;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author AlienIdeology
 */
public class MessageReactionRemoveAllEventHandler extends EventHandler {

    public MessageReactionRemoveAllEventHandler(IdentityImpl identity) {
        super(identity);
    }

    @Override
    public void dispatchEvent(JSONObject json, int sequence) {
        MessageChannel channel = (MessageChannel) identity.getMessageChannel(json.getString("channel_id"));
        if (channel == null) {
            logger.log(LogLevel.FETAL, "[UNKNOWN CHANNEL] [MESSAGE_REACTION_REMOVE_ALL]");
            return;
        }

        Message message = (Message) channel.getMessage(json.getString("message_id"));
        MessageReactionRemoveAllEvent event = new MessageReactionRemoveAllEvent(identity, sequence, channel, message, new ArrayList<>(message.getReactions()));
        message.getReactions().clear();
        dispatchEvent(event);
    }

}
