package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.internal.object.Identity;
import org.alienideology.jcord.event.message.dm.PrivateMessageUpdateEvent;
import org.alienideology.jcord.event.message.guild.GuildMessageUpdateEvent;
import org.alienideology.jcord.internal.object.Message;
import org.alienideology.jcord.internal.object.channel.MessageChannel;
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
        MessageChannel channel = (MessageChannel) edited.getChannel();

        if (!edited.isFromSelf()) { // Not from self
            if (channel.isPrivate()) {
                fireEvent(new PrivateMessageUpdateEvent(identity, sequence, channel, edited));
            } else {
                fireEvent(new GuildMessageUpdateEvent(identity, sequence, channel, edited));
            }
        }
    }

}
