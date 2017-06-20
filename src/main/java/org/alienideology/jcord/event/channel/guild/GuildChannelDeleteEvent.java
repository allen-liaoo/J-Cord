package org.alienideology.jcord.event.channel.guild;

import com.sun.istack.internal.NotNull;
import org.alienideology.jcord.event.channel.ChannelDeleteEvent;
import org.alienideology.jcord.handle.channel.IChannel;
import org.alienideology.jcord.handle.channel.IGuildChannel;
import org.alienideology.jcord.handle.channel.ITextChannel;
import org.alienideology.jcord.handle.channel.IVoiceChannel;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.Channel;
import org.alienideology.jcord.internal.object.guild.Guild;

import java.time.OffsetDateTime;

/**
 * @author AlienIdeology
 */
public class GuildChannelDeleteEvent extends ChannelDeleteEvent implements IGuildChannelEvent {

    private Guild guild;

    public GuildChannelDeleteEvent(IdentityImpl identity, int sequence, Channel channel, OffsetDateTime timeStamp, Guild guild) {
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

    @Override
    public ITextChannel getTextChannel() {
        return channel.isType(IChannel.Type.TEXT) ? (ITextChannel) channel : null;
    }

    @Override
    public IVoiceChannel getVoiceChannel() {
        return channel.isType(IChannel.Type.VOICE) ? (IVoiceChannel) channel : null;
    }

}
