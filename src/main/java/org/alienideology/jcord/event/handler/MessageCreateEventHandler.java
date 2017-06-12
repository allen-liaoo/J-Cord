package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.message.guild.IGuildMessageCreateEvent;
import org.alienideology.jcord.event.message.dm.PrivateMessageCreateEvent;
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
                .setLatestMessage(message);

        if (!message.isFromSelf()) {
            if (message.fromType(Channel.Type.TEXT)) {
                fireEvent(new IGuildMessageCreateEvent(identity, sequence, channel, message));
            } else {
                fireEvent(new PrivateMessageCreateEvent(identity, sequence, channel, message));
            }
        }

    }

}
