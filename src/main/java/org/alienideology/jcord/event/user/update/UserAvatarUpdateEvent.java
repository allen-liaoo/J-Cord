package org.alienideology.jcord.event.user.update;

import org.alienideology.jcord.event.user.UserUpdateEvent;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.user.User;

/**
 * @author AlienIdeology
 */
public class UserAvatarUpdateEvent extends UserUpdateEvent {

    private final String oldAvatar;

    public UserAvatarUpdateEvent(IdentityImpl identity, int sequence, User user, String oldAvatar) {
        super(identity, sequence, user);
        this.oldAvatar = oldAvatar;
    }

    public String getNewAvatar() {
        return user.getAvatarUrl();
    }

    public String getOldAvatar() {
        return oldAvatar;
    }

}
