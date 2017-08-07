package org.alienideology.jcord.event.guild.update;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.guild.GuildUpdateEvent;
import org.alienideology.jcord.handle.guild.IGuild;

/**
 * @author AlienIdeology
 */
public class GuildNameUpdateEvent extends GuildUpdateEvent {

    private final String oldName;

    public GuildNameUpdateEvent(Identity identity, int sequence, IGuild guild, String oldName) {
        super(identity, sequence, guild);
        this.oldName = oldName;
    }

    public String getNewName() {
        return guild.getName();
    }

    public String getOldName() {
        return oldName;
    }

}
