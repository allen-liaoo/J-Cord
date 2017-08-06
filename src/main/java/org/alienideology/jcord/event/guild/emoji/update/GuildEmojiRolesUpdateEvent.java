package org.alienideology.jcord.event.guild.emoji.update;

import org.alienideology.jcord.event.guild.emoji.GuildEmojiUpdateEvent;
import org.alienideology.jcord.handle.guild.IRole;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.guild.GuildEmoji;

import java.util.List;

/**
 * @author AlienIdeology
 */
public class GuildEmojiRolesUpdateEvent extends GuildEmojiUpdateEvent {

    private final List<IRole> oldRoles;

    public GuildEmojiRolesUpdateEvent(IdentityImpl identity, Guild guild, int sequence, GuildEmoji emoji, List<IRole> oldRoles) {
        super(identity, guild, sequence, emoji);
        this.oldRoles = oldRoles;
    }

    public List<IRole> getNewUsableRoles() {
        return emoji.getUsableRoles();
    }

    public List<IRole> getOldUsableRoles() {
        return oldRoles;
    }

}
