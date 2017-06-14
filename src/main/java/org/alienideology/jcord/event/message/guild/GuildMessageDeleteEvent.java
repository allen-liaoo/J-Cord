package org.alienideology.jcord.event.message.guild;

import com.sun.istack.internal.NotNull;
import org.alienideology.jcord.handle.channel.ITextChannel;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.event.message.MessageDeleteEvent;
import org.alienideology.jcord.internal.object.channel.MessageChannel;

import java.time.OffsetDateTime;

/**
 * @author AlienIdeology
 */
public class GuildMessageDeleteEvent extends MessageDeleteEvent implements IGuildMessageEvent {

    public GuildMessageDeleteEvent(IdentityImpl identity, int sequence, MessageChannel channel, String id, OffsetDateTime deleteTimStamp) {
        super(identity, sequence, channel, id, deleteTimStamp);
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
