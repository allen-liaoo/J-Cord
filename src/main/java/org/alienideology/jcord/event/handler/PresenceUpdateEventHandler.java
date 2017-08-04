package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.event.user.update.GameUpdateEvent;
import org.alienideology.jcord.event.user.update.OnlineStatusUpdateEvent;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.user.Presence;
import org.alienideology.jcord.internal.object.user.User;
import org.alienideology.jcord.util.log.LogLevel;
import org.json.JSONObject;

import java.util.Objects;

/**
 * @author AlienIdeology
 */
public class PresenceUpdateEventHandler extends EventHandler {

    public PresenceUpdateEventHandler(IdentityImpl identity) {
        super(identity);
    }

    @Override
    public void dispatchEvent(JSONObject json, int sequence) {
        User user = (User) identity.getUser(json.getJSONObject("user").getString("id"));
        if (user == null) {
            logger.log(LogLevel.FETAL, "[UNKNOWN USER] [PRESENCE_UPDATE_EVENT]");
            return;
        }
        Presence oldPresence = (Presence) user.getPresence();
        builder.buildPresence(json, user); // Presence are automatically set to the user
        Presence newPresence = (Presence) user.getPresence();

        if (!Objects.equals(newPresence.getStatus(), oldPresence.getStatus())) {
            dispatchEvent(new OnlineStatusUpdateEvent(identity, sequence, user, oldPresence));
        }
        if (!Objects.equals(newPresence.getGame(), oldPresence.getGame())) {
            dispatchEvent(new GameUpdateEvent(identity, sequence, user, oldPresence));
        }
    }

}
