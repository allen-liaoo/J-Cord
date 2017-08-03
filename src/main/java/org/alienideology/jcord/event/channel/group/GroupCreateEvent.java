package org.alienideology.jcord.event.channel.group;

import org.alienideology.jcord.event.channel.ChannelCreateEvent;
import org.alienideology.jcord.handle.channel.IGroup;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.Channel;

/**
 * @author AlienIdeology
 */
public class GroupCreateEvent extends ChannelCreateEvent implements IGroupEvent {

    public GroupCreateEvent(IdentityImpl identity, int sequence, Channel channel) {
        super(identity, sequence, channel);
    }

    @Override
    public IGroup getGroup() {
        return (IGroup) getChannel();
    }

}
