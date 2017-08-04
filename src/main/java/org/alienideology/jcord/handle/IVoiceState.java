package org.alienideology.jcord.handle;

import org.alienideology.jcord.handle.channel.IAudioChannel;
import org.alienideology.jcord.handle.user.IUser;
import org.jetbrains.annotations.Nullable;

/**
 * IVoiceState - A general voice state of an user, for voice channel or call connection.
 * @author AlienIdeology
 */
public interface IVoiceState extends IDiscordObject {

    /**
     * Get the user of this voice state.
     *
     * @return The user.
     */
    IUser getUser();

    /**
     * Get the channel this state is connected to.
     * If there is no voice channel connected, then this returns null.
     *
     * @return The audio channel.
     */
    @Nullable
    IAudioChannel getChannel();

    /**
     * Get the session ID of this state, used to perform internal tasks.
     *
     * @return The session id.
     */
    String getSessionId();

    /**
     * Check if the voice state user is self muted.
     *
     * @return True if the voice state user is self muted.
     */
    boolean isSelfMuted();

    /**
     * Check if the voice state user is self deafened.
     *
     * @return True if the voice state user is self deafened.
     */
    boolean isSelfDeafened();

}
