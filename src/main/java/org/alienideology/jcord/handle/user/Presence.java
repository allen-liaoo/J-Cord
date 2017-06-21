package org.alienideology.jcord.handle.user;

import com.sun.istack.internal.Nullable;
import org.alienideology.jcord.internal.object.DiscordObject;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.user.User;

/**
 * Presence - A status of a user, containing information about the user online status and game.
 *
 * @author AlienIdeology
 * @since 0.0.7
 */
public final class Presence extends DiscordObject {

    private final User user;
    private Game game;
    private OnlineStatus status;

    public Presence(IdentityImpl identity, User user, Game game, OnlineStatus status) {
        super(identity);
        this.user = user;
        this.game = game;
        this.status = status;
    }

    /**
     * Get the user this presence belongs to.
     *
     * @return The user.
     */
    public User getUser() {
        return user;
    }

    /**
     * Get the game this user is playing.
     * The game may be null if the user is not playing or streaming anything.
     *
     * @return The game.
     */
    @Nullable
    public Game getGame() {
        return game;
    }

    /**
     * Get the online status of this user.
     *
     * @return The online status.
     */
    public OnlineStatus getStatus() {
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
