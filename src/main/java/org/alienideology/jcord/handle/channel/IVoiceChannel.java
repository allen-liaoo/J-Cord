package org.alienideology.jcord.handle.channel;

/**
 * VoiceChannel - An IGuildChannel for audio connections.
 * @author AlienIdeology
 */
public interface IVoiceChannel extends IGuildChannel, Comparable<IVoiceChannel> {

    /**
     * The minimum bitrate of a voice channel.
     */
    int VOICE_CHANNEL_BITRATE_MIN = 8000;
    /**
     * The maximum bitrate of a voice channel in a normal server.
     */
    int VOICE_CHANNEL_BITRATE_MAX = 96000;
    /**
     * The maximum bitrate of a voice channel in a VIP server.
     */
    int VOICE_CHANNEL_BITRATE_VIP_MAX = 128000;
    /**
     * The minimum user limit of a voice channel.
     * {code 0} user limit refers to no limit.
     */
    int VOICE_CHANNEL_USER_LIMIT_MIN = 0;
    /**
     * The maximum user limit of a voice channel.
     */
    int VOICE_CHANNEL_USER_LIMIT_MAX = 99;

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

    /**
     * Compare this to another VoiceChannel by their integer positions.
     * @see IGuildChannel#getPosition()
     *
     * @param o The other VoiceChannel.
     * @return the value {@code 0} if the positions are the same;
     *         a value less than {@code 0} if this voice channel's position is smaller than the other voice channel; and
     *         a value greater than {@code 0} if this voice channel's position is greater than the other voice channel
     */
    @Override
    default int compareTo(IVoiceChannel o) {
        return Integer.compare(this.getPosition(), o.getPosition());
    }

}
