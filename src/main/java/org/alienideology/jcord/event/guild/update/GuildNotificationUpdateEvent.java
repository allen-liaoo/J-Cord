package org.alienideology.jcord.event.guild.update;

import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.event.guild.GuildUpdateEvent;
import org.alienideology.jcord.internal.object.guild.Guild;

/**
 * @author AlienIdeology
 */
public class GuildNotificationUpdateEvent extends GuildUpdateEvent {

    public GuildNotificationUpdateEvent(IdentityImpl identity, int sequence, Guild newGuild, Guild oldGuild) {
        super(identity, sequence, newGuild, oldGuild);
    }

    public Guild.Notification getNewNotification() {
        return guild.getNotificationsLevel();
    }

    public Guild.Notification getOldNotification() {
        return oldGuild.getNotificationsLevel();
    }

}
