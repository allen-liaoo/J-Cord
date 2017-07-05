package org.alienideology.jcord.event.guild;

import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;

/**
 * @author AlienIdeology
 */
public class GuildUnavailableEvent extends GuildEvent {

    public GuildUnavailableEvent(IdentityImpl identity, Guild guild, int sequence) {
        super(identity, sequence, guild);
    }

}
