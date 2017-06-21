package org.alienideology.jcord.handle.user;

import com.sun.istack.internal.Nullable;
import org.alienideology.jcord.internal.object.DiscordObject;
import org.alienideology.jcord.internal.object.IdentityImpl;

/**
 * Game - A playing or streaming status of a user.
 * @author AlienIdeology
 */
public final class Game extends DiscordObject {

    private String name;
    private GameType type;
    private String url;

    public Game(IdentityImpl identity, String name) {
        super(identity);
        this.name = name;
        this.type = GameType.PLAYING;
        this.url = null;
    }

    public Game(IdentityImpl identity, String name, String url) {
        super(identity);
        this.name = name;
        this.type = url != null ? GameType.STREAMING : GameType.PLAYING;
        this.url = url;
    }

    /**
     * Get the name of the game.
     *
     * @return The string name.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the game type.
     *
     * @return Playing or streaming.
     */
    public GameType getType() {
        return type;
    }

    /**
     * Check if the game is a stream.
     *
     * @return True of the game status is streaming.
     */
    public boolean isStreaming() {
        return type.equals(GameType.STREAMING);
    }

    /**
     * Get the url of the stream.
     * The url may be null for not streaming game.
     *
     * @return The url.
     */
    @Nullable
    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Game) {
            Game game = (Game) obj;
            return name.equals(game.getName())
                    && type.equals(game.getType())
                    && url.equals(game.getUrl());
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Game{" +
                "name='" + name + '\'' +
                ", type=" + type +
                '}';
    }

    /**
     * The game type.
     */
    public enum GameType {
        PLAYING (0),
        STREAMING (1);

        public final int id;

        GameType(int id) {
            this.id = id;
        }
    }
}
