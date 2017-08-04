package org.alienideology.jcord.handle.guild;

import org.alienideology.jcord.handle.IVoiceState;
import org.alienideology.jcord.handle.channel.IVoiceChannel;
import org.jetbrains.annotations.Nullable;

/**
 * IGuildVoiceState - A voice state for a member in a guild.
 *
 * @author AlienIdeology
 */
public interface IGuildVoiceState extends IVoiceState {

    /**
     * Get the guild of this voice state.
     *
     * @return The guild.
     */
    IGuild geGuild();

    /**
     * Get the voice channel this member is connected to,
     * or null if this member is not connected to any voice channel.
     *
     * @return The voice channel.
     */
    @Nullable
    IVoiceChannel getVoiceChannel();

    /**
     * Get the member of this voice state.
     *
     * @return The member.
     */
    IMember getMember();

    /**
     * Check if this member is muted, either self muted or by an admin in the guild.
     *
     * @return True if the member is muted.
     */
    default boolean isMuted() {
        return isMutedByServer() || isSelfMuted();
    }

    /**
     * Check if this member is muted by an admin in the guild.
     *
     * @return True if this member is muted by an admin in the guild.
     */
    boolean isMutedByServer();

    /**
     * Check if this member is deafened, either self deafened or by an admin in the guild.
     *
     * @return True if the member is deafened.
     */
    default boolean isDeafened() {
        return isDeafenedByServer() || isSelfDeafened();
    }

    /**
     * Check if this member is deafened by an admin in the guild.
     *
     * @return True if this member is deafened by an admin in the guild.
     */
    boolean isDeafenedByServer();

    /**
     * Check if this member is being suppressed (Unable to speak).
     * This happens when the member does not have {@link org.alienideology.jcord.handle.permission.Permission#SPEAK} permission,
     * or when the channel is an AFK channel.
     *
     * @return True if the member is being suppressed.
     */
    boolean isSuppressed();

    /**
     * Check if this member is connected to a voice channel or not.
     *
     * @return True if this member is connected to a voice channel.
     */
    default boolean isInChannel() {
        return getChannel() != null;
    }

}
