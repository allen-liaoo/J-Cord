package org.alienideology.jcord.event.channel.guild.text.update;

import org.alienideology.jcord.event.channel.guild.text.TextChannelUpdateEvent;
import org.alienideology.jcord.handle.permission.PermOverwrite;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.Channel;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author AlienIdeology
 */
public class TextChannelPermissionsUpdateEvent extends TextChannelUpdateEvent {

    private final Collection<PermOverwrite> changed;
    private final Collection<PermOverwrite> oldPerms;

    public TextChannelPermissionsUpdateEvent(IdentityImpl identity, int sequence, Channel channel, Collection<PermOverwrite> changed, Collection<PermOverwrite> oldPerms) {
        super(identity, sequence, channel);
        this.changed = changed;
        this.oldPerms = oldPerms;
    }

    public Collection<PermOverwrite> getNewPermOverwrites() {
        return channel.getPermOverwrites();
    }

    public Collection<PermOverwrite> getOldPermOverwrites() {
        return oldPerms;
    }

    public Collection<PermOverwrite> getChangedPermOverwrites() {
        return changed;
    }

    public Collection<PermOverwrite> getChangedRolePermOverwrites() {
        return changed.stream().filter(PermOverwrite::isRoleOverwrite).collect(Collectors.toList());
    }

    public Collection<PermOverwrite> getChangedMemberPermOverwrites() {
        return changed.stream().filter(po -> !po.isRoleOverwrite()).collect(Collectors.toList());
    }

}
