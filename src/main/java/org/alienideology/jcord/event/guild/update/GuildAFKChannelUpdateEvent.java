package org.alienideology.jcord.event.guild.update;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.guild.GuildUpdateEvent;
import org.alienideology.jcord.handle.channel.IVoiceChannel;
import org.alienideology.jcord.handle.guild.IGuild;

/**
 * @author AlienIdeology
 */
public class GuildAFKChannelUpdateEvent extends GuildUpdateEvent {

    private final IVoiceChannel oldAFKChannel;

    public GuildAFKChannelUpdateEvent(Identity identity, int sequence, IGuild guild, IVoiceChannel oldAFKChannel) {
        super(identity, sequence, guild);
        this.oldAFKChannel = oldAFKChannel;
    }

    public IVoiceChannel getOldAFKChannel() {
        return oldAFKChannel;
    }

}
