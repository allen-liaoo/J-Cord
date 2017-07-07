package org.alienideology.jcord.handle.user;

import org.alienideology.jcord.handle.IDiscordObject;
import org.alienideology.jcord.handle.IMention;
import org.alienideology.jcord.handle.ISnowFlake;
import org.alienideology.jcord.handle.channel.IPrivateChannel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * User - A base entity, can be a member of guild/private channel, and bot/human.
 * @author AlienIdeology
 */
public interface IUser extends IDiscordObject, ISnowFlake, IMention {

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
    List<Character> USERNAME_DISALLOWED_CHARS = Arrays.asList('@', '`', ':', '#', /* Zero Width */ '\u200B');

    /**
     * A list of disallowed user names.
     */
    List<String> USERNAME_DISALLOWED_NAMES = Arrays.asList("DISCORDTAG", "EVERYONE", "HERE");

    /**
     * The pattern for matching valid streaming urls.
     * Discord only support twitch streams currently.
     */
    Pattern PATTERN_TWITCH_URL = Pattern.compile("^(http|https)(://)(www\\.|)(twitch)(\\.tv/)(.+)");

    /**
     * Checks if an username is valid or not.
     *
     * Validations:
     * <ul>
     *     <li>Username may not be null or empty.</li>
     *     <li>The username's length must be between {@link #USERNAME_LENGTH_MIN} and {@link #USERNAME_LENGTH_MAX}.</li>
     *     <li>The username must not contain characters in {@link #USERNAME_DISALLOWED_CHARS}.</li>
     *     <li>The username must not equals and username in {@link #USERNAME_DISALLOWED_NAMES}.</li>
     * </ul>
     *
     * @param username The username to be check with.
     * @return True if the username is valid.
     */
    static boolean isValidUsername(String username) {
        return username != null && !username.isEmpty() &&
                username.length() >= USERNAME_LENGTH_MIN &&
                username.length() <= USERNAME_LENGTH_MAX &&
                Collections.disjoint(Arrays.asList(username.toCharArray()), USERNAME_DISALLOWED_CHARS) &&
                !USERNAME_DISALLOWED_NAMES.contains(username.toUpperCase());
    }

    /**
     * Get or open a PrivateChannel with this user
     *
     * @return The PrivateChannel with this user
     */
    IPrivateChannel getPrivateChannel();

    /**
     * Close the PrivateChannel with this user
     * Note that this action can be undone by using {@link #getPrivateChannel()} to reopen a new PrivateChannel.
     */
    void closePrivateChannel();

    /**
     * Get the username of this user.
     *
     * @return The string username.
     */
    String getName();

    /**
     * Get the discriminator of this user. It is a four digit number.
     *
     * @return The string discriminator.
     */
    String getDiscriminator();

    /**
     * Get the avatar url of this user.
     *
     * @return The url.
     */
    String getAvatar();

    /**
     * Get the email of this user. Requires email OAuth2 scope.
     *
     * @return The string email.
     */
    String getEmail();

    /**
     * Get the presence object of this user.
     * The presence contains information about a user's online status and playing game.
     *
     * @return The presence object.
     */
    Presence getPresence();

    /**
     * @return True if this user is a bot.
     */
    boolean isBot();

    /**
     * @return True if this user is a webhook.
     */
    boolean isWebHook();

    /**
     * @return True if this user is verified (claimed account).
     */
    boolean isVerified();

    /**
     * @return True if this user has two factor authentication enabled.
     */
    boolean isMFAEnabled();

    /**
     * @return True if this user is identity itself.
     */
    default boolean isSelf() {
        return getIdentity().getSelf().getId().equals(getId());
    }

    /**
     * Gets the formatted mention of the user. Same as IUser#mention(true)
     *
     * @return The formatted String.
     */
    @Override
    default String mention() {
        return "<@"+getId()+">";
    }

    /**
     * Gets the formatted mention of the user
     *
     * @param val Include the nickname or not
     * @return The formatted String.
     */
    default String mention(boolean val) {
        return val ? "<!@"+getId()+">" : "<@"+getId()+">";
    }
}
