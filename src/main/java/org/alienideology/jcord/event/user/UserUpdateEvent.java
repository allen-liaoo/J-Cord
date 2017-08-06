package org.alienideology.jcord.event.user;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.user.IUser;

/**
 * @author AlienIdeology
 */
public class UserUpdateEvent extends UserEvent {

    public UserUpdateEvent(Identity identity, int sequence, IUser user) {
        super(identity, sequence, user);
    }

    /**
     * @return The new, updated user.
     */
    @Override
    public IUser getUser() {
        return super.getUser();
    }

}
