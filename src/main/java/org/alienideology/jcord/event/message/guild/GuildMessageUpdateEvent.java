package org.alienideology.jcord.event.message.guild;

import com.sun.istack.internal.NotNull;
import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.message.MessageUpdateEvent;
import org.alienideology.jcord.object.Guild;
import org.alienideology.jcord.object.Message;
import org.alienideology.jcord.object.channel.MessageChannel;
import org.alienideology.jcord.object.channel.TextChannel;

/**
 * @author AlienIdeology
 */
public class GuildMessageUpdateEvent extends MessageUpdateEvent implements IGuildMessageEvent {

    public GuildMessageUpdateEvent(Identity identity, int sequence, MessageChannel channel, Message message) {
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
