package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.event.channel.dm.PrivateChannelCreateEvent;
import org.alienideology.jcord.event.channel.guild.text.TextChannelCreateEvent;
import org.alienideology.jcord.event.channel.guild.voice.VoiceChannelCreateEvent;
import org.alienideology.jcord.handle.channel.IChannel;
import org.alienideology.jcord.handle.channel.IGuildChannel;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.Channel;
import org.alienideology.jcord.internal.object.channel.PrivateChannel;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.json.JSONObject;

/**
 * @author AlienIdeology
 */
public class ChannelCreateEventHandler extends EventHandler {

    public ChannelCreateEventHandler(IdentityImpl identity) {
        super(identity);
    }

    @Override
    public void dispatchEvent(JSONObject json, int sequence) {
        boolean isPrivate = json.has("is_private") && json.getBoolean("is_private");

        Channel channel;

        if (isPrivate) {
            channel = builder.buildPrivateChannel(json);
            identity.addPrivateChannel((PrivateChannel) channel);

            dispatchEvent(new PrivateChannelCreateEvent(identity, sequence, channel));
        } else {
            channel = (Channel) builder.buildGuildChannel(json);
            Guild guild = (Guild) ((IGuildChannel) channel).getGuild();
            guild.addGuildChannel((IGuildChannel) channel);
            if (channel.isType(IChannel.Type.TEXT)) {
                dispatchEvent(new TextChannelCreateEvent(identity, sequence, channel, guild));
            } else {
                dispatchEvent(new VoiceChannelCreateEvent(identity, sequence, channel, guild));
            }
        }
    }

}
