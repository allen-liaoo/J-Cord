package org.alienideology.jcord.event.guild.update;

import org.alienideology.jcord.event.guild.GuildUpdateEvent;
import org.alienideology.jcord.handle.Region;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;

/**
 * @author AlienIdeology
 */
public class GuildRegionUpdateEvent extends GuildUpdateEvent {

    public GuildRegionUpdateEvent(IdentityImpl identity, int sequence, Guild guild, Guild oldGuild) {
        super(identity, sequence, guild, oldGuild);
    }

    public Region getNewRegion() {
        return guild.getRegion();
    }

    public Region getOldRegion() {
        return oldGuild.getRegion();
    }

}
