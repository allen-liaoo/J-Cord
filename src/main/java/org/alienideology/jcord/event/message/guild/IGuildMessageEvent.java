package org.alienideology.jcord.event.message.guild;

import com.sun.istack.internal.NotNull;
import org.alienideology.jcord.object.Guild;
import org.alienideology.jcord.object.channel.TextChannel;

/**
 * @author AlienIdeology
 */
public interface IGuildMessageEvent {

    @NotNull
    Guild getGuild();

    @NotNull
    TextChannel getTextChannel();

}
