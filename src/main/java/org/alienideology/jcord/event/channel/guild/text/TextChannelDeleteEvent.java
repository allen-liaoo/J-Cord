package org.alienideology.jcord.event.channel.guild.text;

import org.alienideology.jcord.event.channel.guild.GuildChannelDeleteEvent;
import org.alienideology.jcord.handle.channel.ITextChannel;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.Channel;
import org.alienideology.jcord.internal.object.guild.Guild;

import java.time.OffsetDateTime;

/**
 * @author AlienIdeology
 */
public class TextChannelDeleteEvent extends GuildChannelDeleteEvent implements ITextChannelEvent{

    public TextChannelDeleteEvent(IdentityImpl identity, int sequence, Channel channel, OffsetDateTime timeStamp, Guild guild) {
        super(identity, sequence, channel, timeStamp, guild);
    }

    @Override
    public ITextChannel getTextChannel() {
        return (ITextChannel) getGuildChannel();
    }

}
