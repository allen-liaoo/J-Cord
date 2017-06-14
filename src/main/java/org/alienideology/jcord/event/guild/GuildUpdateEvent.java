package org.alienideology.jcord.event.guild;

import org.alienideology.jcord.internal.object.Identity;
import org.alienideology.jcord.internal.object.Guild;

/**
 * @author AlienIdeology
 */
public class GuildUpdateEvent extends GuildEvent {

    protected final Guild oldGuild;

    public GuildUpdateEvent(Identity identity, Guild newGuild, int sequence, Guild oldGuild) {
        super(identity, newGuild, sequence);
        this.oldGuild = oldGuild;
    }

    /**
     * @return The new, updated guild.
     */
    @Override
    public Guild getGuild() {
        return super.getGuild();
    }

    public Guild getOldGuild() {
        return oldGuild;
    }
}
