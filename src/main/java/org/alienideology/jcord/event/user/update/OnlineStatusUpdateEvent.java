package org.alienideology.jcord.event.user.update;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.user.PresenceUpdateEvent;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.handle.user.OnlineStatus;

/**
 * @author AlienIdeology
 */
public class OnlineStatusUpdateEvent extends PresenceUpdateEvent {

    private final OnlineStatus oldStatus;

    public OnlineStatusUpdateEvent(Identity identity, int sequence, IUser user, OnlineStatus oldStatus) {
        super(identity, sequence, user);
        this.oldStatus = oldStatus;
    }

    public OnlineStatus getNewStatus() {
        return getPresence().getStatus();
    }

    public OnlineStatus getOldStatus() {
        return oldStatus;
    }

}
