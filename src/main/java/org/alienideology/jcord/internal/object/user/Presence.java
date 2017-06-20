package org.alienideology.jcord.internal.object.user;

import org.alienideology.jcord.internal.object.DiscordObject;
import org.alienideology.jcord.internal.object.IdentityImpl;

/**
 * @author AlienIdeology
 */
public final class Presence extends DiscordObject {

    private final User user;
    private Game game;
    private Status status;

    public Presence(IdentityImpl identity, User user, Game game, Status status) {
        super(identity);
        this.user = user;
        this.game = game;
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public Game getGame() {
        return game;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Presence{" +
                "user=" + user +
                ", game=" + game +
                ", status=" + status +
                '}';
    }
}