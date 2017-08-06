package org.alienideology.jcord.event.guild.emoji;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.guild.IGuildEmoji;
import org.alienideology.jcord.internal.object.guild.Guild;

import java.time.OffsetDateTime;

/**
 * @author AlienIdeology
 */
public class GuildEmojiDeleteEvent extends GuildEmojiEvent {

    private final OffsetDateTime timeStamp;

    public GuildEmojiDeleteEvent(Identity identity, Guild guild, int sequence, IGuildEmoji emoji, OffsetDateTime timeStamp) {
        super(identity, guild, sequence, emoji);
        this.timeStamp = timeStamp;
    }

    public OffsetDateTime getTimeStamp() {
        return timeStamp;
    }
}
