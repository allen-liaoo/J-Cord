package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.message.MessageDeleteEvent;
import org.alienideology.jcord.object.channel.MessageChannel;
import org.json.JSONObject;

/**
 * @author AlienIdeology
 */
public class MessageDeleteEventHandler extends EventHandler {

    public MessageDeleteEventHandler(Identity identity) {
        super(identity);
    }

    @Override
    public void dispatchEvent(JSONObject json, int sequence) {
        String msg_id = json.getString("id");
        String channel_id = json.getString("channel_id");

        MessageChannel channel = identity.getMessageChannel(channel_id);
        if (channel != null) {
            fireEvent(new MessageDeleteEvent(identity, sequence, channel, msg_id));

        } else {
            identity.LOG.error("Unknown message channel for a MessageDeleteEvent!");
        }
    }
}
