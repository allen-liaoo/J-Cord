package org.alienideology.jcord.event.guild.role.update;

import org.alienideology.jcord.event.guild.role.GuildRoleUpdateEvent;
import org.alienideology.jcord.handle.permission.Permission;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.guild.Role;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author AlienIdeology
 */
public class GuildRolePermissionsUpdateEvent extends GuildRoleUpdateEvent {

    private List<Permission> allowed;
    private List<Permission> denied;

    public GuildRolePermissionsUpdateEvent(IdentityImpl identity, Guild guild, int sequence, Role role, List<Permission> allowed, List<Permission> denied) {
        super(identity, guild, sequence, role);
        this.allowed = allowed;
        this.denied = denied;
    }

    public Collection<Permission> getNewPermissions() {
        return role.getPermissions();
    }

    public Collection<Permission> getOldPermissions() {
        List<Permission> permissions = new ArrayList<>(role.getPermissions());
        permissions.removeAll(allowed);
        permissions.addAll(denied);
        return permissions;
    }

    public Collection<Permission> getAllowedPermissions() {
        return allowed;
    }

    public Collection<Permission> getDeniedPermissions() {
        return denied;
    }

}
