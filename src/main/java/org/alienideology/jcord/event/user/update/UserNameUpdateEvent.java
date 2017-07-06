package org.alienideology.jcord.event.user.update;

import org.alienideology.jcord.event.user.UserUpdateEvent;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.user.User;

/**
 * @author AlienIdeology
 */
public class UserNameUpdateEvent extends UserUpdateEvent {

    public UserNameUpdateEvent(IdentityImpl identity, int sequence, User user, User oldUser) {
        super(identity, sequence, user, oldUser);
    }

    public String getNewName() {
        return user.getName();
    }

    public String getOldName() {
        return oldUser.getName();
    }

    public String getNewDiscriminator() {
        return user.getDiscriminator();
    }

    public String getOldDiscriminator() {
        return oldUser.getDiscriminator();
    }

}
