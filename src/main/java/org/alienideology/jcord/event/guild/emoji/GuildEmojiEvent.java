package org.alienideology.jcord.event.guild.emoji;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.guild.GuildEvent;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IGuildEmoji;

/**
 * @author AlienIdeology
 */
public class GuildEmojiEvent extends GuildEvent {

    protected final IGuildEmoji emoji;

    public GuildEmojiEvent(Identity identity, IGuild guild, int sequence, IGuildEmoji emoji) {
        super(identity, sequence, guild);
        this.emoji = emoji;
    }

    public IGuildEmoji getGuildEmoji() {
        return emoji;
    }

}
