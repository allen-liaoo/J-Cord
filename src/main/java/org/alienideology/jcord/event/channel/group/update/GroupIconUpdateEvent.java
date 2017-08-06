package org.alienideology.jcord.event.channel.group.update;

import org.alienideology.jcord.event.channel.group.GroupUpdateEvent;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.Channel;
import org.alienideology.jcord.internal.rest.HttpPath;

/**
 * @author AlienIdeology
 */
public class GroupIconUpdateEvent extends GroupUpdateEvent {

    private final String oldIcon;

    public GroupIconUpdateEvent(IdentityImpl identity, int sequence, Channel channel, String icon) {
        super(identity, sequence, channel);
        this.oldIcon = icon;
    }

    public String getOldIconHash() {
        return oldIcon;
    }

    public String getOldIconUrl() {
        return String.format(HttpPath.EndPoint.GROUP_ICON, getChannel().getId(), getOldIconHash());
    }

    public String getNewIconHash() {
        return getGroup().getIconHash();
    }

    public String getNewIconurl() {
        return getGroup().getIconUrl();
    }

}
