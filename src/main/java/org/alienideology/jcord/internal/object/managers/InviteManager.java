package org.alienideology.jcord.internal.object.managers;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.IInvite;
import org.alienideology.jcord.handle.audit.AuditAction;
import org.alienideology.jcord.handle.channel.IGuildChannel;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.managers.IInviteManager;
import org.alienideology.jcord.handle.permission.Permission;
import org.alienideology.jcord.internal.exception.PermissionException;
import org.alienideology.jcord.internal.gateway.HttpPath;
import org.alienideology.jcord.internal.gateway.Requester;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.ObjectBuilder;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author AlienIdeology
 */
public final class InviteManager implements IInviteManager {

    private Guild guild;
    private IGuildChannel channel;

    boolean isGuild;

    public InviteManager(Guild guild) {
        this.guild = guild;
        this.channel = guild.getDefaultChannel();
        this.isGuild = true;
    }

    public InviteManager(IGuildChannel channel) {
        this.guild = (Guild) channel.getGuild();
        this.channel = channel;
        this.isGuild = false;
    }

    @Override
    public Identity getIdentity() {
        return guild.getIdentity();
    }

    @Override
    public IGuild getGuild() {
        return guild;
    }

    @Override
    public IGuildChannel getGuildChannel() {
        return channel;
    }

    @Override
    public List<IInvite> getGuildInvites() {
        if (!guild.getSelfMember().hasPermissions(true, Permission.MANAGE_SERVER)) {
            throw new PermissionException(Permission.ADMINISTRATOR, Permission.MANAGE_SERVER);
        }

        List<IInvite> invites = new ArrayList<>();
        ObjectBuilder builder = new ObjectBuilder((IdentityImpl) getIdentity());
        JSONArray guildInvites = new Requester(getIdentity(), HttpPath.Invite.GET_GUILD_INVITES).request(guild.getId())
                .getAsJSONArray();

        for (int i = 0; i < guildInvites.length(); i++) {
            JSONObject invite = guildInvites.getJSONObject(i);
            invites.add(builder.buildInvite(invite));
        }

        return invites;
    }

    @Override
    public List<IInvite> getChannelInvites() {
        if (!channel.hasPermission(guild.getSelfMember(), Permission.ADMINISTRATOR, Permission.MANAGE_CHANNELS)) {
            throw new PermissionException(Permission.ADMINISTRATOR, Permission.MANAGE_CHANNELS);
        }

        List<IInvite> invites = new ArrayList<>();
        ObjectBuilder builder = new ObjectBuilder((IdentityImpl) getIdentity());
        JSONArray guildInvites = new Requester(getIdentity(), HttpPath.Invite.GET_CHANNEL_INVITES).request(channel.getId())
                .getAsJSONArray();

        for (int i = 0; i < guildInvites.length(); i++) {
            JSONObject invite = guildInvites.getJSONObject(i);
            invites.add(builder.buildInvite(invite));
        }

        return invites;
    }

    @Override
    @Nullable
    public IInvite getInvite(String code) {
        List<IInvite> invites = isGuild ? getChannelInvites() : getChannelInvites();
        for (IInvite invite : invites) {
            if (invite.getCode().equals(code)) {
                return invite;
            }
        }
        return null;
    }

    @Override
    public AuditAction<IInvite> createInvite(int maxUses, long maxAge, boolean isTemporary, boolean isUnique) {
        if (!guild.getSelfMember().hasPermissions(true, Permission.CREATE_INSTANT_INVITE)) {
            throw new PermissionException(Permission.ADMINISTRATOR, Permission.CREATE_INSTANT_INVITE);
        }

        JSONObject json = new JSONObject()
                .put("max_uses", maxUses)
                .put("max_age", maxAge)
                .put("temporary", isTemporary)
                .put("unique", isUnique);

        return new AuditAction<IInvite>((IdentityImpl) getIdentity(), HttpPath.Invite.CREATE_CHANNEL_INVITE, channel.getId()) {
            @Override
            protected IInvite request(Requester requester) {
                JSONObject invite = requester.updateRequestWithBody(request -> request.body(json)).getAsJSONObject();
                return new ObjectBuilder((IdentityImpl) getIdentity()).buildInvite(invite);
            }
        };
    }

    @Override
    public AuditAction<Void> deleteInvite(String code) {
        if (!guild.getSelfMember().hasPermissions(true, Permission.MANAGE_CHANNELS)) {
            throw new PermissionException(Permission.ADMINISTRATOR, Permission.MANAGE_CHANNELS);
        }

        return new AuditAction<Void>((IdentityImpl) getIdentity(), HttpPath.Invite.DELETE_INVITE, code) {
            @Override
            protected Void request(Requester requester) {
                requester.performRequest();
                return null;
            }
        };
    }

}
