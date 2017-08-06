package org.alienideology.jcord.event.guild.emoji;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IGuildEmoji;

/**
 * @author AlienIdeology
 */
public class GuildEmojiUploadEvent extends GuildEmojiEvent {

    public GuildEmojiUploadEvent(Identity identity, IGuild guild, int sequence, IGuildEmoji emoji) {
        super(identity, guild, sequence, emoji);
    }

}
