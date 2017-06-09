package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.message.GuildMessageCreateEvent;
import org.alienideology.jcord.event.message.PrivateMessageCreateEvent;
import org.alienideology.jcord.object.channel.Channel;
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
        MessageChannel channel = message.getChannel()
                .setLastMessage(message);

        if (message.fromType(Channel.Type.TEXT)) {
            fireEvent(new GuildMessageCreateEvent(identity, sequence, channel, message));
        } else {
            fireEvent(new PrivateMessageCreateEvent(identity, sequence, channel, message));
        }

    }

}
