package org.alienideology.jcord.handle.managers;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.IInvite;
import org.alienideology.jcord.handle.channel.IGuildChannel;
import org.alienideology.jcord.handle.channel.ITextChannel;
import org.alienideology.jcord.handle.channel.IVoiceChannel;
import org.alienideology.jcord.handle.guild.IGuild;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * IInviteManager - A manager for managing invites in both a guild or a guild channel.
 *
 * @author AlienIdeology
 */
public interface IInviteManager {

    /**
     * Get the identity this invite managers belongs to.
     *
     * @return The identity.
     */
    Identity getIdentity();

    /**
     * Get the guild this invite managers manages invites from.
     *
     * @return The guild.
     */
    IGuild getGuild();

    /**
     * Get the channel this invites managers manages.
     *
     * @return The channel. Can be {@link ITextChannel} or {@link IVoiceChannel}.
     */
    IGuildChannel getGuildChannel();

    /**
     * Get the invites of a guild.
     * This is equivalent to getting the invites of a default channel of a guild.
     * Channel specific invites are not included in this list.
     *
     * It is recommended to cache the invites.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manager Server} permission.
     *
     * @return The guild invites.
     */
    List<IInvite> getGuildInvites();

    /**
     * Get the invites of a channel.
     * If this invite manager is under a guild, this will be getting the default channel's invites of that guild.
     * @see IGuild#getDefaultChannel()
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manager Channels} permission in that channel.
     *
     * @return The channel invites.
     */
    List<IInvite> getChannelInvites();

    /**
     * Get an invite by code.
     * This method invokes {@link #getGuildInvites()} or {@link #getChannelInvites()} to search for a matched code.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Create Instant Invite} permission.
     *
     * @param code The invite code.
     * @return The invite, or null if no invite is found.
     */
    @Nullable
    IInvite getInvite(String code);

    /**
     * Create a new invite for the guild channel.
     * If this manager is under a guild, the invite will be created under the default channel.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Create Instant Invite} permission.
     *
     * @param maxUses Max number of uses of the invite. (0 for unlimited uses)
     * @param maxAge Max durations in second before expiry (0 for unlimited age)
     * @param isTemporary True if this invite only grant temporarily membership.
     * @param isUnique True for creating unique one time use invites.
     * @return The invite created.
     */
    IInvite createInvite(int maxUses, long maxAge, boolean isTemporary, boolean isUnique);

    /**
     * Deletes an existing invite.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manager Channels} permission.
     *
     * @param invite The invite.
     */
    void deleteInvite(IInvite invite);

    /**
     * Deletes an existing invite by code.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manager Channels} permission.
     *
     * @param code The invite code.
     */
    void deleteInvite(String code);

}
