package org.alienideology.jcord.handle.managers;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IMember;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.handle.user.OnlineStatus;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * ISelfManager - A manager that manages self user.
 * This managers is for both bot and client, which means setting email or password is not supported.
 *
 * @author AlienIdeology
 */
public interface ISelfManager {

    /**
     * The pattern for matching valid streaming urls.
     * Discord only support twitch streams currently.
     */
    Pattern TWITCH_URL = Pattern.compile("^(http|https)(://)(www\\.|)(twitch)(\\.tv/)(.+)");

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
     * @param name The new username.
     */
    void modifyUserName(String name);

    /**
     * Modify the avatar of this identity.
     *
     * @param image The buffered avatar.
     * @throws IOException When decoding image.
     */
    void modifyAvatar(BufferedImage image) throws IOException;

    /**
     * Modify the avatar of this identity.
     *
     * @param path The avatar file path.
     * @throws IOException When decoding image.
     */
    void modifyAvatar(String path) throws IOException;

    /**
     * Set the online status of this user.
     * Setting the status to {@link OnlineStatus#OFFLINE} or {@link OnlineStatus#UNKNOWN} will automatically be recognized as {@link OnlineStatus#INVISIBLE}.
     * Setting the status to {@link OnlineStatus#STREAMING} will be recognized as {@link OnlineStatus#ONLINE}.
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
     * use this method instead.
     *
     * @param guild The guild to leave.
     */
    void leaveGuild(IGuild guild);

}
