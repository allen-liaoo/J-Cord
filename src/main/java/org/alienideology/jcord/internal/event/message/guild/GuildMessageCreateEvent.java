package org.alienideology.jcord.internal.event.message.guild;

import com.sun.istack.internal.NotNull;
import org.alienideology.jcord.internal.Identity;
import org.alienideology.jcord.internal.event.message.MessageCreateEvent;
import org.alienideology.jcord.internal.object.channel.MessageChannel;
import org.alienideology.jcord.internal.object.channel.TextChannel;
import org.alienideology.jcord.internal.object.Guild;
import org.alienideology.jcord.internal.object.Message;

/**
 * @author AlienIdeology
 */
public class GuildMessageCreateEvent extends MessageCreateEvent implements IGuildMessageEvent {

    public GuildMessageCreateEvent(Identity identity, int sequence, MessageChannel channel, Message message) {
        super(identity, sequence, channel, message);
    }

    @Override
    @NotNull
    public Guild getGuild() {
        return super.getGuild();
    }

    @Override
    @NotNull
    public TextChannel getTextChannel() {
        return super.getTextChannel();
    }
}
