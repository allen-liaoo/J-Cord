package org.alienideology.jcord.handle.guild;

import org.alienideology.jcord.handle.IVoiceState;
import org.alienideology.jcord.handle.channel.IVoiceChannel;

/**
 * @author AlienIdeology
 */
public interface IGuildVoiceState extends IVoiceState {

    IGuild geGuild();

    IVoiceChannel getVoiceChannel();

    IMember getMember();

    default boolean isMuted() {
        return isMutedByServer() || isSelfMuted();
    }

    boolean isMutedByServer();

    default boolean isDeafened() {
        return isDeafenedByServer() || isSelfDeafened();
    }

    boolean isDeafenedByServer();

    boolean isSuppressed();

    default boolean isInChannel() {
        return getChannel() != null;
    }

}
