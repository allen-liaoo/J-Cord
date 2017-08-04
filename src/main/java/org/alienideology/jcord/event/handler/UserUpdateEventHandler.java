package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.event.user.update.UserAvatarUpdateEvent;
import org.alienideology.jcord.event.user.update.UserNameUpdateEvent;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.user.User;
import org.alienideology.jcord.util.log.LogLevel;
import org.json.JSONObject;

import java.util.Objects;

/**
 * @author AlienIdeology
 */
public class UserUpdateEventHandler extends EventHandler {

    public UserUpdateEventHandler(IdentityImpl identity) {
        super(identity);
    }

    @Override
    public void dispatchEvent(JSONObject json, int sequence) {
        User oldUser = (User) identity.getUser(json.getString("id"));
        if (oldUser == null) {
            logger.log(LogLevel.FETAL, "[UNKNOWN USER] [PRESENCE_UPDATE_EVENT]");
            return;
        }
        User newUser = builder.buildUser(json);
        identity.updateUser(newUser);

        if (!Objects.equals(oldUser.getName(), newUser.getName())) {
            dispatchEvent(new UserNameUpdateEvent(identity, sequence, newUser, oldUser));
        }
        if (!Objects.equals(oldUser.getAvatarUrl(), newUser.getAvatarUrl())) {
            dispatchEvent(new UserAvatarUpdateEvent(identity, sequence, newUser, oldUser));
        }
    }

}
