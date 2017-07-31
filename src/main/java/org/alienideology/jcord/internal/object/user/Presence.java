package org.alienideology.jcord.internal.object.user;

import org.alienideology.jcord.handle.user.IGame;
import org.alienideology.jcord.handle.user.IPresence;
import org.alienideology.jcord.handle.user.OnlineStatus;
import org.alienideology.jcord.internal.object.DiscordObject;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.Jsonable;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

/**
 * @author AlienIdeology
 */
public final class Presence extends DiscordObject implements IPresence, Jsonable {

    private final User user;
    private Game game;
    private OnlineStatus status;
    private Long since;

    public Presence(IdentityImpl identity, User user, Game game, OnlineStatus status, Long since) {
        super(identity);
        this.user = user;
        this.game = game;
        this.status = status;
        this.since = since;
    }

    @Override
    public JSONObject toJson() {
        JSONObject presence = new JSONObject()
                .put("status", status.getAppropriateKey())
                .put("afk", status.isIdle())
                .put("since", status.isIdle() ? since : JSONObject.NULL);

        return presence;
    }

    /**
     * Get the user this presence belongs to.
     *
     * @return The user.
     */
    @Override
    public User getUser() {
        return user;
    }

    /**
     * Get the game this user is playing.
     * The game may be null if the user is not playing or streaming anything.
     *
     * @return The game.
     */
    @Override
    @Nullable
    public IGame getGame() {
        return game;
    }

    /**
     * Get the online status of this user.
     *
     * @return The online status.
     */
    @Override
    public OnlineStatus getStatus() {
        return status;
    }

    /**
     * Get the time in milliseconds(Unix time) when the client went idle.
     * Returns null if the client is not idle.
     *
     * @return The time since client went idle.
     */
    @Override
    @Nullable
    public Long getSince() {
        return since;
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
