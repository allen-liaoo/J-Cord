package org.alienideology.jcord.event.channel.guild;

import org.jetbrains.annotations.NotNull;
import org.alienideology.jcord.event.channel.ChannelCreateEvent;
import org.alienideology.jcord.handle.channel.IGuildChannel;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.Channel;
import org.alienideology.jcord.internal.object.guild.Guild;

/**
 * @author AlienIdeology
 */
public class GuildChannelCreateEvent extends ChannelCreateEvent implements IGuildChannelEvent {

    private Guild guild;

    public GuildChannelCreateEvent(IdentityImpl identity, int sequence, Channel channel, Guild guild) {
        super(identity, sequence, channel);
        this.guild = guild;
    }

    @Override
    public IGuild getGuild() {
        return guild;
    }

    @Override
    public boolean isPrivateChannel() {
        return false;
    }

    @Override
    @NotNull
    public IGuildChannel getGuildChannel() {
        return (IGuildChannel) channel;
    }

}
