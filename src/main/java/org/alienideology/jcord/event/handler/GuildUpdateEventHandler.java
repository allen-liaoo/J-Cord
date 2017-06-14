package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.event.guild.update.*;
import org.alienideology.jcord.internal.object.Guild;
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

        if (!Objects.equals(oldGuild.getOwner(), newGuild.getOwner())) {
            fireEvent(new GuildOwnerUpdateEvent(identity, newGuild, sequence, oldGuild));
        }
        if (!Objects.equals(oldGuild.getRegion(), newGuild.getRegion())) {
            fireEvent(new GuildRegionUpdateEvent(identity, newGuild, sequence, oldGuild));
        }
        if (!Objects.equals(oldGuild.getIcon(), newGuild.getIcon())) {
            fireEvent(new GuildIconUpdateEvent(identity, newGuild, sequence, oldGuild));
        }
        if (!Objects.equals(oldGuild.getSplash(), newGuild.getSplash())) {
            fireEvent(new GuildSplashUpdateEvent(identity, newGuild, sequence, oldGuild));
        }
        if (!Objects.equals(oldGuild.getAfkTimeout(), newGuild.getAfkTimeout())) {
            fireEvent(new GuildAFKTimeoutUpdateEvent(identity, newGuild, sequence, oldGuild));
        }
        if (!Objects.equals(oldGuild.getVerificationLevel(), newGuild.getVerificationLevel())) {
            fireEvent(new GuildVerificationUpdateEvent(identity, newGuild, sequence, oldGuild));
        }
        if (!Objects.equals(oldGuild.getNotificationsLevel(), newGuild.getNotificationsLevel())) {
            fireEvent(new GuildNotificationUpdateEvent(identity, newGuild, sequence, oldGuild));
        }
        if (!Objects.equals(oldGuild.getMFALevel(), newGuild.getMFALevel())) {
            fireEvent(new GuildMFAUpdateEvent(identity, newGuild, sequence, oldGuild));
        }
    }
}
