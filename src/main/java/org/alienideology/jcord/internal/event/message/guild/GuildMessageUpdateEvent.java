package org.alienideology.jcord.internal.event.message.guild;

import com.sun.istack.internal.NotNull;
import org.alienideology.jcord.internal.Identity;
import org.alienideology.jcord.internal.event.message.MessageUpdateEvent;
import org.alienideology.jcord.internal.object.Guild;
import org.alienideology.jcord.internal.object.Message;
import org.alienideology.jcord.internal.object.channel.MessageChannel;
import org.alienideology.jcord.internal.object.channel.TextChannel;

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
