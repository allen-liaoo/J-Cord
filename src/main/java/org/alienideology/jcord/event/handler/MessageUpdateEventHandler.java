package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.event.message.dm.PrivateMessageUpdateEvent;
import org.alienideology.jcord.event.message.guild.GuildMessageUpdateEvent;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.MessageChannel;
import org.alienideology.jcord.internal.object.message.Message;
import org.json.JSONObject;

/**
 * @author AlienIdeology
 */
public class MessageUpdateEventHandler extends EventHandler {

    public MessageUpdateEventHandler(IdentityImpl identity) {
        super(identity);
    }

    @Override
    public void dispatchEvent(JSONObject json, int sequence) {
        Message edited = builder.buildMessageById(json.getString("channel_id"), json.getString("key"));
        MessageChannel channel = (MessageChannel) edited.getChannel();

        if (!edited.isFromSelf()) { // Not from self
            if (channel.isPrivate()) {
                dispatchEvent(new PrivateMessageUpdateEvent(identity, sequence, channel, edited));
            } else {
                dispatchEvent(new GuildMessageUpdateEvent(identity, sequence, channel, edited));
            }
        }
    }

}
