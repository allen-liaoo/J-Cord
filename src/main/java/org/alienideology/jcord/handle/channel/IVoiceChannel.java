package org.alienideology.jcord.handle.channel;

/**
 * VoiceChannel - An IGuildChannel for audio connections.
 * @author AlienIdeology
 */
public interface IVoiceChannel extends IGuildChannel {

    /**
     * @return The integer value of the bitrate of this voice channel.
     */
    int getBitrate();

    /**
     * @return The user limit of the voice channel.
     */
    int getUserLimit();

}
