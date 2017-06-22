package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.internal.object.IdentityImpl;
import org.json.JSONObject;

/**
 * @author AlienIdeology
 */
// TODO: Finish Message Reaction Events. Should I divide it into private and guild?
public class MessageReactionAddEventHandler extends EventHandler {

    public MessageReactionAddEventHandler(IdentityImpl identity) {
        super(identity);
    }

    @Override
    public void dispatchEvent(JSONObject json, int sequence) {
        String channelId = json.getString("channel_id");
        String messageId = json.getString("message_id");
        String userId = json.getString("user_id");


    }

}
