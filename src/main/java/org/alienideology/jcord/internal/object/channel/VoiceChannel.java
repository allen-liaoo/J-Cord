package org.alienideology.jcord.internal.object.channel;

import org.alienideology.jcord.handle.channel.IChannel;
import org.alienideology.jcord.handle.channel.IVoiceChannel;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IMember;
import org.alienideology.jcord.handle.guild.IRole;
import org.alienideology.jcord.handle.managers.IChannelManager;
import org.alienideology.jcord.handle.managers.IInviteManager;
import org.alienideology.jcord.handle.modifiers.IChannelModifier;
import org.alienideology.jcord.handle.permission.PermOverwrite;
import org.alienideology.jcord.handle.permission.Permission;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.Jsonable;
import org.alienideology.jcord.internal.object.managers.ChannelManager;
import org.alienideology.jcord.internal.object.managers.InviteManager;
import org.alienideology.jcord.internal.object.modifiers.ChannelModifier;
import org.json.JSONObject;

import java.util.Collection;
import java.util.Objects;

/**
 * @author AlienIdeology
 */
public final class VoiceChannel extends Channel implements IVoiceChannel, Jsonable {

    private IGuild guild;

    private String name;
    private int position;
    private int bitrate;
    private int userLimit;

    private Collection<PermOverwrite> permOverwrites;

    private final ChannelManager channelManager;
    private final ChannelModifier channelModifier;
    private final InviteManager inviteManager;


    public VoiceChannel(IdentityImpl identity, IGuild guild, String id) {
        super(identity, id, IChannel.Type.GUILD_VOICE);
        this.guild = guild;
        this.channelManager = new ChannelManager(this);
        this.channelModifier = new ChannelModifier(this);
        this.inviteManager = new InviteManager(this);
    }

    @Override
    public JSONObject toJson() {
        return new JSONObject()
                .put("name", name == null ? "" : name)
                .put("type", Type.GUILD_VOICE.key)
                .put("bitrate", bitrate)
                .put("user_limit", userLimit)
                .put("permission_overwrites", permOverwrites);
    }

    public VoiceChannel copy() {
        return new VoiceChannel(identity, guild, id)
                .setName(name)
                .setPosition(position)
                .setBitrate(bitrate)
                .setUserLimit(userLimit)
                .setPermOverwrites(permOverwrites);
    }

    @Override
    public IChannelManager getManager() {
        return channelManager;
    }

    @Override
    public IChannelModifier getModifier() {
        return null;
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
        return userLimit;
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
        if (member.isOwner()) return true;
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

    public VoiceChannel setName(String name) {
        this.name = name;
        return this;
    }

    public VoiceChannel setPosition(int position) {
        this.position = position;
        return this;
    }

    public VoiceChannel setBitrate(int bitrate) {
        this.bitrate = bitrate;
        return this;
    }

    public VoiceChannel setUserLimit(int userLimit) {
        this.userLimit = userLimit;
        return this;
    }

    public VoiceChannel setPermOverwrites(Collection<PermOverwrite> permOverwrites) {
        this.permOverwrites = permOverwrites;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof VoiceChannel) && Objects.equals(this.id, ((VoiceChannel) obj).getId());
    }

    @Override
    public String toString() {
        return "VoiceChannel{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", guild=" + guild +
                ", name='" + name + '\'' +
                '}';
    }

}
