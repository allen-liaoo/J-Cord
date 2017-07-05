package org.alienideology.jcord.event.guild.member;

import org.alienideology.jcord.event.guild.GuildEvent;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.user.User;

/**
 * @author AlienIdeology
 */
public class GuildUnbanEvent extends GuildEvent {

    private User unbannedUser;

    public GuildUnbanEvent(IdentityImpl identity, Guild guild, int sequence, User unbannedUser) {
        super(identity, sequence, guild);
        this.unbannedUser = unbannedUser;
    }

    public User getUnbannedUser() {
        return unbannedUser;
    }

}
