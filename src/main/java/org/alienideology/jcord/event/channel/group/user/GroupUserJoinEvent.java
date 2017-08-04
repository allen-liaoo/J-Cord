package org.alienideology.jcord.event.channel.group.user;

import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.Channel;

/**
 * @author AlienIdeology
 */
public class GroupUserJoinEvent extends GroupUserEvent {

    public GroupUserJoinEvent(IdentityImpl identity, int sequence, Channel channel, IUser user) {
        super(identity, sequence, channel, user);
    }

}
