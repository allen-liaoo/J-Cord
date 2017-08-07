package org.alienideology.jcord.internal.object.managers;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.Icon;
import org.alienideology.jcord.handle.audit.AuditAction;
import org.alienideology.jcord.handle.channel.IChannel;
import org.alienideology.jcord.handle.channel.IGuildChannel;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IMember;
import org.alienideology.jcord.handle.guild.IRole;
import org.alienideology.jcord.handle.managers.IChannelManager;
import org.alienideology.jcord.handle.permission.Permission;
import org.alienideology.jcord.handle.user.IWebhook;
import org.alienideology.jcord.internal.exception.ErrorResponseException;
import org.alienideology.jcord.internal.exception.HigherHierarchyException;
import org.alienideology.jcord.internal.exception.HttpErrorException;
import org.alienideology.jcord.internal.exception.PermissionException;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.ObjectBuilder;
import org.alienideology.jcord.internal.object.channel.TextChannel;
import org.alienideology.jcord.internal.object.channel.VoiceChannel;
import org.alienideology.jcord.internal.rest.ErrorResponse;
import org.alienideology.jcord.internal.rest.HttpCode;
import org.alienideology.jcord.internal.rest.HttpPath;
import org.alienideology.jcord.internal.rest.Requester;
import org.json.JSONObject;

import java.util.Collection;

/**
 * @author AlienIdeology
 */
public final class ChannelManager implements IChannelManager {

    private IGuildChannel channel;

    public ChannelManager(TextChannel channel) {
        this.channel = channel;
    }

    public ChannelManager(VoiceChannel channel) {
        this.channel = channel;
    }

    @Override
    public Identity getIdentity() {
        return channel.getIdentity();
    }

    @Override
    public IGuild getGuild() {
        return channel.getGuild();
    }

    @Override
    public IGuildChannel getGuildChannel() {
        return channel;
    }

    @Override
    public AuditAction<Void> editPermOverwrite(IMember member, Collection<Permission> allowed, Collection<Permission> denied) {
        if (!member.getGuild().equals(getGuild())) {
            throw new ErrorResponseException(ErrorResponse.UNKNOWN_MEMBER);
        }
        if (!channel.hasPermission(getGuild().getSelfMember(), Permission.ADMINISTRATOR, Permission.MANAGE_ROLES)) {
            throw new PermissionException(Permission.ADMINISTRATOR, Permission.MANAGE_ROLES);
        }
        if (!getGuild().getSelfMember().canModify(member)) {
            throw new HigherHierarchyException(HigherHierarchyException.HierarchyType.ROLE);
        }

        return editPerms(member.getId(),
                new JSONObject()
                        .put("allow", Permission.getLongByPermissions(allowed))
                        .put("deny", Permission.getLongByPermissions(denied))
                        .put("type", "member")
        );
    }

    @Override
    public AuditAction<Void> editPermOverwrite(IRole role, Collection<Permission> allowed, Collection<Permission> denied) {
        if (!role.getGuild().equals(getGuild())) {
            throw new ErrorResponseException(ErrorResponse.UNKNOWN_ROLE);
        }
        if (!channel.hasPermission(getGuild().getSelfMember(), Permission.ADMINISTRATOR, Permission.MANAGE_ROLES)) {
            throw new PermissionException(Permission.ADMINISTRATOR, Permission.MANAGE_ROLES);
        }
        if (!getGuild().getSelfMember().canModify(role)) {
            throw new HigherHierarchyException(HigherHierarchyException.HierarchyType.ROLE);
        }

        return editPerms(role.getId(),
                new JSONObject()
                        .put("allow", Permission.getLongByPermissions(allowed))
                        .put("deny", Permission.getLongByPermissions(denied))
                        .put("type", "role")
        );
    }

    private AuditAction<Void> editPerms(String id, JSONObject json) {
        return new AuditAction<Void>((IdentityImpl) getIdentity(), HttpPath.Channel.EDIT_CHANNEL_PERMISSIONS, getGuildChannel().getId(), id) {
            @Override
            protected Void request(Requester requester) {
                requester.updateRequestWithBody(request -> request.body(json))
                        .performRequest();
                return null;
            }
        };
    }

    @Override
    public AuditAction<Void> deletePermOverwrite(String id) {
        if (!channel.hasPermission(getGuild().getSelfMember(), Permission.ADMINISTRATOR, Permission.MANAGE_ROLES)) {
            throw new PermissionException(Permission.ADMINISTRATOR, Permission.MANAGE_ROLES);
        }

        try {
            return new AuditAction<Void>((IdentityImpl) getIdentity(), HttpPath.Channel.DELETE_CHANNEL_PERMISSION, getGuildChannel().getId(), id) {
                @Override
                protected Void request(Requester requester) {
                    requester.performRequest();
                    return null;
                }
            };
        } catch (HttpErrorException ex) {
            if (ex.isPermissionException()) {
                // Can be thrown by modifying higher hierarchy perm overwrite
                // Not sure if this will really cause the problem
                throw new HigherHierarchyException(HigherHierarchyException.HierarchyType.UNKNOWN);
            } else if (ex.getCode().equals(HttpCode.NOT_FOUND)) {
                throw new ErrorResponseException(ErrorResponse.UNKNOWN_OVERWRITE);
            } else {
                throw ex;
            }
        }
    }

    @Override
    public AuditAction<IWebhook> createWebhook(String defaultName, Icon defaultAvatar) {
        if (getGuildChannel().isType(IChannel.Type.GUILD_VOICE)) {
            throw new IllegalArgumentException("Cannot delete a webhook from a voice channel!");
        }
        if (!channel.hasPermission(getGuild().getSelfMember(), Permission.ADMINISTRATOR, Permission.MANAGE_WEBHOOKS)) {
            throw new PermissionException(Permission.ADMINISTRATOR, Permission.MANAGE_WEBHOOKS);
        }

        if (!IWebhook.isValidWebhookName(defaultName)) {
            throw new IllegalArgumentException("The webhook to create does not have a valid name!");
        }

        return new AuditAction<IWebhook>((IdentityImpl) getIdentity(), HttpPath.Webhook.CREATE_WEBHOOK, getGuildChannel().getId()) {
            @Override
            protected IWebhook request(Requester requester) {
                JSONObject wh = requester.updateRequestWithBody(request ->
                                request.body(new JSONObject()
                                        .put("name", defaultName == null ? "" : defaultName)
                                        .put("avatar", defaultAvatar.getData())))
                        .getAsJSONObject();
                return new ObjectBuilder((IdentityImpl) getIdentity()).buildWebhook(wh);
            }
        };
    }
}
