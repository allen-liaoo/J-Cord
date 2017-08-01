package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.event.message.dm.PrivateMessageCreateEvent;
import org.alienideology.jcord.event.message.guild.GuildMessageCreateEvent;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.MessageChannel;
import org.alienideology.jcord.internal.object.message.Message;
import org.json.JSONObject;

/**
 * @author AlienIdeology
 */
public class MessageCreateEventHandler extends EventHandler {

    public MessageCreateEventHandler(IdentityImpl identity) {
        super(identity);
    }

    @Override
    public void dispatchEvent(JSONObject json, int sequence) {
        Message message = builder.buildMessage(json);
        try {
            MessageChannel channel = (MessageChannel) message.getChannel();
            if (channel.isPrivate()) {
                dispatchEvent(new PrivateMessageCreateEvent(identity, sequence, channel, message));
            } else {
                dispatchEvent(new GuildMessageCreateEvent(identity, sequence, channel, message));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
