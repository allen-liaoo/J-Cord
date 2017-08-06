package org.alienideology.jcord.event.channel.guild.voice.update;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.channel.guild.voice.VoiceChannelUpdateEvent;
import org.alienideology.jcord.handle.channel.IChannel;
import org.alienideology.jcord.handle.permission.PermOverwrite;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author AlienIdeology
 */
public class VoiceChannelPermissionsUpdateEvent extends VoiceChannelUpdateEvent {

    private final Collection<PermOverwrite> changed;
    private final Collection<PermOverwrite> oldPerms;

    public VoiceChannelPermissionsUpdateEvent(Identity identity, int sequence, IChannel channel, Collection<PermOverwrite> changed, Collection<PermOverwrite> oldPerms) {
        super(identity, sequence, channel);
        this.changed = changed;
        this.oldPerms = oldPerms;
    }

    public Collection<PermOverwrite> getNewPermOverwrites() {
        return channel.getPermOverwrites();
    }

    public Collection<PermOverwrite> getOlgPermOverwrites() {
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
