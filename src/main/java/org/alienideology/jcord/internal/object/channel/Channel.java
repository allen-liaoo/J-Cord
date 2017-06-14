package org.alienideology.jcord.internal.object.channel;

import org.alienideology.jcord.handle.channel.IChannel;
import org.alienideology.jcord.internal.Identity;
import org.alienideology.jcord.internal.object.DiscordObject;
import org.alienideology.jcord.handle.ISnowFlake;

import java.util.Objects;

/**
 * Channel - A communication pipeline
 * Can be IGuildChannel, VoiceChannel or PrivateChannel
 * @author AlienIdeology
 */
public class Channel extends DiscordObject implements IChannel {

    protected final String id;
    protected final Type type;
    protected final boolean isPrivate;

    /**
     * Channel Constructor
     * @param id The id of this channel
     * @param type The type of channel
     */
    public Channel (Identity identity, String id, Type type) {
        super(identity);
        this.id = id;
        this.type = type;
        this.isPrivate = (type == Type.PRIVATE);
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public boolean isPrivate() {
        return isPrivate;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Channel) && Objects.equals(this.id, ((Channel) obj).getId());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + (isPrivate ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ID: "+id;
    }

    // TODO: Move Channel.Type to IChannel

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
