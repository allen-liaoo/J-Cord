package org.alienideology.jcord.handle.channel;

/**
 * VoiceChannel - An IGuildChannel for audio connections.
 * @author AlienIdeology
 */
public interface IVoiceChannel extends IGuildChannel {

    /**
     * Bitrate is the value of {@code kbps} a voice channel has.
     * Higher {@code kbps} means higher voice quality, but it also effects the
     * voice quality to users on mobile or with poor connections.
     *
     * @return The integer value of the bitrate of this voice channel.
     */
    int getBitrate();

    /**
     * User limit is the amount of members can be in a voice channel.
     * If the user limit is 0, it means there are no limits.
     *
     * @return The user limit of the voice channel.
     */
    int getUserLimit();

}
