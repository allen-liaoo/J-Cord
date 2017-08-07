package org.alienideology.jcord.event.guild.update;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.guild.GuildUpdateEvent;
import org.alienideology.jcord.handle.guild.IGuild;

/**
 * @author AlienIdeology
 */
public class GuildIconUpdateEvent extends GuildUpdateEvent {

    private final String oldIcon;

    public GuildIconUpdateEvent(Identity identity, int sequence, IGuild guild, String oldIcon) {
        super(identity, sequence, guild);
        this.oldIcon = oldIcon;
    }

    public String getNewIcon() {
        return guild.getIconUrl();
    }

    public String getOldIcon() {
        return oldIcon;
    }

}
