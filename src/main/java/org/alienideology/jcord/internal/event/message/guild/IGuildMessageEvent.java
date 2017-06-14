package org.alienideology.jcord.internal.event.message.guild;

import com.sun.istack.internal.NotNull;
import org.alienideology.jcord.internal.object.Guild;
import org.alienideology.jcord.internal.object.channel.TextChannel;

/**
 * @author AlienIdeology
 */
public interface IGuildMessageEvent {

    @NotNull
    Guild getGuild();

    @NotNull
    TextChannel getTextChannel();

}
