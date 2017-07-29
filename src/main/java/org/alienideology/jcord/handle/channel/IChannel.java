package org.alienideology.jcord.handle.channel;

import org.alienideology.jcord.handle.IDiscordObject;
import org.alienideology.jcord.handle.ISnowFlake;

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

        GUILD_TEXT (0),
        DM (1),
        GUILD_VOICE (2),
        GROUP_DM (3),
        GUILD_CATEGORY (4),
        UNKNOWN (-1);

        public int key;

        Type (int key) {
            this.key = key;
        }

        public static Type getByKey (int key) {
            for (Type type : values()) {
                if (type.key == key) {
                    return type;
                }
            }
            return UNKNOWN;
        }

        public boolean isPrivate() {
            return this == DM || this == GROUP_DM;
        }

        public boolean isGuildChannel() {
            return this == GUILD_TEXT || this == GUILD_VOICE || this == GUILD_CATEGORY;
        }

    }
}
