package org.alienideology.jcord.event.guild.update;

import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.event.guild.GuildUpdateEvent;
import org.alienideology.jcord.internal.object.guild.Guild;

/**
 * @author AlienIdeology
 */
public class GuildIconUpdateEvent extends GuildUpdateEvent {

    public GuildIconUpdateEvent(IdentityImpl identity, Guild newGuild, int sequence, Guild oldGuild) {
        super(identity, newGuild, sequence, oldGuild);
    }

    public String getNewIcon() {
        return guild.getIcon();
    }

    public String getOldIcon() {
        return oldGuild.getIcon();
    }

}
