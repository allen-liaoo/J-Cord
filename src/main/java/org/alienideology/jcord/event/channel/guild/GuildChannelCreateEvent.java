package org.alienideology.jcord.event.channel.guild;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.channel.ChannelCreateEvent;
import org.alienideology.jcord.handle.channel.IChannel;
import org.alienideology.jcord.handle.channel.IGuildChannel;
import org.alienideology.jcord.handle.guild.IGuild;
import org.jetbrains.annotations.NotNull;

/**
 * @author AlienIdeology
 */
public class GuildChannelCreateEvent extends ChannelCreateEvent implements IGuildChannelEvent {

    private final IGuild guild;

    public GuildChannelCreateEvent(Identity identity, int sequence, IChannel channel, IGuild guild) {
        super(identity, sequence, channel);
        this.guild = guild;
    }

    @Override
    public IGuild getGuild() {
        return guild;
    }

    @Override
    @NotNull
    public IGuildChannel getGuildChannel() {
        return (IGuildChannel) channel;
    }

}
