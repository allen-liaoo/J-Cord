package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.object.Message;
import org.json.JSONObject;

/**
 * @author AlienIdeology
 */
public class MessageUpdateEventHandler extends EventHandler {

    public MessageUpdateEventHandler(Identity identity) {
        super(identity);
    }

    @Override
    public void dispatchEvent(JSONObject json, int sequence) {
        Message edited = builder.buildMessageById(json.getString("channel_id"), json.getString("id"));

        if (!edited.isFromSelf()) { // Not from self

        }
    }

}
