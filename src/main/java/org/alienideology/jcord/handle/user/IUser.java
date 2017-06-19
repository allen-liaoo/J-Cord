package org.alienideology.jcord.handle.user;

import org.alienideology.jcord.handle.IDiscordObject;
import org.alienideology.jcord.handle.IMention;
import org.alienideology.jcord.handle.ISnowFlake;
import org.alienideology.jcord.handle.channel.IPrivateChannel;

/**
 * User - A base entity, can be a member of guild/private channel, and bot/human.
 * @author AlienIdeology
 */
public interface IUser extends IDiscordObject, ISnowFlake, IMention {

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
