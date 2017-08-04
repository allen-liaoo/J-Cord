package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.event.channel.group.update.GroupIconUpdateEvent;
import org.alienideology.jcord.event.channel.group.update.GroupNameUpdateEvent;
import org.alienideology.jcord.event.channel.group.update.GroupOwnerUpdateEvent;
import org.alienideology.jcord.event.channel.guild.text.update.TextChannelNameUpdateEvent;
import org.alienideology.jcord.event.channel.guild.text.update.TextChannelPermissionsUpdateEvent;
import org.alienideology.jcord.event.channel.guild.text.update.TextChannelPositionUpdateEvent;
import org.alienideology.jcord.event.channel.guild.text.update.TextChannelTopicUpdateEvent;
import org.alienideology.jcord.event.channel.guild.voice.update.*;
import org.alienideology.jcord.handle.channel.IChannel;
import org.alienideology.jcord.handle.channel.IGuildChannel;
import org.alienideology.jcord.handle.permission.PermOverwrite;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.Group;
import org.alienideology.jcord.internal.object.channel.TextChannel;
import org.alienideology.jcord.internal.object.channel.VoiceChannel;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.util.log.LogLevel;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author AlienIdeology
 */
public class ChannelUpdateEventHandler extends EventHandler {

    public ChannelUpdateEventHandler(IdentityImpl identity) {
        super(identity);
    }

    @Override
    public void dispatchEvent(JSONObject json, int sequence) {
        IChannel.Type type = IChannel.Type.getByKey(json.getInt("type"));

        if (type == IChannel.Type.GROUP_DM) {
            groupUpdate(json, sequence);
            return;
        }

        IGuildChannel oldChannel = identity.getGuildChannel(json.getString("id"));
        Guild guild = (Guild) oldChannel.getGuild();
        IGuildChannel newChannel = builder.buildGuildChannel(json);

        // PermOverwrites
        List<PermOverwrite> oldPOW = new ArrayList<>();
        oldPOW.addAll(oldChannel.getPermOverwrites());
        List<PermOverwrite> newPOW = new ArrayList<>();
        newPOW.addAll(newChannel.getPermOverwrites());

        List<PermOverwrite> overwrites = getChangedPermOverwrites(oldPOW, newPOW);

        if (type.equals(IChannel.Type.GUILD_TEXT)) {
            // Update the channel instance
            guild.removeTextChannel(oldChannel.getId());
            guild.addGuildChannel(newChannel);

            TextChannel oChannel = (TextChannel) oldChannel;
            TextChannel nChannel = (TextChannel) newChannel;

            if (!Objects.equals(oChannel.getName(), nChannel.getName())) {
                dispatchEvent(new TextChannelNameUpdateEvent(identity, sequence, nChannel, oChannel, null));
            }

            if (!Objects.equals(oChannel.getPosition(), nChannel.getPosition())) {
                dispatchEvent(new TextChannelPositionUpdateEvent(identity, sequence, nChannel, oChannel));
            }

            if (overwrites.size() > 0) {
                dispatchEvent(new TextChannelPermissionsUpdateEvent(identity, sequence, nChannel, oChannel, overwrites));
            }

            if (!Objects.equals(oChannel.getTopic(), nChannel.getTopic())) {
                dispatchEvent(new TextChannelTopicUpdateEvent(identity, sequence, nChannel, oChannel));
            }

        } else if (type.equals(IChannel.Type.GUILD_VOICE)) {
            // Update the channel instance
            guild.removeVoiceChannel(oldChannel.getId());
            guild.addGuildChannel(newChannel);

            VoiceChannel oChannel = (VoiceChannel) oldChannel;
            VoiceChannel nChannel = (VoiceChannel) newChannel;

            if (!Objects.equals(oChannel.getName(), nChannel.getName())) {
                dispatchEvent(new VoiceChannelNameUpdateEvent(identity, sequence, nChannel, oChannel));
            }

            if (!Objects.equals(oChannel.getPosition(), nChannel.getPosition())) {
                dispatchEvent(new VoiceChannelPositionUpdateEvent(identity, sequence, nChannel, oChannel));
            }

            if (overwrites.size() > 0) {
                dispatchEvent(new VoiceChannelPermissionsUpdateEvent(identity, sequence, nChannel, oChannel, overwrites));
            }

            if (!Objects.equals(oChannel.getBitrate(), nChannel.getBitrate())) {
                dispatchEvent(new VoiceChannelBitrateUpdateEvent(identity, sequence, nChannel, oChannel));
            }

            if (!Objects.equals(oChannel.getUserLimit(), nChannel.getUserLimit())) {
                dispatchEvent(new VoiceChannelUserLimitUpdateEvent(identity, sequence, nChannel, oChannel));
            }
        } else {
            logger.log(LogLevel.FETAL, "[UNEXPECTED CHANNEL TYPE] [CHANNEL_UPDATE_EVENT_HANDLER] Type: " + type.key);
        }

    }

    private List<PermOverwrite> getChangedPermOverwrites(List<PermOverwrite> oldOverwrites, List<PermOverwrite> newOverwrites) {
        List<PermOverwrite> permOverwrites = new ArrayList<>();

        for (PermOverwrite overwrite : oldOverwrites) {
            if (!newOverwrites.contains(overwrite))
                permOverwrites.add(overwrite);
            int index = newOverwrites.indexOf(overwrite);
            if (index != -1 && !overwrite.equals(newOverwrites.get(index))) {
                permOverwrites.add(overwrite);
            }
        }
        return permOverwrites;
    }

    private void groupUpdate(JSONObject json, int sequence) {
        System.out.println(json.toString(4));
        Group group = (Group) identity.getClient().getGroup(json.getString("id"));
        if (group == null) {
            logger.log(LogLevel.FETAL, "[UNKNOWN GROUP] [CHANNEL_UPDATE_EVENT_HANDLER] ID: " + json.getString("id"));
            return;
        }

        String ownerId = json.getString("owner_id");
        IUser owner = group.getRecipient(ownerId);
        if (owner == null) {
            // Not GroupOwnerUpdateEvent
            if (ownerId.equals(group.getOwner().getId())) {
                owner = group.getOwner();
            } else {
                logger.log(LogLevel.FETAL, "[UNKNOWN GROUP OWNER] [CHANNEL_UPDATE_EVENT_HANDLER] ID: " + ownerId);
                return;
            }
        }

        String name = json.isNull("name") ? null : json.getString("name");
        String icon = json.isNull("icon") ? null : json.getString("icon");

        if (!Objects.equals(owner, group.getOwner())) {
            IUser oldOwner = group.getOwner();
            group.setOwnerId(ownerId);
            dispatchEvent(new GroupOwnerUpdateEvent(identity, sequence, group, oldOwner));
        }
        if (!Objects.equals(name, group.getName())) {
            String oldName = group.getName();
            group.setName(name);
            dispatchEvent(new GroupNameUpdateEvent(identity, sequence, group, oldName));
        }
        if (!Objects.equals(icon, group.getIconHash())) {
            String oldIcon = group.getIconHash();
            group.setIcon(icon);
            dispatchEvent(new GroupIconUpdateEvent(identity, sequence, group, oldIcon));
        }
    }

}
