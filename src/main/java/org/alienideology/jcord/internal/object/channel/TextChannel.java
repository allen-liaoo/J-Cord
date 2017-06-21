package org.alienideology.jcord.internal.object.channel;

import org.alienideology.jcord.handle.channel.IChannel;
import org.alienideology.jcord.handle.channel.ITextChannel;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IMember;
import org.alienideology.jcord.handle.guild.IRole;
import org.alienideology.jcord.handle.managers.IChannelManager;
import org.alienideology.jcord.handle.managers.IInviteManager;
import org.alienideology.jcord.handle.permission.PermOverwrite;
import org.alienideology.jcord.handle.permission.Permission;
import org.alienideology.jcord.internal.object.Buildable;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.managers.ChannelManager;
import org.alienideology.jcord.internal.object.managers.InviteManager;
import org.alienideology.jcord.internal.object.message.Message;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @author AlienIdeology
 */
public final class TextChannel extends MessageChannel implements ITextChannel, Buildable {

    private Guild guild;
    private ChannelManager channelManager;
    private InviteManager inviteManager;

    private String name;
    private int position;
    private String topic;

    private List<PermOverwrite> permOverwrites = new ArrayList<>();

    public TextChannel(IdentityImpl identity, String guild_id, String id, String name, int position, String topic, Message lastMessagt) {
        super(identity, id, IChannel.Type.TEXT, lastMessagt);
        this.guild = guild_id == null ? null : (Guild) identity.getGuild(guild_id);
        this.name = name;
        this.position = position;
        this.topic = topic;
        this.channelManager = new ChannelManager(this);
        this.inviteManager = new InviteManager(this);
    }

    @Override
    public JSONObject toJson() {
        return new JSONObject()
                .put("name", name == null? "" : name)
                .put("type", "text");
    }

    @Override
    public IGuild getGuild() {
        return guild;
    }

    @Override
    public IChannelManager getChannelManager() {
        return channelManager;
    }

    @Override
    public IInviteManager getInviteManager() {
        return inviteManager;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public Collection<PermOverwrite> getPermOverwrites() {
        return permOverwrites;
    }

    @Override
    public boolean hasAllPermission(IMember member, Collection<Permission> permissions) {
        PermOverwrite overwrites = getMemberPermOverwrite(member.getId());
        for (Permission permission : permissions) {
            if (overwrites.getDeniedPermissions().contains(permission))
                return false;
            if (!member.getPermissions().contains(permission))
                return false;
        }
        return true;
    }

    @Override
    public boolean hasAllPermission(IRole role, Collection<Permission> permissions) {
        PermOverwrite overwrites = getRolePermOverwrite(role.getId());
        for (Permission permission : permissions) {
            if (overwrites.getDeniedPermissions().contains(permission))
                return false;
            if (!role.getPermissions().contains(permission))
                return false;
        }
        return true;
    }

    @Override
    public boolean hasPermission(IMember member, Collection<Permission> permissions) {
        PermOverwrite overwrites = getMemberPermOverwrite(member.getId());

        // If the member does not have overwrite, check the highest role
        if (overwrites == null) {
            overwrites = getRolePermOverwrite(member.getHighestRole().getId());
        }

        // If the member or highest role has overwrite
        if (overwrites != null) {
            for (Permission permission : permissions) {
                // If the overwrite allowed one permission
                if (overwrites.getAllowedPermissions().contains(permission))
                    return true;
                // If the overwrite did not denied, and the member has permission
                if (!overwrites.getDeniedPermissions().contains(permission) && member.getPermissions().contains(permission))
                    return true;
            }

        // If not, check permission
        } else {
            if (member.hasPermissions(true, permissions))
                return true;
        }
        return false;
    }

    @Override
    public boolean hasPermission(IRole role, Collection<Permission> permissions) {
        PermOverwrite overwrites = getRolePermOverwrite(role.getId());

        // If the member or highest role has overwrite
        if (overwrites != null) {
            for (Permission permission : permissions) {
                // If the overwrite allowed one permission
                if (overwrites.getAllowedPermissions().contains(permission))
                    return true;
                // If the overwrite did not denied, and the member has permission
                if (!overwrites.getDeniedPermissions().contains(permission) && role.getPermissions().contains(permission))
                    return true;
            }

        // If not, check permission
        } else {
            if (role.hasPermissions(true, permissions))
                return true;
        }
        return false;
    }

    @Override
    public String getTopic() {
        return topic;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof TextChannel) && Objects.equals(this.id, ((TextChannel) obj).getId());
    }

    @Override
    public String toString() {
        return "TextChannel{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", guild=" + guild +
                ", name='" + name + '\'' +
                '}';
    }

    public TextChannel setPermOverwrites(List<PermOverwrite> permOverwrites) {
        this.permOverwrites = permOverwrites;
        return this;
    }

}
