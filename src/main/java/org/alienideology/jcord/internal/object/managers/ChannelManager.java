package org.alienideology.jcord.internal.object.managers;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.Icon;
import org.alienideology.jcord.handle.audit.AuditAction;
import org.alienideology.jcord.handle.channel.IChannel;
import org.alienideology.jcord.handle.channel.IGuildChannel;
import org.alienideology.jcord.handle.channel.ITextChannel;
import org.alienideology.jcord.handle.channel.IVoiceChannel;
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
import org.alienideology.jcord.internal.gateway.ErrorResponse;
import org.alienideology.jcord.internal.gateway.HttpCode;
import org.alienideology.jcord.internal.gateway.HttpPath;
import org.alienideology.jcord.internal.gateway.Requester;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.ObjectBuilder;
import org.alienideology.jcord.internal.object.channel.TextChannel;
import org.alienideology.jcord.internal.object.channel.VoiceChannel;
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
    public AuditAction<Void> modifyName(String name) {
        if (!IGuildChannel.isValidChannelName(name)) {
            throw new IllegalArgumentException("Invalid channel name!");
        }
        return modifyChannel(new JSONObject().put("name", name));
    }

    @Override
    public AuditAction<Void> modifyPosition(int position) {
        return modifyChannel(new JSONObject().put("position", position));
    }

    @Override
    public AuditAction<Void> moveChannelBy(int amount) {
        return modifyPosition(channel.getPosition() + amount);
    }

    @Override
    public AuditAction<Void> modifyTopic(String topic) {
        if (channel instanceof VoiceChannel) {
            throw new IllegalArgumentException("Cannot modify the topic of a voice channel!");
        }
        if (topic == null) topic = "";
        if (!ITextChannel.isValidTopic(topic)) {
            throw new IllegalArgumentException("The TextChannel's topic may not be longer than"+ ITextChannel.TEXT_CHANNEL_TOPIC_LENGTH_MAX +" characters!");
        }
        return modifyChannel(new JSONObject().put("topic", topic));
    }

    @Override
    public AuditAction<Void> modifyBitrate(int bitrate) {
        if (channel instanceof TextChannel) {
            throw new IllegalArgumentException("Cannot modify the bitrate of a text channel!");
        }
        checkBitrate(bitrate, getGuild());

        return modifyChannel(new JSONObject().put("bitrate", bitrate));
    }

    @Override
    public AuditAction<Void> modifyUserLimit(int limit) {
        if (channel instanceof TextChannel) {
            throw new IllegalArgumentException("Cannot modify the user limit of a text channel!");
        }
        if (!IVoiceChannel.isValidUserLimit(limit)) {
            throw new IllegalArgumentException("The user limit is not valid!");
        }
        return modifyChannel(new JSONObject().put("user_limit", limit));
    }

    private AuditAction<Void> modifyChannel(JSONObject json) {
        if (!channel.hasPermission(getGuild().getSelfMember(), Permission.ADMINISTRATOR, Permission.MANAGE_CHANNELS)) {
            throw new PermissionException(Permission.ADMINISTRATOR, Permission.MANAGE_CHANNELS);
        }

        return new AuditAction<Void>((IdentityImpl) getIdentity(), HttpPath.Channel.MODIFY_CHANNEL, channel.getId()) {
            @Override
            protected Void request(Requester requester) {
                requester.updateRequestWithBody(request -> request.body(json)).performRequest();
                return null;
            }
        };
    }

    private void checkBitrate(int bitrate, IGuild guild) {
        if (bitrate < IVoiceChannel.VOICE_CHANNEL_BITRATE_MIN) {
            throw new IllegalArgumentException("The bitrate can not be lower than "+ IVoiceChannel.VOICE_CHANNEL_BITRATE_MIN+"!");
        } else if (bitrate > IVoiceChannel.VOICE_CHANNEL_BITRATE_MAX) {
            if (guild.getSplash() != null && bitrate > IVoiceChannel.VOICE_CHANNEL_BITRATE_VIP_MAX) { // Guild is VIP
                throw new IllegalArgumentException("The bitrate of a vip guild can not be greater than "+ IVoiceChannel.VOICE_CHANNEL_BITRATE_VIP_MAX+"!");
            } else {
                throw new IllegalArgumentException("The bitrate of a normal guild can not be greater than "+ IVoiceChannel.VOICE_CHANNEL_BITRATE_MAX+"!");
            }
        }
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
