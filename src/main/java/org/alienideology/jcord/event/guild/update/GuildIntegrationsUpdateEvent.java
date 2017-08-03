package org.alienideology.jcord.event.guild.update;

import org.alienideology.jcord.event.guild.GuildUpdateEvent;
import org.alienideology.jcord.handle.guild.IIntegration;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;

import java.util.List;

/**
 * @author AlienIdeology
 */
public class GuildIntegrationsUpdateEvent extends GuildUpdateEvent {

    public GuildIntegrationsUpdateEvent(IdentityImpl identity, int sequence, Guild newGuild) {
        super(identity, sequence, newGuild, null);
    }

    public List<IIntegration> getIntegrations() {
        return getGuild().getIntegrations();
    }

    /**
     * Since guild integrations are not cached, there will not be an old guild instance.
     *
     * @return throws exception.
     * @throws UnsupportedOperationException
     */
    @Override
    public Guild getOldGuild() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Since guild integrations are not cached, there will not be an old guild instance!");
    }

}
