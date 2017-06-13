package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.message.guild.GuildMessageCreateEvent;
import org.alienideology.jcord.event.message.dm.PrivateMessageCreateEvent;
import org.alienideology.jcord.object.channel.MessageChannel;
import org.alienideology.jcord.object.Message;
import org.json.JSONObject;

/**
 * @author AlienIdeology
 */
public class MessageCreateEventHandler extends EventHandler {

    public MessageCreateEventHandler(Identity identity) {
        super(identity);
    }

    @Override
    public void dispatchEvent(JSONObject json, int sequence) {
        Message message = builder.buildMessage(json);
        try {
            MessageChannel channel = message.getChannel();
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
