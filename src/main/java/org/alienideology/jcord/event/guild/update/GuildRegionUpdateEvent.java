package org.alienideology.jcord.event.guild.update;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.guild.GuildUpdateEvent;
import org.alienideology.jcord.handle.Region;
import org.alienideology.jcord.handle.guild.IGuild;

/**
 * @author AlienIdeology
 */
public class GuildRegionUpdateEvent extends GuildUpdateEvent {

    private final Region oldRegion;

    public GuildRegionUpdateEvent(Identity identity, int sequence, IGuild guild, Region oldRegion) {
        super(identity, sequence, guild);
        this.oldRegion = oldRegion;
    }

    public Region getNewRegion() {
        return guild.getRegion();
    }

    public Region getOldRegion() {
        return oldRegion;
    }

}
