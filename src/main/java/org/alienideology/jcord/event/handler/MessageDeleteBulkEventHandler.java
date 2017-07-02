package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.event.message.MessageBulkDeleteEvent;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.MessageChannel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author AlienIdeology
 */
public class MessageDeleteBulkEventHandler extends EventHandler {

    public MessageDeleteBulkEventHandler(IdentityImpl identity) {
        super(identity);
    }

    @Override
    public void dispatchEvent(JSONObject json, int sequence) {
        MessageChannel channel = (MessageChannel) identity.getMessageChannel(json.getString("channel_id"));
        List<String> ids = new ArrayList<>();
        JSONArray array = json.getJSONArray("ids");
        for (int i = 0; i < array.length(); i++) {
            ids.add(array.getString(i));
        }

        dispatchEvent(new MessageBulkDeleteEvent(identity, sequence, channel, ids));
    }

}
