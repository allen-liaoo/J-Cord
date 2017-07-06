package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.event.channel.TypingStartEvent;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.MessageChannel;
import org.alienideology.jcord.internal.object.user.User;
import org.json.JSONObject;

/**
 * @author AlienIdeology
 */
public class TypingStartEventHandler extends EventHandler {

    public TypingStartEventHandler(IdentityImpl identity) {
        super(identity);
    }

    @Override
    public void dispatchEvent(JSONObject json, int sequence) {
        MessageChannel channel = (MessageChannel) identity.getMessageChannel(json.getString("channel_id"));
        User user = (User) identity.getUser(json.getString("user_id"));
        int timeStamp = json.getInt("timestamp");

        dispatchEvent(new TypingStartEvent(identity, sequence, channel, user, timeStamp));
    }

}
