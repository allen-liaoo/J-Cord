package org.alienideology.jcord.handle.user;

import org.alienideology.jcord.handle.IMention;
import org.alienideology.jcord.handle.ISnowFlake;
import org.alienideology.jcord.handle.channel.IPrivateChannel;
import org.alienideology.jcord.internal.object.channel.PrivateChannel;

/**
 * User - A base entity, can be a member of guild/private channel, and bot/human.
 * @author AlienIdeology
 */
public interface IUser extends ISnowFlake, IMention {

    /**
     * Get or open a PrivateChannel with this user
     * @return The PrivateChannel with this user
     */
    IPrivateChannel getPrivateChannel();

    String getName();

    String getDiscriminator();

    String getAvatar();

    String getEmail();

    boolean isBot();

    boolean isWebHook();

    boolean isVerified();

    boolean isMFAEnabled();

    boolean isSelf();

    @Override
    String getId();

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
