package org.alienideology.jcord.internal.object.managers;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.channel.IGuildChannel;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IMember;
import org.alienideology.jcord.handle.guild.IRole;
import org.alienideology.jcord.handle.managers.IChannelManager;
import org.alienideology.jcord.handle.permission.Permission;
import org.alienideology.jcord.internal.exception.ErrorResponseException;
import org.alienideology.jcord.internal.exception.HigherHierarchyException;
import org.alienideology.jcord.internal.exception.HttpErrorException;
import org.alienideology.jcord.internal.exception.PermissionException;
import org.alienideology.jcord.internal.gateway.ErrorResponse;
import org.alienideology.jcord.internal.gateway.HttpCode;
import org.alienideology.jcord.internal.gateway.HttpPath;
import org.alienideology.jcord.internal.gateway.Requester;
import org.alienideology.jcord.internal.object.IdentityImpl;
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
    public void modifyName(String name) {
        checkName(name);
        modifyChannel(new JSONObject().put("name", name));
    }

    @Override
    public void modifyPosition(int position) {
        modifyChannel(new JSONObject().put("position", position));
    }

    @Override
    public void moveChannelBy(int amount) {
        modifyPosition(channel.getPosition() + amount);
    }

    @Override
    public void modifyTopic(String topic) {
        if (channel instanceof VoiceChannel) {
            throw new IllegalArgumentException("Cannot modify the topic of a voice channel!");
        }
        if (topic == null) topic = "";
        checkTopic(topic);
        modifyChannel(new JSONObject().put("topic", topic));
    }

    @Override
    public void modifyBitrate(int bitrate) {
        if (channel instanceof TextChannel) {
            throw new IllegalArgumentException("Cannot modify the bitrate of a text channel!");
        }

        checkBitrate(bitrate, getGuild());

        modifyChannel(new JSONObject().put("bitrate", bitrate));
    }

    @Override
    public void modifyUserLimit(int limit) {
        if (channel instanceof TextChannel) {
            throw new IllegalArgumentException("Cannot modify the user limit of a text channel!");
        }
        checkUserLimit(limit);
        modifyChannel(new JSONObject().put("user_limit", limit));
    }

    private void modifyChannel(JSONObject json) {
        if (!channel.hasPermission(getGuild().getSelfMember(), Permission.ADMINISTRATOR, Permission.MANAGE_CHANNELS)) {
            throw new PermissionException(Permission.ADMINISTRATOR, Permission.MANAGE_CHANNELS);
        }

        new Requester((IdentityImpl) getIdentity(), HttpPath.Channel.MODIFY_CHANNEL)
                .request(channel.getId()).updateRequestWithBody(request -> request.body(json)).performRequest();
    }

    /*
        Utility Checkers
     */
    public static void checkName(String name) {
        if (name.length() < CHANNEL_NAME_LENGTH_MIN || name.length() > CHANNEL_NAME_LENGTH_MAX) {
            throw new IllegalArgumentException("The channel's name can not be shorter than "+CHANNEL_NAME_LENGTH_MIN+
                    " or longer than "+CHANNEL_NAME_LENGTH_MAX+" characters!");
        }
    }

    public static void checkTopic(String topic) {
        if (topic.length() > TEXT_CHANNEL_TOPIC_LENGTH_MAX) {
            throw new IllegalArgumentException("The TextChannel's topic may not be longer than"+ TEXT_CHANNEL_TOPIC_LENGTH_MAX +" characters!");
        }
    }

    public static void checkBitrate(int bitrate, IGuild guild) {
        if (bitrate < VOICE_CHANNEL_BITRATE_MIN) {
            throw new IllegalArgumentException("The bitrate can not be lower than "+VOICE_CHANNEL_BITRATE_MIN+"!");
        } else if (bitrate > VOICE_CHANNEL_BITRATE_MAX) {
            if (guild.getSplash() != null && bitrate > VOICE_CHANNEL_BITRATE_VIP_MAX) { // Guild is VIP
                throw new IllegalArgumentException("The bitrate of a vip guild can not be greater than "+VOICE_CHANNEL_BITRATE_VIP_MAX+"!");
            } else {
                throw new IllegalArgumentException("The bitrate of a normal guild can not be greater than "+VOICE_CHANNEL_BITRATE_MAX+"!");
            }
        }
    }

    public static void checkUserLimit(int limit) {
        if (limit < VOICE_CHANNEL_USER_LIMIT_MIN || limit > VOICE_CHANNEL_USER_LIMIT_MAX) {
            throw new IllegalArgumentException("The user limit may not be lower than "+VOICE_CHANNEL_USER_LIMIT_MIN+" or greater than "+VOICE_CHANNEL_USER_LIMIT_MAX+"!");
        }
    }

    @Override
    public void editPermOverwrite(IMember member, Collection<Permission> allowed, Collection<Permission> denied) {
        if (!member.getGuild().equals(getGuild())) {
            throw new ErrorResponseException(ErrorResponse.UNKNOWN_MEMBER);
        }
        if (!channel.hasPermission(getGuild().getSelfMember(), Permission.ADMINISTRATOR, Permission.MANAGE_ROLES)) {
            throw new PermissionException(Permission.ADMINISTRATOR, Permission.MANAGE_ROLES);
        }
        if (!getGuild().getSelfMember().canModify(member)) {
            throw new HigherHierarchyException(HigherHierarchyException.HierarchyType.ROLE);
        }

        editPerms(member.getId(),
                new JSONObject()
                        .put("allow", Permission.getLongByPermissions(allowed))
                        .put("deny", Permission.getLongByPermissions(denied))
                        .put("type", "member")
        );
    }

    @Override
    public void editPermOverwrite(IRole role, Collection<Permission> allowed, Collection<Permission> denied) {
        if (!role.getGuild().equals(getGuild())) {
            throw new ErrorResponseException(ErrorResponse.UNKNOWN_ROLE);
        }
        if (!channel.hasPermission(getGuild().getSelfMember(), Permission.ADMINISTRATOR, Permission.MANAGE_ROLES)) {
            throw new PermissionException(Permission.ADMINISTRATOR, Permission.MANAGE_ROLES);
        }
        if (!getGuild().getSelfMember().canModify(role)) {
            throw new HigherHierarchyException(HigherHierarchyException.HierarchyType.ROLE);
        }

        editPerms(role.getId(),
                new JSONObject()
                        .put("allow", Permission.getLongByPermissions(allowed))
                        .put("deny", Permission.getLongByPermissions(denied))
                        .put("type", "role")
        );
    }

    private void editPerms(String id, JSONObject json) {
        new Requester((IdentityImpl) getIdentity(), HttpPath.Channel.EDIT_CHANNEL_PERMISSIONS)
                .request(getGuildChannel().getId(), id)
                .updateRequestWithBody(request -> request.body(json))
                .performRequest();
    }

    @Override
    public void deletePermOverwrite(String id) {
        if (!channel.hasPermission(getGuild().getSelfMember(), Permission.ADMINISTRATOR, Permission.MANAGE_ROLES)) {
            throw new PermissionException(Permission.ADMINISTRATOR, Permission.MANAGE_ROLES);
        }

        try {
            new Requester((IdentityImpl) getIdentity(), HttpPath.Channel.DELETE_CHANNEL_PERMISSION)
                    .request(getGuildChannel().getId(), id)
                    .performRequest();
        } catch (HttpErrorException ex) {
            if (ex.isPermissionException()) {
                // Can by modifying higher hierarchy perm overwrite
                // Not sure if this is thrown properly
                throw new HigherHierarchyException(HigherHierarchyException.HierarchyType.UNKNOWN);
            } else if (ex.getCode().equals(HttpCode.NOT_FOUND)) {
                throw new ErrorResponseException(ErrorResponse.UNKNOWN_OVERWRITE);
            } else {
                throw ex;
            }
        }
    }
}
