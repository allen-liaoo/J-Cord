package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.event.channel.guild.text.update.TextChannelNameUpdateEvent;
import org.alienideology.jcord.event.channel.guild.text.update.TextChannelPermissionsUpdateEvent;
import org.alienideology.jcord.event.channel.guild.text.update.TextChannelPositionUpdateEvent;
import org.alienideology.jcord.event.channel.guild.text.update.TextChannelTopicUpdateEvent;
import org.alienideology.jcord.event.channel.guild.voice.update.*;
import org.alienideology.jcord.handle.channel.IChannel;
import org.alienideology.jcord.handle.channel.IGuildChannel;
import org.alienideology.jcord.handle.permission.PermOverwrite;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.TextChannel;
import org.alienideology.jcord.internal.object.channel.VoiceChannel;
import org.alienideology.jcord.internal.object.guild.Guild;
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
        IGuildChannel oldChannel = identity.getGuildChannel(json.getString("id"));
        Guild guild = (Guild) oldChannel.getGuild();
        IGuildChannel newChannel = builder.buildGuildChannel(json);

        // PermOverwrites
        List<PermOverwrite> oldPOW = new ArrayList<>();
        oldPOW.addAll(oldChannel.getPermOverwrites());
        List<PermOverwrite> newPOW = new ArrayList<>();
        newPOW.addAll(newChannel.getPermOverwrites());

        List<PermOverwrite> overwrites = getChangedPermOverwrites(oldPOW, newPOW);

        if (oldChannel.isType(IChannel.Type.TEXT)) {
            // Update the channel instance
            guild.removeTextChannel(oldChannel.getId());
            guild.addGuildChannel(newChannel);

            TextChannel oChannel = (TextChannel) oldChannel;
            TextChannel nChannel = (TextChannel) newChannel;

            if (!Objects.equals(oChannel.getName(), nChannel.getName())) {
                fireEvent(new TextChannelNameUpdateEvent(identity, sequence, nChannel, oChannel, null));
            }

            if (!Objects.equals(oChannel.getPosition(), nChannel.getPosition())) {
                fireEvent(new TextChannelPositionUpdateEvent(identity, sequence, nChannel, oChannel));
            }

            if (overwrites.size() > 0) {
                fireEvent(new TextChannelPermissionsUpdateEvent(identity, sequence, nChannel, oChannel, overwrites));
            }

            if (!Objects.equals(oChannel.getTopic(), nChannel.getTopic())) {
                fireEvent(new TextChannelTopicUpdateEvent(identity, sequence, nChannel, oChannel));
            }

        } else {
            // Update the channel instance
            guild.removeVoiceChannel(oldChannel.getId());
            guild.addGuildChannel(newChannel);

            VoiceChannel oChannel = (VoiceChannel) oldChannel;
            VoiceChannel nChannel = (VoiceChannel) newChannel;

            if (!Objects.equals(oChannel.getName(), nChannel.getName())) {
                fireEvent(new VoiceChannelNameUpdateEvent(identity, sequence, nChannel, oChannel));
            }

            if (!Objects.equals(oChannel.getPosition(), nChannel.getPosition())) {
                fireEvent(new VoiceChannelPositionUpdateEvent(identity, sequence, nChannel, oChannel));
            }

            if (overwrites.size() > 0) {
                fireEvent(new VoiceChannelPermissionsUpdateEvent(identity, sequence, nChannel, oChannel, overwrites));
            }

            if (!Objects.equals(oChannel.getBitrate(), nChannel.getBitrate())) {
                fireEvent(new VoiceChannelBitrateUpdateEvent(identity, sequence, nChannel, oChannel));
            }

            if (!Objects.equals(oChannel.getUserLimit(), nChannel.getUserLimit())) {
                fireEvent(new VoiceChannelUserLimitUpdateEvent(identity, sequence, nChannel, oChannel));
            }
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

}
