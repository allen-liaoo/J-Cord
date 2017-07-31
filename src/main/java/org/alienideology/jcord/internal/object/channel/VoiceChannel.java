package org.alienideology.jcord.internal.object.channel;

import org.alienideology.jcord.handle.channel.IChannel;
import org.alienideology.jcord.handle.channel.IVoiceChannel;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IMember;
import org.alienideology.jcord.handle.guild.IRole;
import org.alienideology.jcord.handle.managers.IChannelManager;
import org.alienideology.jcord.handle.managers.IInviteManager;
import org.alienideology.jcord.handle.permission.PermOverwrite;
import org.alienideology.jcord.handle.permission.Permission;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.Jsonable;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.managers.ChannelManager;
import org.alienideology.jcord.internal.object.managers.InviteManager;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @author AlienIdeology
 */
public final class VoiceChannel extends Channel implements IVoiceChannel, Jsonable {

    private Guild guild;
    private ChannelManager channelManager;
    private InviteManager inviteManager;

    private String name;
    private int position;
    private int bitrate;
    private int user_limit;

    private List<PermOverwrite> permOverwrites = new ArrayList<>();

    public VoiceChannel(IdentityImpl identity, String guild_id, String id, String name, int position, int bitrate, int user_limit) {
        super(identity, id, IChannel.Type.GUILD_VOICE);
        this.guild = guild_id == null ? null : (Guild) identity.getGuild(guild_id);
        this.name = name;
        this.position = position;
        this.bitrate = bitrate;
        this.user_limit = user_limit;
        this.channelManager = new ChannelManager(this);
        this.inviteManager = new InviteManager(this);
    }

    @Override
    public JSONObject toJson() {
        return new JSONObject()
                .put("name", name == null ? "" : name)
                .put("type", "voice")
                .put("bitrate", bitrate)
                .put("user_limit", user_limit)
                .put("permission_overwrites", permOverwrites);
    }

    public VoiceChannel copy() {
        return new VoiceChannel(identity, guild.getId(), id, name, position, bitrate, user_limit);
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
    public int getBitrate() {
        return bitrate;
    }

    @Override
    public int getUserLimit() {
        return user_limit;
    }

    @Override
    public IGuild getGuild() {
        return guild;
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

    // For OverwriteCheckable, the methods are essentially the same as TextChannel.

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
    public boolean equals(Object obj) {
        return (obj instanceof VoiceChannel) && Objects.equals(this.id, ((VoiceChannel) obj).getId());
    }

    @Override
    public String toString() {
        return "VoiceChannel{" +
                "key='" + id + '\'' +
                ", type=" + type +
                ", guild=" + guild +
                ", name='" + name + '\'' +
                '}';
    }

    public VoiceChannel setPermOverwrites(List<PermOverwrite> permOverwrites) {
        this.permOverwrites = permOverwrites;
        return this;
    }

}
