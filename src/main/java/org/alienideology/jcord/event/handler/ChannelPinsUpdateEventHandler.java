package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.event.message.MessagePinUpdateEvent;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.MessageChannel;
import org.json.JSONObject;

/**
 * @author AlienIdeology
 */
public class ChannelPinsUpdateEventHandler extends EventHandler {

    public ChannelPinsUpdateEventHandler(IdentityImpl identity) {
        super(identity);
    }

    @Override
    public void dispatchEvent(JSONObject json, int sequence) {
        System.out.println(json.toString(4));

        MessageChannel channel = (MessageChannel) identity.getMessageChannel(json.getString("channel_id"));
        String timeStamp = json.isNull("last_pin_timestamp") ? null : json.getString("last_pin_timestamp");
        dispatchEvent(new MessagePinUpdateEvent(identity, sequence, channel, null, timeStamp));
    }

}
