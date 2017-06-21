package org.alienideology.jcord.event.user;

import org.alienideology.jcord.handle.user.Presence;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.user.User;

/**
 * @author AlienIdeology
 */
public class PresenceUpdateEvent extends UserEvent {

    private Presence oldPresence;

    public PresenceUpdateEvent(IdentityImpl identity, int sequence, User user, Presence oldPresence) {
        super(identity, sequence, user);
        this.oldPresence = oldPresence;
    }

    public Presence getNewPresence() {
        return user.getPresence();
    }

    public Presence getOldPresence() {
        return oldPresence;
    }

}
