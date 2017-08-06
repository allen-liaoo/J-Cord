package org.alienideology.jcord.event.user;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.Event;
import org.alienideology.jcord.handle.user.IUser;

/**
 * @author AlienIdeology
 */
public class UserEvent extends Event {

    protected IUser user;

    public UserEvent(Identity identity, int sequence, IUser user) {
        super(identity, sequence);
        this.user = user;
    }

    public IUser getUser() {
        return user;
    }

}
