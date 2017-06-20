package org.alienideology.jcord.event.guild.emoji;

import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.guild.GuildEmoji;

/**
 * @author AlienIdeology
 */
public class GuildEmojiUploadEvent extends GuildEmojiEvent {

    public GuildEmojiUploadEvent(IdentityImpl identity, Guild guild, int sequence, GuildEmoji emoji) {
        super(identity, guild, sequence, emoji);
    }

}
