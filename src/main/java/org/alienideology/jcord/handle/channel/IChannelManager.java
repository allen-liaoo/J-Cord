package org.alienideology.jcord.handle.channel;

/**
 * IChannelManager - A manager for both text and voice channels.
 * @author AlienIdeology
 */
public interface IChannelManager {

    void modifyName(IGuildChannel channel, String name);

    void modifyName(String channelId, String name);

    void modifyPosition(IGuildChannel channel, int position);

    void modifyPosition(String channelId, int position);

    void modifyTopic(ITextChannel channel, String topic);

    void modifyTopic(String channelId, String topic);

    void modifyBitrate(IVoiceChannel channel, int bitrate);

    void modifyBitrate(String channelId, int bitrate);

    void modifyUserLimit(IVoiceChannel channel, int limit);

    void modifyUserLimit(String channelId, int limit);

    void deleteChannel(IGuildChannel channel);

}
