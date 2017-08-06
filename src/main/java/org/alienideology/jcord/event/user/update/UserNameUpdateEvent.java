package org.alienideology.jcord.event.user.update;

import org.alienideology.jcord.event.user.UserUpdateEvent;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.user.User;

/**
 * @author AlienIdeology
 */
public class UserNameUpdateEvent extends UserUpdateEvent {

    private final String oldName;

    public UserNameUpdateEvent(IdentityImpl identity, int sequence, User user, String oldName) {
        super(identity, sequence, user);
        this.oldName = oldName;
    }

    public String getNewName() {
        return user.getName();
    }

    public String getOldName() {
        return oldName;
    }

}
