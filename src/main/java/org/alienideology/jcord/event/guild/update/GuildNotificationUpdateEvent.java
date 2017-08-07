package org.alienideology.jcord.event.guild.update;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.guild.GuildUpdateEvent;
import org.alienideology.jcord.handle.guild.IGuild;

/**
 * @author AlienIdeology
 */
public class GuildNotificationUpdateEvent extends GuildUpdateEvent {

    private final IGuild.Notification oldNotification;

    public GuildNotificationUpdateEvent(Identity identity, int sequence, IGuild guild, IGuild.Notification oldNotification) {
        super(identity, sequence, guild);
        this.oldNotification = oldNotification;
    }

    public IGuild.Notification getNewNotification() {
        return guild.getNotificationsLevel();
    }

    public IGuild.Notification getOldNotification() {
        return oldNotification;
    }

}
