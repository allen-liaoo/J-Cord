package org.alienideology.jcord.event.guild.update;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.guild.GuildUpdateEvent;
import org.alienideology.jcord.handle.guild.IGuild;

/**
 * @author AlienIdeology
 */
public class GuildContentFilterUpdateEvent extends GuildUpdateEvent {

    private final IGuild.ContentFilterLevel oldContentFilter;

    public GuildContentFilterUpdateEvent(Identity identity, int sequence, IGuild guild, IGuild.ContentFilterLevel oldContentFilter) {
        super(identity, sequence, guild);
        this.oldContentFilter = oldContentFilter;
    }

    public IGuild.ContentFilterLevel getNewContentFilter() {
        return getGuild().getContentFilterLevel();
    }

    public IGuild.ContentFilterLevel getOldContentFilter() {
        return oldContentFilter;
    }

}
