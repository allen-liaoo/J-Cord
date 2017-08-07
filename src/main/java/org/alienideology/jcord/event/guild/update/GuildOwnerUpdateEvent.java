package org.alienideology.jcord.event.guild.update;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.guild.GuildUpdateEvent;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IMember;

/**
 * @author AlienIdeology
 */
public class GuildOwnerUpdateEvent extends GuildUpdateEvent {

    private final IMember oldOwner;

    public GuildOwnerUpdateEvent(Identity identity, int sequence, IGuild guild, IMember oldOwner) {
        super(identity, sequence, guild);
        this.oldOwner = oldOwner;
    }

    public IMember getNewOwner() {
        return guild.getOwner();
    }

    public IMember getOldOwner() {
        return oldOwner;
    }

}
