package org.alienideology.jcord.event.message.guild;

import com.sun.istack.internal.NotNull;
import org.alienideology.jcord.handle.channel.ITextChannel;
import org.alienideology.jcord.handle.guild.IGuild;

/**
 * @author AlienIdeology
 */
public interface IGuildMessageEvent {

    @NotNull
    IGuild getGuild();

    @NotNull
    ITextChannel getTextChannel();

}
