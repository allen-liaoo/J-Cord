package org.alienideology.jcord.internal.event.message.dm;

import com.sun.istack.internal.NotNull;
import org.alienideology.jcord.internal.object.channel.PrivateChannel;

/**
 * @author AlienIdeology
 */
public interface IPrivateMessageEvent {

    @NotNull
    PrivateChannel getPrivateChannel();

}
