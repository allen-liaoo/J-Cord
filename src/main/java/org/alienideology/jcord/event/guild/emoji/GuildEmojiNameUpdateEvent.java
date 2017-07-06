package org.alienideology.jcord.event.guild.emoji;

import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.guild.GuildEmoji;

/**
 * @author AlienIdeology
 */
public class GuildEmojiNameUpdateEvent extends GuildEmojiUpdateEvent {

    public GuildEmojiNameUpdateEvent(IdentityImpl identity, Guild guild, int sequence, GuildEmoji emoji, GuildEmoji oldEmoji) {
        super(identity, guild, sequence, emoji, oldEmoji);
    }

    public String getNewName() {
        return emoji.getName();
    }

    public String getOldName() {
        return oldEmoji.getName();
    }

}
