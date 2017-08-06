package org.alienideology.jcord.handle.managers;

import org.alienideology.jcord.handle.IDiscordObject;
import org.alienideology.jcord.handle.Icon;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IMember;
import org.alienideology.jcord.handle.user.IGame;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.handle.user.OnlineStatus;

/**
 * ISelfManager - A manager that manages self user.
 * This managers is for both bot and client, which means setting email or password is not supported.
 *
 * @author AlienIdeology
 */
public interface ISelfManager extends IDiscordObject {

    /**
     * Get the self user.
     *
     * @return The user.
     */
    IUser getSelf();

    /**
     * Modify the user name of this identity.
     *
     * @exception IllegalArgumentException
     *          If the username is not valid. See {@link IUser#isValidUsername(String)}
     *
     * @param name The new username.
     */
    default void modifyUserName(String name) {
        getIdentity().getSelfModifier();
    }

    /**
     * Modify the avatar of this identity.
     *
     * @param icon The avatar.
     */
    default void modifyAvatar(Icon icon) {
        getIdentity().getSelfModifier();
    }

    /**
     * Set the online status of this user.
     * Here are some special cases: <br />
     * <ul>
     *     <li>Setting the status to {@link OnlineStatus#OFFLINE} or {@link OnlineStatus#UNKNOWN} will automatically be recognized as {@link OnlineStatus#INVISIBLE}.</li>
     *     <li>Setting the status to {@link OnlineStatus#STREAMING} will be recognized as {@link OnlineStatus#ONLINE}.</li>
     * </ul>
     *
     * @param status The online status.
     */
    default void setStatus(OnlineStatus status) {
        setPresence(status, getIdentity().getSelf().getPresence().getGame());
    }

    /**
     * Set the game status.
     * Use empty or null name to reset the playing game.
     * @see IGame#from(String)
     * @see IGame#from(String, String)
     *
     * @param game The game.
     */
    default void setGame(IGame game) {
        if (game.getUrl() != null && !game.getUrl().matches(IUser.PATTERN_TWITCH_URL.pattern())) {
            throw new IllegalArgumentException("Streaming game type only support valid twitch urls!");
        }

        setPresence(getIdentity().getSelf().getPresence().getStatus(), game);
    }

    /**
     * Set the presence of this identity.
     * This can set both the online status and game at the same time.
     * @see #setStatus(OnlineStatus)
     * @see #setGame(IGame)
     *
     * @param status The new online status.
     * @param game The new game.
     */
    void setPresence(OnlineStatus status, IGame game);

    /**
     * Leave a guild.
     * Do not use {@link IGuildManager#kickMember(IMember)} to kick the self user,
     * use this instead.
     *
     * @param guild The guild to leave.
     */
    void leaveGuild(IGuild guild);

}
