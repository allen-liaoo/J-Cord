package org.alienideology.jcord.event.channel.guild.text;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.channel.guild.GuildChannelCreateEvent;
import org.alienideology.jcord.handle.channel.IChannel;
import org.alienideology.jcord.handle.channel.ITextChannel;
import org.alienideology.jcord.handle.guild.IGuild;

/**
 * @author AlienIdeology
 */
public class TextChannelCreateEvent extends GuildChannelCreateEvent implements ITextChannelEvent {

    public TextChannelCreateEvent(Identity identity, int sequence, IChannel channel, IGuild guild) {
        super(identity, sequence, channel, guild);
    }

    @Override
    public ITextChannel getTextChannel() {
        return (ITextChannel) getGuildChannel();
    }

}
