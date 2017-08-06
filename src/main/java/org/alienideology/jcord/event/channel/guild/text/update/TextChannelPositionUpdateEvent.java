package org.alienideology.jcord.event.channel.guild.text.update;

import org.alienideology.jcord.event.channel.guild.text.TextChannelUpdateEvent;
import org.alienideology.jcord.handle.channel.IChannel;
import org.alienideology.jcord.internal.object.IdentityImpl;

/**
 * @author AlienIdeology
 */
public class TextChannelPositionUpdateEvent extends TextChannelUpdateEvent {

    private final int oldPosition;

    public TextChannelPositionUpdateEvent(IdentityImpl identity, int sequence, IChannel channel, int oldPosition) {
        super(identity, sequence, channel);
        this.oldPosition = oldPosition;
    }

    public int getNewPosition() {
        return channel.getPosition();
    }

    public int getOldPosition() {
        return oldPosition;
    }

}
