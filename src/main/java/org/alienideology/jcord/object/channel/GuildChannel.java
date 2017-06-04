package org.alienideology.jcord.object.channel;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.object.guild.Guild;

/**
 * GuildChannel - A Channel that exist in a guild
 * @author AlienIdeology
 */
public class GuildChannel extends Channel {

    protected final Type channelType;

    protected Guild guild;
    protected String name;
    protected int position;

    public GuildChannel (Identity identity, String guild_id, String id, Type channel_type, String name, int position) {
        super(identity, id, false);
        this.channelType = channel_type;
//        this.guild = identity.getGuild(guild_id);
        this.name = name;
        this.position = position;
    }

    public Type getChannelType() {
        return channelType;
    }

    public Guild getGuild() {
        return guild;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

}
