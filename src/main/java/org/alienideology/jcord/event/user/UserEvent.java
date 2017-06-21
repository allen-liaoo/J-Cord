package org.alienideology.jcord.event.user;

import org.alienideology.jcord.event.Event;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.user.User;

/**
 * @author AlienIdeology
 */
public class UserEvent extends Event {

    protected User user;

    public UserEvent(IdentityImpl identity, int sequence, User user) {
        super(identity, sequence);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
