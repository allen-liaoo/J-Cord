package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.event.channel.dm.PrivateChannelCreateEvent;
import org.alienideology.jcord.event.channel.group.GroupCreateEvent;
import org.alienideology.jcord.event.channel.guild.text.TextChannelCreateEvent;
import org.alienideology.jcord.event.channel.guild.voice.VoiceChannelCreateEvent;
import org.alienideology.jcord.handle.channel.IChannel;
import org.alienideology.jcord.handle.channel.IGuildChannel;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.Channel;
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
        IChannel.Type type = IChannel.Type.getByKey(json.getInt("type"));

        Channel channel;

        if (type.isPrivate()) {
            if (json.getJSONArray("recipients").length() > 1) {
                channel = builder.buildGroup(json);
                dispatchEvent(new GroupCreateEvent(identity, sequence, channel));
            } else {
                channel = builder.buildPrivateChannel(json);
                dispatchEvent(new PrivateChannelCreateEvent(identity, sequence, channel));
            }
        } else {
            channel = (Channel) builder.buildGuildChannel(json);
            Guild guild = (Guild) ((IGuildChannel) channel).getGuild();
            guild.addGuildChannel((IGuildChannel) channel);
            if (channel.isType(IChannel.Type.GUILD_TEXT)) {
                dispatchEvent(new TextChannelCreateEvent(identity, sequence, channel, guild));
            } else {
                dispatchEvent(new VoiceChannelCreateEvent(identity, sequence, channel, guild));
            }
        }
    }

}
