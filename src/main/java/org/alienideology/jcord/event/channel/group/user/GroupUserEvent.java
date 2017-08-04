package org.alienideology.jcord.event.channel.group.user;

import org.alienideology.jcord.event.channel.ChannelEvent;
import org.alienideology.jcord.event.channel.group.IGroupEvent;
import org.alienideology.jcord.handle.channel.IGroup;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.Channel;

/**
 * @author AlienIdeology
 */
public class GroupUserEvent extends ChannelEvent implements IGroupEvent {

    private final IUser user;

    public GroupUserEvent(IdentityImpl identity, int sequence, Channel channel, IUser user) {
        super(identity, sequence, channel);
        this.user = user;
    }

    @Override
    public IGroup getGroup() {
        return (IGroup) getChannel();
    }

    public IUser getUser() {
        return user;
    }
}
