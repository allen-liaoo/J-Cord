package org.alienideology.jcord.event.user.update;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.user.PresenceUpdateEvent;
import org.alienideology.jcord.handle.user.IGame;
import org.alienideology.jcord.internal.object.user.User;

/**
 * @author AlienIdeology
 */
public class GameUpdateEvent extends PresenceUpdateEvent {

    private final IGame oldGame;

    public GameUpdateEvent(Identity identity, int sequence, User user, IGame oldGame) {
        super(identity, sequence, user);
        this.oldGame = oldGame;
    }

    public IGame getNewGame() {
        return getPresence().getGame();
    }

    public IGame getOldGame() {
        return oldGame;
    }

}
