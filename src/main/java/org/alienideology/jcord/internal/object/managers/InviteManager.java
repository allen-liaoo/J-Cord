package org.alienideology.jcord.internal.object.managers;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.IInvite;
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

    public InviteManager(Guild guild) {
        this.guild = guild;
        this.channel = guild.getDefaultChannel();
    }

    public InviteManager(IGuildChannel channel) {
        this.guild = (Guild) channel.getGuild();
        this.channel = channel;
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
        JSONArray guildInvites = new Requester((IdentityImpl) getIdentity(), HttpPath.Guild.GET_GUILD_INVITES).request(guild.getId())
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
        JSONArray guildInvites = new Requester((IdentityImpl) getIdentity(), HttpPath.Channel.GET_CHANNEL_INVITES).request(channel.getId())
                .getAsJSONArray();

        for (int i = 0; i < guildInvites.length(); i++) {
            JSONObject invite = guildInvites.getJSONObject(i);
            invites.add(builder.buildInvite(invite));
        }

        return invites;
    }

    @Override
    public IInvite createInvite(int maxUses, long maxAge, boolean isTemporary, boolean isUnique) {
        if (!guild.getSelfMember().hasPermissions(true, Permission.CREATE_INSTANT_INVITE)) {
            throw new PermissionException(Permission.ADMINISTRATOR, Permission.CREATE_INSTANT_INVITE);
        }

        JSONObject json = new JSONObject()
                .put("max_uses", maxUses)
                .put("max_age", maxAge)
                .put("temporary", isTemporary)
                .put("unique", isUnique);

        JSONObject invite = new Requester((IdentityImpl) getIdentity(), HttpPath.Channel.CREATE_CHANNEL_INVITE).request(channel.getId())
                .updateRequestWithBody(request -> request.body(json)).getAsJSONObject();
        return new ObjectBuilder((IdentityImpl) getIdentity()).buildInvite(invite);
    }

    @Override
    public void deleteInvite(IInvite invite) {
        deleteInvite(invite.getCode());
    }

    @Override
    public void deleteInvite(String code) {
        if (!guild.getSelfMember().hasPermissions(true, Permission.MANAGE_CHANNELS)) {
            throw new PermissionException(Permission.ADMINISTRATOR, Permission.MANAGE_CHANNELS);
        }

        new Requester((IdentityImpl) getIdentity(), HttpPath.Channel.DELETE_INVITE).request(code)
                .performRequest();
    }

}
