package org.alienideology.jcord.event.guild.emoji;

import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.guild.GuildEmoji;

/**
 * @author AlienIdeology
 */
public class GuildEmojiUpdateEvent extends GuildEmojiEvent {

    protected GuildEmoji oldEmoji;

    public GuildEmojiUpdateEvent(IdentityImpl identity, Guild guild, int sequence, GuildEmoji emoji, GuildEmoji oldEmoji) {
        super(identity, guild, sequence, emoji);
        this.oldEmoji = oldEmoji;
    }

    public GuildEmoji getOldEmoji() {
        return oldEmoji;
    }
}
