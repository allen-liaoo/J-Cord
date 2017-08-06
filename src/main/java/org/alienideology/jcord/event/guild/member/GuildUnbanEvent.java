package org.alienideology.jcord.event.guild.member;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.guild.GuildEvent;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.user.IUser;

/**
 * @author AlienIdeology
 */
public class GuildUnbanEvent extends GuildEvent {

    private IUser unbannedUser;

    public GuildUnbanEvent(Identity identity, IGuild guild, int sequence, IUser unbannedUser) {
        super(identity, sequence, guild);
        this.unbannedUser = unbannedUser;
    }

    public IUser getUnbannedUser() {
        return unbannedUser;
    }

}
