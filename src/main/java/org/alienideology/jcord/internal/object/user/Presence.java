package org.alienideology.jcord.internal.object.user;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.user.IGame;
import org.alienideology.jcord.handle.user.IPresence;
import org.alienideology.jcord.handle.user.OnlineStatus;
import org.alienideology.jcord.internal.object.DiscordObject;
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

    public Presence(Identity identity, User user) {
        super(identity);
        this.user = user;
    }

    @Override
    public JSONObject toJson() {
        return new JSONObject()
                .put("status", status.getAppropriateKey())
                .put("afk", status.isIdle())
                .put("since", status.isIdle() ? since : JSONObject.NULL);
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    @Nullable
    public IGame getGame() {
        return game;
    }

    @Override
    public OnlineStatus getStatus() {
        return status;
    }

    @Override
    @Nullable
    public Long getSince() {
        return since;
    }

    public Presence setGame(Game game) {
        this.game = game;
        return this;
    }

    public Presence setStatus(OnlineStatus status) {
        this.status = status;
        return this;
    }

    public Presence setSince(Long since) {
        this.since = since;
        return this;
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
