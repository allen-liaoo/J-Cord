package org.alienideology.jcord.handle.user;

import org.alienideology.jcord.handle.IDiscordObject;
import org.jetbrains.annotations.Nullable;

/**
 * IPresence - A status of a user, containing information about the user online status and game.
 *
 * @author AlienIdeology
 */
public interface IPresence extends IDiscordObject {

    /**
     * Get the user this presence belongs to.
     *
     * @return The user.
     */
    IUser getUser();

    /**
     * Get the game this user is playing.
     * The game may be null if the user is not playing or streaming anything.
     *
     * @return The game.
     */
    @Nullable
    IGame getGame();

    /**
     * Get the online status of this user.
     *
     * @return The online status.
     */
    OnlineStatus getStatus();

    /**
     * Get the time in milliseconds(Unix time) when the client went idle.
     * Returns null if the client is not idle.
     *
     * @return The time since client went idle.
     */
    @Nullable
    Long getSince();

}
