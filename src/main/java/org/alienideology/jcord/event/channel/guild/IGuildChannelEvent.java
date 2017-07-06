package org.alienideology.jcord.event.channel.guild;

import org.alienideology.jcord.handle.channel.IGuildChannel;
import org.alienideology.jcord.handle.guild.IGuild;

/**
 * @author AlienIdeology
 */
public interface IGuildChannelEvent {

    IGuild getGuild();

    IGuildChannel getGuildChannel();

}
