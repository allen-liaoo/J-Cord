package org.alienideology.jcord.event.user.update;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.user.UserUpdateEvent;
import org.alienideology.jcord.handle.user.IUser;

/**
 * @author AlienIdeology
 */
public class UserDiscriminatorUpdateEvent extends UserUpdateEvent {

    private final String oldDiscrim;

    public UserDiscriminatorUpdateEvent(Identity identity, int sequence, IUser user, String oldDiscrim) {
        super(identity, sequence, user);
        this.oldDiscrim = oldDiscrim;
    }

    public String getNewDiscriminator() {
        return user.getDiscriminator();
    }

    public String getOldDiscriminator() {
        return oldDiscrim;
    }

}
