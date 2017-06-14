package org.alienideology.jcord.internal.event.guild.update;

import org.alienideology.jcord.internal.Identity;
import org.alienideology.jcord.internal.event.guild.GuildUpdateEvent;
import org.alienideology.jcord.internal.object.Guild;
import org.alienideology.jcord.internal.object.guild.Member;

/**
 * @author AlienIdeology
 */
public class GuildOwnerUpdateEvent extends GuildUpdateEvent {

    public GuildOwnerUpdateEvent(Identity identity, Guild guild, int sequence, Guild oldGuild) {
        super(identity, guild, sequence, oldGuild);
    }

    public Member getNewOwner() {
        return guild.getOwner();
    }

    public Member getOldOwner() {
        return oldGuild.getOwner();
    }

}
