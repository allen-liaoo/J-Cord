package org.alienideology.jcord.event.channel.guild.text;

import org.alienideology.jcord.event.channel.guild.GuildChannelCreateEvent;
import org.alienideology.jcord.handle.channel.ITextChannel;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.Channel;
import org.alienideology.jcord.internal.object.guild.Guild;

/**
 * @author AlienIdeology
 */
public class TextChannelCreateEvent extends GuildChannelCreateEvent implements ITextChannelEvent {

    public TextChannelCreateEvent(IdentityImpl identity, int sequence, Channel channel, Guild guild) {
        super(identity, sequence, channel, guild);
    }

    @Override
    public ITextChannel getTextChannel() {
        return (ITextChannel) getGuildChannel();
    }

}
