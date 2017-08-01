package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.event.guild.update.*;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.json.JSONObject;

import java.util.Objects;

/**
 * @author AlienIdeology
 */
public class GuildUpdateEventHandler extends EventHandler {

    public GuildUpdateEventHandler(IdentityImpl identity) {
        super(identity);
    }

    @Override
    public void dispatchEvent(JSONObject json, int sequence) {
        String id = json.getString("id");
        Guild oldGuild = (Guild) identity.getGuild(id);
        Guild newGuild = builder.buildGuildById(id); // This guild will not be added to identity, since IdentityImpl#addGuild rejects repeated guilds

        identity.updateGuild(newGuild);

        if (!Objects.equals(oldGuild.getName(), newGuild.getName())) {
            dispatchEvent(new GuildNameUpdateEvent(identity, sequence, newGuild, oldGuild));
        }
        if (!Objects.equals(oldGuild.getOwner(), newGuild.getOwner())) {
            dispatchEvent(new GuildOwnerUpdateEvent(identity, sequence, newGuild, oldGuild));
        }
        if (!Objects.equals(oldGuild.getRegion(), newGuild.getRegion())) {
            dispatchEvent(new GuildRegionUpdateEvent(identity, sequence, newGuild, oldGuild));
        }
        if (!Objects.equals(oldGuild.getIconUrl(), newGuild.getIconUrl())) {
            dispatchEvent(new GuildIconUpdateEvent(identity, sequence, newGuild, oldGuild));
        }
        if (!Objects.equals(oldGuild.getSplash(), newGuild.getSplash())) {
            dispatchEvent(new GuildSplashUpdateEvent(identity, sequence, newGuild, oldGuild));
        }
        if (!Objects.equals(oldGuild.getAfkTimeout(), newGuild.getAfkTimeout())) {
            dispatchEvent(new GuildAFKTimeoutUpdateEvent(identity, sequence, newGuild, oldGuild));
        }
        if (!Objects.equals(oldGuild.getVerificationLevel(), newGuild.getVerificationLevel())) {
            dispatchEvent(new GuildVerificationUpdateEvent(identity, sequence, newGuild, oldGuild));
        }
        if (!Objects.equals(oldGuild.getNotificationsLevel(), newGuild.getNotificationsLevel())) {
            dispatchEvent(new GuildNotificationUpdateEvent(identity, sequence, newGuild, oldGuild));
        }
        if (!Objects.equals(oldGuild.getMFALevel(), newGuild.getMFALevel())) {
            dispatchEvent(new GuildMFAUpdateEvent(identity, sequence, newGuild, oldGuild));
        }
    }
}
