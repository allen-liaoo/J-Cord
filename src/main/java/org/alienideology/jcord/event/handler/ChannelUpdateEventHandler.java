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
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.permission.PermOverwrite;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.Group;
import org.alienideology.jcord.internal.object.channel.TextChannel;
import org.alienideology.jcord.internal.object.channel.VoiceChannel;
import org.alienideology.jcord.util.log.LogLevel;
import org.json.JSONArray;
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

        if (type.equals(IChannel.Type.GROUP_DM)) {
            groupUpdate(json, sequence);
            return;
        }

        IGuildChannel channel = identity.getGuildChannel(json.getString("id"));
        if (channel == null) {
            logger.log(LogLevel.FETAL, "[UNKNOWN CHANNEL][CHANNEL_UPDATE_EVENT] ID: " + json.getString("id"));
            return;
        }
        IGuild guild = channel.getGuild();

        String name = json.getString("name");
        int position = json.getInt("position");

        // PermOverwrites
        List<PermOverwrite> oldPerms = new ArrayList<>(channel.getPermOverwrites());
        List<PermOverwrite> newPerms = new ArrayList<>();
        JSONArray perms = json.getJSONArray("permission_overwrites");
        for (int i = 0; i < perms.length(); i++) {
            newPerms.add(builder.buildPermOverwrite(guild, perms.getJSONObject(i)));
        }

        List<PermOverwrite> changed = getChangedPermOverwrites(oldPerms, newPerms);

        if (type.equals(IChannel.Type.GUILD_TEXT)) {

            TextChannel tc = (TextChannel) channel;

            String topic = json.isNull("topic") ? null : json.getString("topic");

            if (!Objects.equals(tc.getName(), name)) {
                String oldName = tc.getName();
                tc.setName(name);
                dispatchEvent(new TextChannelNameUpdateEvent(identity, sequence, tc, oldName));
            }

            if (!Objects.equals(tc.getPosition(), position)) {
                int oldPosition = tc.getPosition();
                tc.setPosition(position);
                dispatchEvent(new TextChannelPositionUpdateEvent(identity, sequence, tc, oldPosition));
            }

            if (changed.size() > 0) {
                tc.setPermOverwrites(newPerms);
                dispatchEvent(new TextChannelPermissionsUpdateEvent(identity, sequence, tc, changed, oldPerms));
            }

            if (!Objects.equals(tc.getTopic(), topic)) {
                String oldTopic = tc.getTopic();
                tc.setTopic(topic);
                dispatchEvent(new TextChannelTopicUpdateEvent(identity, sequence, tc, oldTopic));
            }

        } else if (type.equals(IChannel.Type.GUILD_VOICE)) {

            VoiceChannel vc = (VoiceChannel) channel;

            int bitrate = json.getInt("bitrate");
            int userLimit = json.getInt("user_limit");

            if (!Objects.equals(vc.getName(), name)) {
                String oldName = vc.getName();
                vc.setName(name);
                dispatchEvent(new VoiceChannelNameUpdateEvent(identity, sequence, vc, oldName));
            }

            if (!Objects.equals(vc.getPosition(), position)) {
                int oldPosition = vc.getPosition();
                vc.setPosition(position);
                dispatchEvent(new VoiceChannelPositionUpdateEvent(identity, sequence, vc, oldPosition));
            }

            if (changed.size() > 0) {
                vc.setPermOverwrites(newPerms);
                dispatchEvent(new VoiceChannelPermissionsUpdateEvent(identity, sequence, vc, changed, oldPerms));
            }

            if (!Objects.equals(vc.getBitrate(), bitrate)) {
                int oldBitrate = vc.getBitrate();
                vc.setBitrate(bitrate);
                dispatchEvent(new VoiceChannelBitrateUpdateEvent(identity, sequence, vc, oldBitrate));
            }

            if (!Objects.equals(vc.getUserLimit(), userLimit)) {
                int oldUserLimit = vc.getUserLimit();
                vc.setUserLimit(oldUserLimit);
                dispatchEvent(new VoiceChannelUserLimitUpdateEvent(identity, sequence, vc, oldUserLimit));
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
