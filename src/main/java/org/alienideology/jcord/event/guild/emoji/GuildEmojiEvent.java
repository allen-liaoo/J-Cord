package org.alienideology.jcord.event.guild.emoji;

import org.alienideology.jcord.event.guild.GuildEvent;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.guild.GuildEmoji;

/**
 * @author AlienIdeology
 */
public class GuildEmojiEvent extends GuildEvent {

    protected GuildEmoji emoji;

    public GuildEmojiEvent(IdentityImpl identity, Guild guild, int sequence, GuildEmoji emoji) {
        super(identity, sequence, guild);
        this.emoji = emoji;
    }

    public GuildEmoji getGuildEmoji() {
        return emoji;
    }

}
