package org.alienideology.jcord.event.message.guild;

import com.sun.istack.internal.NotNull;
import org.alienideology.jcord.handle.channel.ITextChannel;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.event.message.MessageUpdateEvent;
import org.alienideology.jcord.internal.object.Message;
import org.alienideology.jcord.internal.object.channel.MessageChannel;

/**
 * @author AlienIdeology
 */
public class GuildMessageUpdateEvent extends MessageUpdateEvent implements IGuildMessageEvent {

    public GuildMessageUpdateEvent(IdentityImpl identity, int sequence, MessageChannel channel, Message message) {
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
