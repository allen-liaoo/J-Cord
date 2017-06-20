package org.alienideology.jcord.event.guild.emoji;

import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.guild.GuildEmoji;

import java.time.OffsetDateTime;

/**
 * @author AlienIdeology
 */
public class GuildEmojiDeleteEvent extends GuildEmojiEvent {

    private OffsetDateTime timeStamp;

    public GuildEmojiDeleteEvent(IdentityImpl identity, Guild guild, int sequence, GuildEmoji emoji, OffsetDateTime timeStamp) {
        super(identity, guild, sequence, emoji);
        this.timeStamp = timeStamp;
    }

    public OffsetDateTime getTimeStamp() {
        return timeStamp;
    }
}
