package org.alienideology.jcord.event.message.guild;

import com.sun.istack.internal.NotNull;
import org.alienideology.jcord.handle.channel.ITextChannel;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.event.message.MessageCreateEvent;
import org.alienideology.jcord.internal.object.channel.MessageChannel;
import org.alienideology.jcord.internal.object.message.Message;

/**
 * @author AlienIdeology
 */
public class GuildMessageCreateEvent extends MessageCreateEvent implements IGuildMessageEvent {

    public GuildMessageCreateEvent(IdentityImpl identity, int sequence, MessageChannel channel, Message message) {
        super(identity, sequence, channel, message);
    }

    @Override
    @NotNull
    public IGuild getGuild() {
        return super.getGuild();
    }

    @Override
    @NotNull
    public ITextChannel getTextChannel() {
        return super.getTextChannel();
    }
}
