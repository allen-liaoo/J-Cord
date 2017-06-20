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

    /**
     * Get the code of this invite.
     * The invite code is usually 6 to 7 letters long, with alphabets and numbers.
     *
     * @return The invite code.
     */
    String getCode();

    /**
     * Get the guild this invite blongs to.
     *
     * @return The guild.
     */
    Guild getGuild();

    /**
     * Get the {@link IGuildChannel} this invite leads to.
     *
     * @return The channel.
     */
    IGuildChannel getChannel();

    /**
     * Get the author of this invite.
     *
     * @return The inviter.
     */
    User getInviter();

    /**
     * Get the number of uses this invite has.
     *
     * @return The uses.
     */
    int getUses();

    /**
     * Get the max number of uses this invite can have before expiry.
     *
     * @return The max uses.
     */
    int getMaxUses();

    /**
     * Get the max duration in seconds this invite can last befor expiry.
     *
     * @return The max age.
     */
    long getMaxAge();

    /**
     * CHeck if this invite only granted temporary membership.
     * Temporary members are automatically kicked when they disconnected, unless a role has been assigned.
     *
     * @return True of this invite granted temporary membership.
     */
    boolean isTemporary();

    /**
     * Check if this invite is revoked.
     *
     * @return True if the invite is revoked.
     */
    boolean isRevoked();

    /**
     * Get the timestamp when this invite was made.
     *
     * @return The timestamp.
     */
    OffsetDateTime getTimeStamp();
    
}
