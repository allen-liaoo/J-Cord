package org.alienideology.jcord.event.channel.guild.voice.update;

import org.alienideology.jcord.event.channel.guild.voice.VoiceChannelUpdateEvent;
import org.alienideology.jcord.handle.channel.IGuildChannel;
import org.alienideology.jcord.handle.permission.PermOverwrite;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.Channel;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author AlienIdeology
 */
public class VoiceChannelPermissionsUpdateEvent extends VoiceChannelUpdateEvent {

    private Collection<PermOverwrite> changed;

    public VoiceChannelPermissionsUpdateEvent(IdentityImpl identity, int sequence, Channel channel, IGuildChannel oldChannel, Collection<PermOverwrite> changed) {
        super(identity, sequence, channel, oldChannel);
        this.changed = changed;
    }

    public Collection<PermOverwrite> getNewPermOverwrites() {
        return channel.getPermOverwrites();
    }

    public Collection<PermOverwrite> getOlgPermOverwrites() {
        return oldChannel.getPermOverwrites();
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
