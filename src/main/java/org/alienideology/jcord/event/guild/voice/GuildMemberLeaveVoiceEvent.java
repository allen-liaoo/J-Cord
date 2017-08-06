package org.alienideology.jcord.event.guild.voice;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.channel.IVoiceChannel;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IMember;

/**
 * @author AlienIdeology
 */
public class GuildMemberLeaveVoiceEvent extends GuildMemberVoiceEvent {

    private final IVoiceChannel channelLeft;

    public GuildMemberLeaveVoiceEvent(Identity identity, int sequence, IGuild guild, IMember member, IVoiceChannel channelLeft) {
        super(identity, sequence, guild, member);
        this.channelLeft = channelLeft;
    }

    public IVoiceChannel getChannelLeft() {
        return channelLeft;
    }

}
