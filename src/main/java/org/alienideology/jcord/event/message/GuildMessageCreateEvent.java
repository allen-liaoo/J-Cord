package org.alienideology.jcord.event.message;

import com.sun.istack.internal.NotNull;
import org.alienideology.jcord.Identity;
import org.alienideology.jcord.object.channel.MessageChannel;
import org.alienideology.jcord.object.channel.TextChannel;
import org.alienideology.jcord.object.Guild;
import org.alienideology.jcord.object.Message;

/**
 * @author AlienIdeology
 */
public class GuildMessageCreateEvent extends MessageCreateEvent {

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
