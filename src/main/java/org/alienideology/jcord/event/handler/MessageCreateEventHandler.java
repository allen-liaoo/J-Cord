package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.event.message.guild.GuildMessageCreateEvent;
import org.alienideology.jcord.event.message.dm.PrivateMessageCreateEvent;
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
            channel.setLatestMessage(message);
            if (!message.isFromSelf()) {
                if (channel.isPrivate()) {
                    fireEvent(new PrivateMessageCreateEvent(identity, sequence, channel, message));
                } else {
                    fireEvent(new GuildMessageCreateEvent(identity, sequence, channel, message));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
