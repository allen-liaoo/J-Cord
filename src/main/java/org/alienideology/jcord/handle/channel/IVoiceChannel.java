package org.alienideology.jcord.handle.channel;

/**
 * VoiceChannel - An IGuildChannel for audio connections.
 * @author AlienIdeology
 */
public interface IVoiceChannel extends IGuildChannel {

    int getBitrate();

    int getUserLimit();

}
