package org.alienideology.jcord.event.user.update;

import org.alienideology.jcord.event.user.PresenceUpdateEvent;
import org.alienideology.jcord.handle.user.OnlineStatus;
import org.alienideology.jcord.handle.user.Presence;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.user.User;

/**
 * @author AlienIdeology
 */
public class OnlineStatusUpdateEvent extends PresenceUpdateEvent {

    public OnlineStatusUpdateEvent(IdentityImpl identity, int sequence, User user, Presence oldPresence) {
        super(identity, sequence, user, oldPresence);
    }

    public OnlineStatus getNewStatus() {
        return getNewPresence().getStatus();
    }

    public OnlineStatus getOldStatus() {
        return oldPresence.getStatus();
    }

}
