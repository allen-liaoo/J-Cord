package org.alienideology.jcord.event.guild.emoji;

import org.alienideology.jcord.handle.guild.IRole;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.guild.GuildEmoji;

import java.util.List;

/**
 * @author AlienIdeology
 */
public class GuildEmojiRolesUpdateEvent extends GuildEmojiUpdateEvent {

    public GuildEmojiRolesUpdateEvent(IdentityImpl identity, Guild guild, int sequence, GuildEmoji emoji, GuildEmoji oldEmoji) {
        super(identity, guild, sequence, emoji, oldEmoji);
    }

    public List<IRole> getNewUsableRoles() {
        return emoji.getUsableRoles();
    }

    public List<IRole> getOldUsableRoles() {
        return oldEmoji.getUsableRoles();
    }

}
