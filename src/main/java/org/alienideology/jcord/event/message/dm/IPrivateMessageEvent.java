package org.alienideology.jcord.event.message.dm;

import org.jetbrains.annotations.NotNull;
import org.alienideology.jcord.handle.channel.IPrivateChannel;
import org.alienideology.jcord.internal.object.channel.PrivateChannel;

/**
 * @author AlienIdeology
 */
public interface IPrivateMessageEvent {

    @NotNull
    IPrivateChannel getPrivateChannel();

}
