package org.alienideology.jcord.handle.managers;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.Icon;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IMember;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.handle.user.OnlineStatus;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * ISelfManager - A manager that manages self user.
 * This managers is for both bot and client, which means setting email or password is not supported.
 *
 * @author AlienIdeology
 */
public interface ISelfManager {

    /**
     * The minimum length of a username.
     */
    int USERNAME_LENGTH_MIN = 2;

    /**
     * The maximum length of a username.
     */
    int USERNAME_LENGTH_MAX = 32;

    /**
     * A list of disallowed characters to be put into the user name.
     */
    List<Character> USERNAME_DISALLOWED_CHARS = Arrays.asList('@', '`', ':', '#');

    /**
     * The pattern for matching valid streaming urls.
     * Discord only support twitch streams currently.
     */
    Pattern TWITCH_URL = Pattern.compile("^(http|https)(://)(www\\.|)(twitch)(\\.tv/)(.+)");

    /**
     * Checks if an username is valid or not.
     *
     * Validations: <br />
     * <ul>
     *     <li>
     *         The username's length must be between {@link ISelfManager#USERNAME_LENGTH_MIN}
     *         and {@link ISelfManager#USERNAME_LENGTH_MAX}.
     *     </li>
     *     <li>
     *         The username must not contain characters in {@link ISelfManager#USERNAME_DISALLOWED_CHARS}.
     *     </li>
     *     <li>
     *         The username must not equals {@code "discordtag"}, {@code "everyone}, or {@code here}.
     *     </li>
     * </ul>
     *
     *
     * @param username The username to be check with.
     * @return True if the username is valid.
     */
    static boolean isValidUsername(String username) {
        return !(username != null && !username.isEmpty()) ||
                username.length() >= USERNAME_LENGTH_MIN &&
                username.length() <= USERNAME_LENGTH_MAX &&
                !contains(username, USERNAME_DISALLOWED_CHARS) &&
                !username.equalsIgnoreCase("discordtag") &&
                !username.equalsIgnoreCase("everyone") &&
                !username.equalsIgnoreCase("here");
    }

    static boolean contains(String str, List<Character> arg) {
        for (char c : str.toCharArray()) {
            if (arg.contains(c))
                return true;
        }
        return false;
    }

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
     *          If the username is not valid. See {@link #isValidUsername(String)}
     * @param name The new username.
     */
    void modifyUserName(String name);

    /**
     * Modify the avatar of this identity.
     *
     * @param icon The avatar.
     * @throws IOException When decoding image.
     */
    void modifyAvatar(Icon icon) throws IOException;

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
     * use this event instead.
     *
     * @param guild The guild to leave.
     */
    void leaveGuild(IGuild guild);

}
