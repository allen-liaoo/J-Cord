package org.alienideology.jcord.event.guild.update;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.guild.GuildUpdateEvent;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IIntegration;

import java.util.List;

/**
 * @author AlienIdeology
 */
public class GuildIntegrationsUpdateEvent extends GuildUpdateEvent {

    public GuildIntegrationsUpdateEvent(Identity identity, int sequence, IGuild guild) {
        super(identity, sequence, guild);
    }

    public List<IIntegration> getIntegrations() {
        return getGuild().getIntegrations();
    }

}
