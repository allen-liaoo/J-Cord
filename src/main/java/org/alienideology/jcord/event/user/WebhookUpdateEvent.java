package org.alienideology.jcord.event.user;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.Event;
import org.alienideology.jcord.handle.channel.ITextChannel;
import org.alienideology.jcord.handle.guild.IGuild;

/**
 * @author AlienIdeology
 */
public class WebhookUpdateEvent extends Event {

    private final IGuild guild;
    private final ITextChannel channel;

    public WebhookUpdateEvent(Identity identity, int sequence, IGuild guild, ITextChannel channel) {
        super(identity, sequence);
        this.guild = guild;
        this.channel = channel;
    }

    public IGuild getGuild() {
        return guild;
    }

    public ITextChannel getChannel() {
        return channel;
    }

}
