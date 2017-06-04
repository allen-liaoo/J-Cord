package org.alienideology.jcord.object.channel;

import org.alienideology.jcord.Identity;

/**
 * TextChannel - A GuildChannel for text messages.
 * @author AlienIdeology
 */
public class TextChannel extends GuildChannel {

    private String topic;
//    private Message message;

    public TextChannel(Identity identity, String guild_id, String id, String name, int position, String topic, String last_message_id) {
        super(identity, guild_id, id, Type.TEXT, name, position);
        this.topic = topic;
//        this.message = new ObjectBuilder().buildMessage(HttpPath.Channel.GET_CHANNEL_MESSAGE.request(identity, id, last_message_id).asjson());
    }

    public String getTopic() {
        return topic;
    }
}
