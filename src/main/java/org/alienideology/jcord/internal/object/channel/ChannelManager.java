package org.alienideology.jcord.internal.object.channel;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.Permission;
import org.alienideology.jcord.handle.channel.IChannelManager;
import org.alienideology.jcord.handle.channel.IGuildChannel;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.internal.exception.PermissionException;
import org.alienideology.jcord.internal.gateway.HttpPath;
import org.alienideology.jcord.internal.gateway.Requester;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.json.JSONObject;

/**
 * @author AlienIdeology
 */
public class ChannelManager implements IChannelManager {

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
        if (name.length() < CHANNEL_NAME_LENGTH_MIN || name.length() > CHANNEL_NAME_LENGTH_MAX) {
            throw new IllegalArgumentException("The channel's name can not be shorter than "+CHANNEL_NAME_LENGTH_MIN+
                    " or longer than "+CHANNEL_NAME_LENGTH_MAX+" characters!");
        }
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
        if (topic.length() > TEXT_CHANNEL_TOPIC_LENGTH_MAX) {
            throw new IllegalArgumentException("The TextChannel's topic may not be longer than"+ TEXT_CHANNEL_TOPIC_LENGTH_MAX +" characters!");
        }
        modifyChannel(new JSONObject().put("topic", topic));
    }

    @Override
    public void modifyBitrate(int bitrate) {
        if (channel instanceof TextChannel) {
            throw new IllegalArgumentException("Cannot modify the bitrate of a text channel!");
        }

        if (bitrate < VOICE_CHANNEL_BITRATE_MIN) {
            throw new IllegalArgumentException("The bitrate can not be lower than "+VOICE_CHANNEL_BITRATE_MIN+"!");
        } else if (bitrate > VOICE_CHANNEL_BITRATE_MAX) {
            if (getGuild().getSplash() != null && bitrate > VOICE_CHANNEL_BITRATE_VIP_MAX) { // Guild is VIP
                throw new IllegalArgumentException("The bitrate of a vip guild can not be greater than "+VOICE_CHANNEL_BITRATE_VIP_MAX+"!");
            } else {
                throw new IllegalArgumentException("The bitrate of a normal guild can not be greater than "+VOICE_CHANNEL_BITRATE_MAX+"!");
            }
        }

        modifyChannel(new JSONObject().put("bitrate", bitrate));
    }

    @Override
    public void modifyUserLimit(int limit) {
        if (channel instanceof TextChannel) {
            throw new IllegalArgumentException("Cannot modify the user limit of a text channel!");
        }
        if (limit < VOICE_CHANNEL_USER_LIMIT_MIN || limit > VOICE_CHANNEL_USER_LIMIT_MAX) {
            throw new IllegalArgumentException("The user limit may not be lower than "+VOICE_CHANNEL_USER_LIMIT_MIN+" or greater than "+VOICE_CHANNEL_USER_LIMIT_MAX+"!");
        }
        modifyChannel(new JSONObject().put("user_limit", limit));
    }

    private void modifyChannel(JSONObject json) {
        if (!getGuild().getSelfMember().hasPermissions(true, Permission.MANAGE_CHANNELS)) {
            throw new PermissionException(Permission.ADMINISTRATOR, Permission.MANAGE_CHANNELS);
        }

        new Requester((IdentityImpl) getIdentity(), HttpPath.Channel.MODIFY_CHANNEL)
                .request(channel.getId()).updateRequestWithBody(request -> request.body(json)).performRequest();
    }

    @Override
    public void deleteChannel() {
        if (!getGuild().getSelfMember().hasPermissions(true, Permission.MANAGE_CHANNELS)) {
            throw new PermissionException(Permission.ADMINISTRATOR, Permission.MANAGE_CHANNELS);
        }

        if (channel instanceof TextChannel) {
            if (((TextChannel) channel).isDefaultChannel()) {
                throw new PermissionException("Cannot delete the default channel of a guild!");
            }
        }

        new Requester((IdentityImpl) getIdentity(), HttpPath.Channel.DELETE_CHANNEL)
                .request(channel.getId()).performRequest();
    }

}
