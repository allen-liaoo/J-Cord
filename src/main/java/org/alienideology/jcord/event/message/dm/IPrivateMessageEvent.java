package org.alienideology.jcord.event.message.dm;

import com.sun.istack.internal.NotNull;
import org.alienideology.jcord.object.channel.PrivateChannel;

/**
 * @author AlienIdeology
 */
public interface IPrivateMessageEvent {

    @NotNull
    PrivateChannel getPrivateChannel();

}
