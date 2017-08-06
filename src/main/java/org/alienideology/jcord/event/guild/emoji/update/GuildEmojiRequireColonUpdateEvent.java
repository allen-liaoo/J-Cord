package org.alienideology.jcord.event.guild.emoji.update;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.guild.emoji.GuildEmojiUpdateEvent;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.guild.GuildEmoji;

/**
 * @author AlienIdeology
 */
public class GuildEmojiRequireColonUpdateEvent extends GuildEmojiUpdateEvent {

    public GuildEmojiRequireColonUpdateEvent(Identity identity, Guild guild, int sequence, GuildEmoji emoji) {
        super(identity, guild, sequence, emoji);
    }

    public boolean isRequireColon() {
        return emoji.isRequireColon();
    }

}
