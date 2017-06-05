package org.alienideology.jcord.object.channel;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.object.Mention;
import org.alienideology.jcord.object.message.Message;

import java.util.Objects;

/**
 * TextChannel - A GuildChannel for text messages.
 * @author AlienIdeology
 */
public class TextChannel extends GuildChannel implements Mention {

    private String topic;
    private Message lastMessage;

    public TextChannel(Identity identity, String guild_id, String id, String name, int position, String topic) {
        super(identity, guild_id, id, Type.TEXT, name, position);
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    public Message getLastMessage() {
        return lastMessage;
    }

    /**
     * [API Use Only]
     * @param lastMessage The last message of this channel
     * @return TextChannel for chaining
     */
    public TextChannel setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
        return this;
    }

    @Override
    public String mention() {
        return "<#"+id+">";
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof TextChannel) && Objects.equals(this.id, ((TextChannel) obj).getId());
    }

}
