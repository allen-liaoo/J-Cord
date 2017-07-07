package org.alienideology.jcord.handle.managers;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.Icon;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IMember;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.handle.user.OnlineStatus;

/**
 * ISelfManager - A manager that manages self user.
 * This managers is for both bot and client, which means setting email or password is not supported.
 *
 * @author AlienIdeology
 */
public interface ISelfManager {

    /**
     * Get the identity this self user is.
     *
     * @return The identity.
     */
    Identity getIdentity();

    /**
     * Get the self user.
     *
     * @return The user.
     */
    IUser getSelf();

    /**
     * Modify the user name of this identity.
     * Changing the username will cause the discriminator to randomize.
     *
     * @exception IllegalArgumentException
     *          If the username is not valid. See {@link IUser#isValidUsername(String)}
     * @param name The new username.
     */
    void modifyUserName(String name);

    /**
     * Modify the avatar of this identity.
     *
     * @param icon The avatar.
     */
    void modifyAvatar(Icon icon);

    /**
     * Set the online status of this user.
     * Here are some special cases: <br />
     * <ul>
     *     <li>Setting the status to {@link OnlineStatus#OFFLINE} or {@link OnlineStatus#UNKNOWN} will automatically be recognized as {@link OnlineStatus#INVISIBLE}.</li>
     *     <li>Setting the status to {@link OnlineStatus#STREAMING} will be recognized as {@link OnlineStatus#ONLINE}.</li>
     * </ul>
     *
     * @param status The online status.
     * @return ISelfManager for chaining.
     */
    ISelfManager setStatus(OnlineStatus status);

    /**
     * Set the playing status.
     * Use empty or null name to reset the playing game.
     *
     * @param name The game's name.
     * @return ISelfManager for chaining.
     */
    ISelfManager setPlaying(String name);

    /**
     * Set the streaming status.
     *
     * @exception IllegalArgumentException
     *          If the url is not a valid {@code Twitch} url.
     *
     * @param name The stream's name.
     * @param url The stream's url.
     * @return ISelfManager for chaining.
     */
    ISelfManager setStreaming(String name, String url);

    /**
     * Leave a guild.
     * Do not use {@link IGuildManager#kickMember(IMember)} to kick the self user,
     * use this event instead.
     *
     * @param guild The guild to leave.
     */
    void leaveGuild(IGuild guild);

}
