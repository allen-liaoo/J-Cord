package org.alienideology.jcord.handle.channel;

import org.alienideology.jcord.handle.IDiscordObject;
import org.alienideology.jcord.handle.ISnowFlake;
import org.alienideology.jcord.internal.object.channel.Channel;

/**
 * Channel - A communication pipeline
 * Can be IGuildChannel, VoiceChannel or PrivateChannel
 *
 * @author AlienIdeology
 */
public interface IChannel extends IDiscordObject, ISnowFlake {

    Channel.Type getType();

    default boolean isType(Channel.Type type) {
        return getType().equals(type);
    }

    boolean isPrivate();

}
