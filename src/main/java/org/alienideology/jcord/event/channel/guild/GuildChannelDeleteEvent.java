package org.alienideology.jcord.event.channel.guild;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.channel.ChannelDeleteEvent;
import org.alienideology.jcord.handle.channel.IChannel;
import org.alienideology.jcord.handle.channel.IGuildChannel;
import org.alienideology.jcord.handle.guild.IGuild;
import org.jetbrains.annotations.NotNull;

import java.time.OffsetDateTime;

/**
 * @author AlienIdeology
 */
public class GuildChannelDeleteEvent extends ChannelDeleteEvent implements IGuildChannelEvent {

    private final IGuild guild;

    public GuildChannelDeleteEvent(Identity identity, int sequence, IChannel channel, OffsetDateTime timeStamp, IGuild guild) {
        super(identity, sequence, channel, timeStamp);
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
