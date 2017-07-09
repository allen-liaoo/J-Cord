package org.alienideology.jcord.event.user.update;

import org.alienideology.jcord.event.user.UserUpdateEvent;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.user.User;

/**
 * @author AlienIdeology
 */
public class UserAvatarUpdateEvent extends UserUpdateEvent {

    public UserAvatarUpdateEvent(IdentityImpl identity, int sequence, User user, User oldUser) {
        super(identity, sequence, user, oldUser);
    }

    public String getNewAvatar() {
        return user.getAvatarUrl();
    }

    public String getOldAvatar() {
        return oldUser.getAvatarUrl();
    }

}
