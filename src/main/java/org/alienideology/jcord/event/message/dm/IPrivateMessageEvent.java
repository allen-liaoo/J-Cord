package org.alienideology.jcord.event.message.dm;

import com.sun.istack.internal.NotNull;
import org.alienideology.jcord.handle.channel.IPrivateChannel;
import org.alienideology.jcord.internal.object.channel.PrivateChannel;

/**
 * @author AlienIdeology
 */
public interface IPrivateMessageEvent {

    @NotNull
    IPrivateChannel getPrivateChannel();

}
