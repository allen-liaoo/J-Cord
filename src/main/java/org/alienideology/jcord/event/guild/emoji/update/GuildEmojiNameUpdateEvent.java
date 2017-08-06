package org.alienideology.jcord.event.guild.emoji.update;

import org.alienideology.jcord.event.guild.emoji.GuildEmojiUpdateEvent;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.guild.GuildEmoji;

/**
 * @author AlienIdeology
 */
public class GuildEmojiNameUpdateEvent extends GuildEmojiUpdateEvent {

    private final String oldName;

    public GuildEmojiNameUpdateEvent(IdentityImpl identity, Guild guild, int sequence, GuildEmoji emoji, String oldName) {
        super(identity, guild, sequence, emoji);
        this.oldName = oldName;
    }

    public String getNewName() {
        return emoji.getName();
    }

    public String getOldName() {
        return oldName;
    }

}
