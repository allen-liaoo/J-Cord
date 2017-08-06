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
import org.alienideology.jcord.handle.user.IWebhook;
import org.alienideology.jcord.internal.exception.PermissionException;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.Jsonable;
import org.alienideology.jcord.internal.object.ObjectBuilder;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.managers.ChannelManager;
import org.alienideology.jcord.internal.object.managers.InviteManager;
import org.alienideology.jcord.internal.rest.HttpPath;
import org.alienideology.jcord.internal.rest.Requester;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @author AlienIdeology
 */
public final class TextChannel extends MessageChannel implements ITextChannel, Jsonable {

    private Guild guild;
    private ChannelManager channelManager;
    private InviteManager inviteManager;

    private String name;
    private int position;
    private String topic;

    private List<PermOverwrite> permOverwrites = new ArrayList<>();

    public TextChannel(IdentityImpl identity, String guild_id, String id, String name, int position, String topic) {
        super(identity, id, IChannel.Type.GUILD_TEXT);
        this.guild = guild_id == null ? null : (Guild) identity.getGuild(guild_id);
        this.name = name;
        this.position = position;
        this.topic = topic;
        this.channelManager = new ChannelManager(this);
        this.inviteManager = new InviteManager(this);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject()
                .put("name", name == null? "" : name)
                .put("type", Type.GUILD_TEXT.key);
        if (permOverwrites != null && !permOverwrites.isEmpty()) {
            JSONArray perms = new JSONArray();
            for (PermOverwrite perm : permOverwrites) {
                perms.put(perm.toJson());
            }
            json.put("permission_overwrites", perms);
        }
        return json;
    }

    public TextChannel copy() {
        return new TextChannel(identity, guild.getId(), id, name, position, topic);
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
            if (!member.getPermissions().contains(permission))
                return false;
            if (overwrites != null && overwrites.getDeniedPermissions().contains(permission))
                return false;
        }
        return true;
    }

    @Override
    public boolean hasAllPermission(IRole role, Collection<Permission> permissions) {
        PermOverwrite overwrites = getRolePermOverwrite(role.getId());
        for (Permission permission : permissions) {
            if (!role.getPermissions().contains(permission))
                return false;
            if (overwrites != null && overwrites.getDeniedPermissions().contains(permission))
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
    public @Nullable IWebhook getWebhook(String id) {
        for (IWebhook webhook : getWebhooks()) {
            if (webhook.getId().equals(id)) {
                return webhook;
            }
        }
        return null;
    }

    @Override
    public List<IWebhook> getWebhooks() {
        if (!hasPermission(guild.getSelfMember(), Permission.ADMINISTRATOR, Permission.MANAGE_WEBHOOKS)) {
            throw new PermissionException(Permission.ADMINISTRATOR, Permission.MANAGE_WEBHOOKS);
        }

        JSONArray whs = new Requester(identity, HttpPath.Webhook.GET_CHANNEL_WEBHOOKS).request(id)
                .getAsJSONArray();
        List<IWebhook> webhooks = new ArrayList<>();
        ObjectBuilder builder = new ObjectBuilder(identity);
        for (int i = 0; i < whs.length(); i++) {
            webhooks.add(builder.buildWebhook(whs.getJSONObject(i)));
        }
        return webhooks;
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
