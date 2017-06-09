package org.alienideology.jcord.object.channel;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.object.Guild;

import java.util.Objects;

/**
 * VoiceChannel - A GuildChannel for audio connections.
 * @author AlienIdeology
 */
public class VoiceChannel extends Channel implements GuildChannel {

    private Guild guild;

    private String name;
    private int position;
    private int bitrate;
    private int user_limit;

    public VoiceChannel(Identity identity, String guild_id, String id, String name, int position, int bitrate, int user_limit) {
        super(identity, id, Type.VOICE);
        this.guild = identity.getGuild(guild_id);
        this.name = name;
        this.position = position;
        this.bitrate = bitrate;
        this.user_limit = user_limit;
    }

    public int getBitrate() {
        return bitrate;
    }

    public int getUser_limit() {
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
