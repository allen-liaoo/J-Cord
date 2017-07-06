package org.alienideology.jcord.event.guild.update;

import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.event.guild.GuildUpdateEvent;
import org.alienideology.jcord.internal.object.guild.Guild;

/**
 * @author AlienIdeology
 */
public class GuildIconUpdateEvent extends GuildUpdateEvent {

    public GuildIconUpdateEvent(IdentityImpl identity, int sequence, Guild newGuild, Guild oldGuild) {
        super(identity, sequence, newGuild, oldGuild);
    }

    public String getNewIcon() {
        return guild.getIcon();
    }

    public String getOldIcon() {
        return oldGuild.getIcon();
    }

}
