package org.alienideology.jcord.event.channel.group;

import org.alienideology.jcord.event.channel.ChannelDeleteEvent;
import org.alienideology.jcord.handle.channel.IGroup;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.Channel;

import java.time.OffsetDateTime;

/**
 * @author AlienIdeology
 */
public class GroupDeleteEvent extends ChannelDeleteEvent implements IGroupEvent {

    public GroupDeleteEvent(IdentityImpl identity, int sequence, Channel channel, OffsetDateTime timeStamp) {
        super(identity, sequence, channel, timeStamp);
    }

    @Override
    public IGroup getGroup() {
        return (IGroup) getChannel();
    }

}
