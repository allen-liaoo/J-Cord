package org.alienideology.jcord.event.user;

import org.alienideology.jcord.handle.user.IPresence;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.user.Presence;
import org.alienideology.jcord.internal.object.user.User;

/**
 * @author AlienIdeology
 */
public class PresenceUpdateEvent extends UserEvent {

    protected Presence oldPresence;

    public PresenceUpdateEvent(IdentityImpl identity, int sequence, User user, Presence oldPresence) {
        super(identity, sequence, user);
        this.oldPresence = oldPresence;
    }

    public IPresence getNewPresence() {
        return user.getPresence();
    }

    public IPresence getOldPresence() {
        return oldPresence;
    }

}
