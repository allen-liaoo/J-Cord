package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.event.channel.dm.PrivateChannelDeleteEvent;
import org.alienideology.jcord.event.channel.guild.TextChannelDeleteEvent;
import org.alienideology.jcord.event.channel.guild.VoiceChannelDeleteEvent;
import org.alienideology.jcord.handle.channel.IChannel;
import org.alienideology.jcord.handle.channel.IGuildChannel;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.Channel;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.json.JSONObject;

import java.time.OffsetDateTime;

/**
 * @author AlienIdeology
 */
public class ChannelDeleteEventHandler extends EventHandler {

    public ChannelDeleteEventHandler(IdentityImpl identity) {
        super(identity);
    }

    @Override
    public void dispatchEvent(JSONObject json, int sequence) {
        Channel channel = (Channel) identity.getChannel(json.getString("id"));
        OffsetDateTime timeStamp = OffsetDateTime.now();

        if (channel.isType(IChannel.Type.PRIVATE)) {
            identity.removePrivateChannel(channel.getId());
            fireEvent(new PrivateChannelDeleteEvent(identity, sequence, channel, timeStamp));
        } else {
            Guild guild = (Guild) ((IGuildChannel) channel).getGuild();
            if (channel.isType(IChannel.Type.TEXT)) {
                guild.removeTextChannel(channel.getId());
                fireEvent(new TextChannelDeleteEvent(identity, sequence, channel, timeStamp, guild));
            } else {
                guild.removeVoiceChannel(channel.getId());
                fireEvent(new VoiceChannelDeleteEvent(identity, sequence, channel, timeStamp, guild));
            }
        }
    }

}
