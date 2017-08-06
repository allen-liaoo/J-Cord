package org.alienideology.jcord.event.channel.guild.text;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.channel.guild.GuildChannelDeleteEvent;
import org.alienideology.jcord.handle.channel.IChannel;
import org.alienideology.jcord.handle.channel.ITextChannel;
import org.alienideology.jcord.internal.object.guild.Guild;

import java.time.OffsetDateTime;

/**
 * @author AlienIdeology
 */
public class TextChannelDeleteEvent extends GuildChannelDeleteEvent implements ITextChannelEvent{

    public TextChannelDeleteEvent(Identity identity, int sequence, IChannel channel, OffsetDateTime timeStamp, Guild guild) {
        super(identity, sequence, channel, timeStamp, guild);
    }

    @Override
    public ITextChannel getTextChannel() {
        return (ITextChannel) getGuildChannel();
    }

}
