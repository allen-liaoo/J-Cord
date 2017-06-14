package org.alienideology.jcord.event.message.guild;

import com.sun.istack.internal.NotNull;
import org.alienideology.jcord.handle.channel.ITextChannel;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.internal.object.Guild;
import org.alienideology.jcord.internal.object.channel.TextChannel;

/**
 * @author AlienIdeology
 */
public interface IGuildMessageEvent {

    @NotNull
    IGuild getGuild();

    @NotNull
    ITextChannel getTextChannel();

}
