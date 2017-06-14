package org.alienideology.jcord.event.guild.update;

import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.event.guild.GuildUpdateEvent;
import org.alienideology.jcord.internal.object.Guild;
import org.alienideology.jcord.internal.object.Region;

/**
 * @author AlienIdeology
 */
public class GuildRegionUpdateEvent extends GuildUpdateEvent {

    public GuildRegionUpdateEvent(IdentityImpl identity, Guild guild, int sequence, Guild oldGuild) {
        super(identity, guild, sequence, oldGuild);
    }

    public Region getNewRegion() {
        return guild.getRegion();
    }

    public Region getOldRegion() {
        return oldGuild.getRegion();
    }

}
