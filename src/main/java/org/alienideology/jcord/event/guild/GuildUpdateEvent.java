package org.alienideology.jcord.event.guild;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.guild.IGuild;

/**
 * @author AlienIdeology
 */
public class GuildUpdateEvent extends GuildEvent {

    public GuildUpdateEvent(Identity identity, int sequence, IGuild guild) {
        super(identity, sequence, guild);
    }

    /**
     * @return The new, updated guild.
     */
    @Override
    public IGuild getGuild() {
        return super.getGuild();
    }

}
