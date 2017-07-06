package org.alienideology.jcord.event.user;

import org.alienideology.jcord.event.Event;
import org.alienideology.jcord.handle.channel.ITextChannel;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.internal.object.IdentityImpl;

/**
 * @author AlienIdeology
 */
public class WebhookUpdateEvent extends Event {

    private IGuild guild;
    private ITextChannel channel;

    public WebhookUpdateEvent(IdentityImpl identity, int sequence, IGuild guild, ITextChannel channel) {
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
