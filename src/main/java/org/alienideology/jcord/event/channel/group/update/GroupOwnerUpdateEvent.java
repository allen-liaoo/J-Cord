package org.alienideology.jcord.event.channel.group.update;

import org.alienideology.jcord.event.channel.group.GroupUpdateEvent;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.Channel;

/**
 * @author AlienIdeology
 */
public class GroupOwnerUpdateEvent extends GroupUpdateEvent {

    private final IUser oldOwner;

    public GroupOwnerUpdateEvent(IdentityImpl identity, int sequence, Channel channel, IUser oldOwner) {
        super(identity, sequence, channel);
        this.oldOwner = oldOwner;
    }

    public IUser getOldOwner() {
        return oldOwner;
    }

    public IUser getNewOwner() {
        return getGroup().getOwner();
    }

}
