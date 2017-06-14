package org.alienideology.jcord.handle;

import org.alienideology.jcord.internal.object.Mention;
import org.alienideology.jcord.internal.object.SnowFlake;
import org.alienideology.jcord.internal.object.channel.PrivateChannel;

public interface IUser extends SnowFlake, Mention {

    /**
     * Get or open a PrivateChannel with this user
     * @return The PrivateChannel with this user
     */
    PrivateChannel getPrivateChannel();

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
    String mention();

    /**
     * Gets the formatted mention of the user
     *
     * @param val Include the nickname or not
     * @return The formatted String.
     */
    String mention(boolean val);
}
