package org.alienideology.jcord.event.guild.emoji;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.guild.GuildEmoji;

/**
 * @author AlienIdeology
 */
public class GuildEmojiUpdateEvent extends GuildEmojiEvent {

    public GuildEmojiUpdateEvent(Identity identity, Guild guild, int sequence, GuildEmoji emoji) {
        super(identity, guild, sequence, emoji);
    }

}
