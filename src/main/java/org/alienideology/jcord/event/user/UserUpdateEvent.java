package org.alienideology.jcord.event.user;

import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.user.User;

/**
 * @author AlienIdeology
 */
public class UserUpdateEvent extends UserEvent {

    protected User oldUser;

    public UserUpdateEvent(IdentityImpl identity, int sequence, User user, User oldUser) {
        super(identity, sequence, user);
        this.oldUser = oldUser;
    }

    /**
     * @return The new, updated user.
     */
    @Override
    public User getUser() {
        return super.getUser();
    }

    public User getOldUser() {
        return oldUser;
    }

}
