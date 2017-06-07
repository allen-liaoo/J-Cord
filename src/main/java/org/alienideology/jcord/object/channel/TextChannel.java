package org.alienideology.jcord.object.channel;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.object.Mention;
import org.alienideology.jcord.object.guild.Guild;
import org.alienideology.jcord.object.message.Message;

import java.util.Objects;

/**
 * TextChannel - A GuildChannel for text messages.
 * @author AlienIdeology
 */
public class TextChannel extends MessageChannel implements GuildChannel, Mention {

    private Guild guild;
    private String name;
    private int position;
    private String topic;

    public TextChannel(Identity identity, String guild_id, String id, String name, int position, String topic, Message lastMessagt) {
        super(identity, id, Channel.Type.TEXT, lastMessagt);
        this.guild = identity.getGuild(guild_id);
        this.name = name;
        this.position = position;
        this.topic = topic;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getPosition() {
        return position;
    }

    public String getTopic() {
        return topic;
    }

    public boolean isDefaultChannel() {
        return id.equals(guild.getId());
    }

    @Override
    public String mention() {
        return "<#"+id+">";
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof TextChannel) && Objects.equals(this.id, ((TextChannel) obj).getId());
    }

    @Override
    public String toString() {
        return "ID: "+id+"\tName: "+name;
    }

}
