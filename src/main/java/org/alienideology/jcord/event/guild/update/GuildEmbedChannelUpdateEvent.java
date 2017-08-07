package org.alienideology.jcord.event.guild.update;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.guild.GuildUpdateEvent;
import org.alienideology.jcord.handle.channel.ITextChannel;
import org.alienideology.jcord.handle.guild.IGuild;

/**
 * @author AlienIdeology
 */
public class GuildEmbedChannelUpdateEvent extends GuildUpdateEvent {

    private final ITextChannel oldEmbedChannel;

    public GuildEmbedChannelUpdateEvent(Identity identity, int sequence, IGuild guild, ITextChannel oldEmbedChannel) {
        super(identity, sequence, guild);
        this.oldEmbedChannel = oldEmbedChannel;
    }

    public ITextChannel getOldEmbedChannel() {
        return oldEmbedChannel;
    }

}
