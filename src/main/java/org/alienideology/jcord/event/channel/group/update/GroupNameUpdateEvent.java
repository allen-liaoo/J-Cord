package org.alienideology.jcord.event.channel.group.update;

import org.alienideology.jcord.event.channel.group.GroupUpdateEvent;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.Channel;

/**
 * @author AlienIdeology
 */
public class GroupNameUpdateEvent extends GroupUpdateEvent {

    private final String oldName;

    public GroupNameUpdateEvent(IdentityImpl identity, int sequence, Channel channel, String oldName) {
        super(identity, sequence, channel);
        this.oldName = oldName;
    }

    public String getOldName() {
        return oldName;
    }

    public String getNewName() {
        return getGroup().getName();
    }

}
