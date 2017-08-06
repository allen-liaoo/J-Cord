package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.event.user.update.UserAvatarUpdateEvent;
import org.alienideology.jcord.event.user.update.UserDiscriminatorUpdateEvent;
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
        User user = (User) identity.getUser(json.getString("id"));
        if (user == null) {
            logger.log(LogLevel.FETAL, "[UNKNOWN USER] [PRESENCE_UPDATE_EVENT]");
            return;
        }

        String name = json.getString("username");
        String discrim = json.getString("discriminator");
        String avatar = json.has("avatar") && !json.isNull("avatar") ? json.getString("avatar") : null;

        if (!Objects.equals(user.getName(), name)) {
            String oldName = user.getName();
            user.setName(name);
            dispatchEvent(new UserNameUpdateEvent(identity, sequence, user, oldName));

            String oldDiscrim = user.getDiscriminator();
            user.setDiscriminator(discrim);

            dispatchEvent(new UserDiscriminatorUpdateEvent(identity, sequence, user, discrim));
        }
        if (!Objects.equals(user.getAvatarUrl(), avatar)) {
            String oldAvatar = user.getName();
            user.setAvatar(avatar);
            dispatchEvent(new UserAvatarUpdateEvent(identity, sequence, user, oldAvatar));
        }
    }

}
