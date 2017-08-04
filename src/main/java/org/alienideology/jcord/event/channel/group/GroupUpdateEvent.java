package org.alienideology.jcord.event.channel.group;

import org.alienideology.jcord.event.channel.ChannelEvent;
import org.alienideology.jcord.handle.channel.IGroup;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.Channel;

/**
 * @author AlienIdeology
 */
public class GroupUpdateEvent extends ChannelEvent implements IGroupEvent {

    public GroupUpdateEvent(IdentityImpl identity, int sequence, Channel channel) {
        super(identity, sequence, channel);
    }

    @Override
    public IGroup getGroup() {
        return (IGroup) getChannel();
    }
}
