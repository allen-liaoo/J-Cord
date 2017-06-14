package org.alienideology.jcord.internal.object.channel;

import com.sun.istack.internal.NotNull;
import org.alienideology.jcord.handle.channel.IChannel;
import org.alienideology.jcord.handle.channel.ITextChannel;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.Guild;
import org.alienideology.jcord.internal.object.Message;

import java.util.Objects;

/**
 * @author AlienIdeology
 */
public final class TextChannel extends MessageChannel implements ITextChannel {

    private Guild guild;
    private String name;
    private int position;
    private String topic;

    public TextChannel(IdentityImpl identity, String guild_id, String id, String name, int position, String topic, Message lastMessagt) {
        super(identity, id, IChannel.Type.TEXT, lastMessagt);
        this.guild = (Guild) identity.getGuild(guild_id);
        this.name = name;
        this.position = position;
        this.topic = topic;
    }

    @Override
    public IGuild getGuild() {
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

    @Override
    public String getTopic() {
        return topic;
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
