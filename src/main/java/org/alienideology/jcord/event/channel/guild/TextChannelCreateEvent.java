package org.alienideology.jcord.event.channel.guild;

import org.alienideology.jcord.handle.channel.ITextChannel;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.Channel;
import org.alienideology.jcord.internal.object.guild.Guild;

/**
 * @author AlienIdeology
 */
public class TextChannelCreateEvent extends GuildChannelCreateEvent {

    public TextChannelCreateEvent(IdentityImpl identity, int sequence, Channel channel, Guild guild) {
        super(identity, sequence, channel, guild);
    }

    public ITextChannel getTextChannel() {
        return (ITextChannel) getGuildChannel();
    }

}
