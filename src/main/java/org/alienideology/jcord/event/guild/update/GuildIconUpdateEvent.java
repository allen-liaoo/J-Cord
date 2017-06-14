package org.alienideology.jcord.event.guild.update;

import org.alienideology.jcord.internal.object.Identity;
import org.alienideology.jcord.event.guild.GuildUpdateEvent;
import org.alienideology.jcord.internal.object.Guild;

/**
 * @author AlienIdeology
 */
public class GuildIconUpdateEvent extends GuildUpdateEvent {

    public GuildIconUpdateEvent(Identity identity, Guild newGuild, int sequence, Guild oldGuild) {
        super(identity, newGuild, sequence, oldGuild);
    }

    public String getNewIcon() {
        return guild.getIcon();
    }

    public String getOldIcon() {
        return oldGuild.getIcon();
    }

}
