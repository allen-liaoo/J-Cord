package org.alienideology.jcord.event.guild.update;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.guild.GuildUpdateEvent;
import org.alienideology.jcord.object.Guild;

/**
 * @author AlienIdeology
 */
public class GuildNotificationUpdateEvent extends GuildUpdateEvent {

    public GuildNotificationUpdateEvent(Identity identity, Guild newGuild, int sequence, Guild oldGuild) {
        super(identity, newGuild, sequence, oldGuild);
    }

    public Guild.Notification getNewNotification() {
        return guild.getNotificationsLevel();
    }

    public Guild.Notification getOldNotification() {
        return oldGuild.getNotificationsLevel();
    }

}
