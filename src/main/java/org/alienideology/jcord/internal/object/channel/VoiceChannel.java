package org.alienideology.jcord.internal.object.channel;

import org.alienideology.jcord.handle.channel.IVoiceChannel;
import org.alienideology.jcord.internal.Identity;
import org.alienideology.jcord.internal.object.Guild;

import java.util.Objects;

/**
 * @author AlienIdeology
 */
public class VoiceChannel extends Channel implements IVoiceChannel {

    private Guild guild;

    private String name;
    private int position;
    private int bitrate;
    private int user_limit;

    public VoiceChannel(Identity identity, String guild_id, String id, String name, int position, int bitrate, int user_limit) {
        super(identity, id, Channel.Type.VOICE);
        this.guild = identity.getGuild(guild_id);
        this.name = name;
        this.position = position;
        this.bitrate = bitrate;
        this.user_limit = user_limit;
    }

    @Override
    public int getBitrate() {
        return bitrate;
    }

    @Override
    public int getUserLimit() {
        return user_limit;
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

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof VoiceChannel) && Objects.equals(this.id, ((VoiceChannel) obj).getId());
    }

    @Override
    public String toString() {
        return "ID: "+id+"\tName: "+name;
    }

}
