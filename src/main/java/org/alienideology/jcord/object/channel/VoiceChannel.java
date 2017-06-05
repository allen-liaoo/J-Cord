package org.alienideology.jcord.object.channel;

import org.alienideology.jcord.Identity;

import java.util.Objects;

/**
 * VoiceChannel - A GuildChannel for audio connections.
 * @author AlienIdeology
 */
public class VoiceChannel extends GuildChannel {

    private int bitrate;
    private int user_limit;

    public VoiceChannel(Identity identity, String guild_id, String id, String name, int position, int bitrate, int user_limit) {
        super(identity, guild_id, id, Type.VOICE, name, position);
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
    public boolean equals(Object obj) {
        return (obj instanceof VoiceChannel) && Objects.equals(this.id, ((VoiceChannel) obj).getId());
    }
}
