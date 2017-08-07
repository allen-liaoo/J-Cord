package org.alienideology.jcord.event.guild.update;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.guild.GuildUpdateEvent;
import org.alienideology.jcord.handle.guild.IGuild;

/**
 * @author AlienIdeology
 */
public class GuildEmbedEnabledUpdateEvent extends GuildUpdateEvent {

    public GuildEmbedEnabledUpdateEvent(Identity identity, int sequence, IGuild guild) {
        super(identity, sequence, guild);
    }

    public boolean isEmbedEnabled() {
        return guild.isEmbedEnabled();
    }

}
