package org.alienideology.jcord.handle.user;

import org.alienideology.jcord.handle.IDiscordObject;
import org.alienideology.jcord.internal.object.user.Game;
import org.jetbrains.annotations.Nullable;

/**
 * IGame - A playing or streaming status of a user.
 *
 * @author AlienIdeology
 */
public interface IGame extends IDiscordObject {

    /**
     * Construct a game object from a name of the game.
     *
     * @param game The name of a game.
     * @return The new game object.
     */
    static IGame from(String game) {
        return new Game(null, game);
    }

    /**
     * Construct a game object from a game and streaming url.
     *
     * @param game The name of a game.
     * @param url The twitch url.
     * @return The new game object.
     */
    static IGame from(String game, String url) {
        return new Game(null, game, url);
    }

    /**
     * Get the name of the game.
     *
     * @return The string name.
     */
    String getName();

    /**
     * Get the game type.
     *
     * @return Playing or streaming.
     */
    IGame.Type getType();

    /**
     * Check if the game is a stream.
     *
     * @return True of the game status is streaming.
     */
    boolean isStreaming();

    /**
     * Get the url of the stream.
     * The url may be null for not streaming game.
     *
     * @return The url.
     */
    @Nullable
    String getUrl();

    /**
     * The game type.
     */
    enum Type {
        PLAYING (0),
        STREAMING (1);

        public final int key;

        Type(int key) {
            this.key = key;
        }
    }
}
