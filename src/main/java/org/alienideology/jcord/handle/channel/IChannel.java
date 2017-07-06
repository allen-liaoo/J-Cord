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

    /**
     * Get the channel type of this channel.
     *
     * @see Type
     * @return The channel type.
     */
    IChannel.Type getType();

    /**
     * Check if the channel is a type.
     *
     * @param type The type to be check with.
     * @return True it this channel is the type specified.
     */
    default boolean isType(IChannel.Type type) {
        return getType().equals(type);
    }

    /**
     * Check if this channel is a private channel.
     *
     * @return True if the channel is private channel.
     */
    boolean isPrivate();

    /**
     * Channel Types
     */
    enum Type {

        TEXT,
        VOICE,
        PRIVATE,
        GROUP,
        UNKNOWN;

        public static Type getByKey (String key) {
            for (Type type : values()) {
                if (type.name().toLowerCase().equals(key)) {
                    return type;
                }
            }
            return UNKNOWN;
        }
    }
}
