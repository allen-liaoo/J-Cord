package org.alienideology.jcord.object.channel;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.object.DiscordObject;
import org.alienideology.jcord.object.Mention;
import org.alienideology.jcord.object.SnowFlake;

/**
 * Channel - A communication pipeline
 * Can be GuildChannel, VoiceChannel or PrivateChannel
 * @author AlienIdeology
 */
public class Channel extends DiscordObject implements SnowFlake {

    protected final String id;
    protected final boolean isPrivate;

    /**
     * Channel Constructor
     * @param id The id of this channel
     * @param isPrivate Always false for Guild channels
     */
    public Channel (Identity identity, String id, boolean isPrivate) {
        super(identity);
        this.id = id;
        this.isPrivate = isPrivate;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    @Override
    public String getId() {
        return id;
    }

    /**
     * Channel Types
     */
    public enum Type {

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
