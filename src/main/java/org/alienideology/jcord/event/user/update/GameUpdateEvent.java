package org.alienideology.jcord.event.user.update;

import org.alienideology.jcord.event.user.PresenceUpdateEvent;
import org.alienideology.jcord.handle.user.IGame;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.user.Presence;
import org.alienideology.jcord.internal.object.user.User;

/**
 * @author AlienIdeology
 */
public class GameUpdateEvent extends PresenceUpdateEvent {

    public GameUpdateEvent(IdentityImpl identity, int sequence, User user, Presence oldPresence) {
        super(identity, sequence, user, oldPresence);
    }

    public IGame getNewGame() {
        return getNewPresence().getGame();
    }

    public IGame getOldStatus() {
        return oldPresence.getGame();
    }

}
