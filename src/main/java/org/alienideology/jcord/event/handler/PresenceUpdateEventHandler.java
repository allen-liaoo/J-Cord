package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.event.user.update.GameUpdateEvent;
import org.alienideology.jcord.event.user.update.OnlineStatusUpdateEvent;
import org.alienideology.jcord.handle.user.IGame;
import org.alienideology.jcord.handle.user.OnlineStatus;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.user.Game;
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
        Presence presence = (Presence) user.getPresence();
        OnlineStatus status = OnlineStatus.getByKey(json.getString("status"));
        Game game = null;

        if (json.has("game") && !json.isNull("game")) {
            game = builder.buildGame(json.getJSONObject("game"));
        }

        if (!Objects.equals(presence.getStatus(), status)) {
            OnlineStatus oldStatus = presence.getStatus();
            presence.setStatus(status);
            dispatchEvent(new OnlineStatusUpdateEvent(identity, sequence, user, oldStatus));
        }
        if (!Objects.equals(presence.getGame(), game)) {
            IGame oldGame = presence.getGame();
            presence.setGame(game);
            dispatchEvent(new GameUpdateEvent(identity, sequence, user, oldGame));
        }
    }

}
