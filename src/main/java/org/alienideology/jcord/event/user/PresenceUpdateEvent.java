package org.alienideology.jcord.event.user;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.user.IPresence;
import org.alienideology.jcord.handle.user.IUser;

/**
 * @author AlienIdeology
 */
public class PresenceUpdateEvent extends UserEvent {

    public PresenceUpdateEvent(Identity identity, int sequence, IUser user) {
        super(identity, sequence, user);
    }

    public IPresence getPresence() {
        return user.getPresence();
    }

}
