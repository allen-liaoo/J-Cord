package org.alienideology.jcord.handle;

import org.alienideology.jcord.handle.channel.IGuildChannel;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.user.User;

import java.time.OffsetDateTime;

/**
 * IInvite - A piece of url used to invite users to a guild.
 *
 * @author AlienIdeology
 */
public interface IInvite {

    String getCode();

    Guild getGuild();

    IGuildChannel getChannel();

    User getInviter();

    int getUses();

    int getMaxUses();

    long getMaxAge();

    boolean isTemporary();

    boolean isRevoked();

    OffsetDateTime getTimeStamp();
    
}
