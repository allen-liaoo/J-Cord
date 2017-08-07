package org.alienideology.jcord.handle.managers;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.audit.AuditAction;
import org.alienideology.jcord.handle.channel.IVoiceChannel;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IMember;
import org.alienideology.jcord.handle.guild.IRole;
import org.alienideology.jcord.internal.rest.ErrorResponse;

import java.util.Collection;

/**
 * IMemberManager - The manager that manages and perform actions upon a member.
 * If the action involves multiple members or scopes in the guild level (For bot: kick, ban, unban), then the
 * event can be found in {@link IGuildManager}.
 *
 * @author AlienIdeology
 * @since 0.0.4
 */
public interface IMemberManager {

    /**
     * Get the identity the managers's guild belongs to.
     *
     * @return The identity.
     */
    default Identity getIdentity() {
        return getGuild().getIdentity();
    }

    /**
     * Get the guild the member belongs to.
     *
     *
     * @return The guild.
     */
    IGuild getGuild();

    /**
     * Get the member this managers manages.
     *
     * @return The member.
     */
    IMember getMember();

    /**
     * Modify the nickname of this member.
     * Use empty or null nicknames to reset the nickname.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Change Nickname} permission to modify itself,
     *              or {@code Manage Nicknames} permission to manage other nicknames.
     *              
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException 
     *          If the member is the server owner or have higher role than the identity.
     * 
     * @exception IllegalArgumentException
     *          If the nickname is not valid. See {@link IMember#isValidNickname(String)}
     *
     * @param nickname The new nickname.
     * @return A {@link Void} {@link AuditAction}, used to attach audit log reason.
     */
    AuditAction<Void> modifyNickname(String nickname);

    /**
     * Modify roles of this member. Pass two collections of roles to specify roles to add or remove.
     * Duplicated roles will be ignored.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If one of the roles does not belong to this guild.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the member is the server owner or have higher role than the identity.
     *
     * @param add The roles to add.
     * @param remove The roles to remove.
     * @return A {@link Void} {@link AuditAction}, used to attach audit log reason.
     */
    AuditAction<Void> modifyRoles(Collection<IRole> add, Collection<IRole> remove);

    /**
     * Modify roles of this member by passing all the roles this member will have.
     * Note that this will override all roles the member once had.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If one of the roles does not belong to this guild.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the member is the server owner or have higher role than the identity.
     *
     * @param roles The modified roles.
     * @return A {@link Void} {@link AuditAction}, used to attach audit log reason.
     */
    default AuditAction<Void> modifyRoles(Collection<IRole> roles) {
        return getMember().getModifier().roles(roles).modify();
    }

    /**
     * Add roles to this member.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If one of the roles does not belong to this guild.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the member is the server owner or have higher role than the identity.
     * @exception IllegalArgumentException If the member already had that role.
     *
     * @param roles The role to be added.
     * @return A {@link Void} {@link AuditAction}, used to attach audit log reason.
     */
    AuditAction<Void> addRoles(IRole... roles);

    /**
     * Add roles to this member.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If one of the roles does not belong to this guild.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the member is the server owner or have higher role than the identity.
     * @exception IllegalArgumentException If the member already had that role.
     *
     * @param roles The roles to be added.
     * @return A {@link Void} {@link AuditAction}, used to attach audit log reason.
     */
    AuditAction<Void> addRoles(Collection<IRole> roles);

    /**
     * Remove roles from this member.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If one of the roles does not belong to this guild.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the member is the server owner or have higher role than the identity.
     *
     * @param roles The roles to be removed.
     * @return A {@link Void} {@link AuditAction}, used to attach audit log reason.
     */
    AuditAction<Void> removeRoles(IRole... roles);

    /**
     * Remove roles from this member.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *      <ul>
     *          <li>If the member does not belong to this guild.</li>
     *          <li>If the role does not belong to this guild.</li>
     *      </ul>
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the member is the server owner or have higher role than the identity.
     *
     * @param roles The roles to be removed.
     * @return A {@link Void} {@link AuditAction}, used to attach audit log reason.
     */
    AuditAction<Void> removeRoles(Collection<IRole> roles);

    /**
     * Move this member to a voice channel.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          <ul>
     *              <li>If the identity does not have {@code Move Members} permission.</li>
     *              <li>If the identity does not have {@code Connect} permission to connect to the specific channel.</li>
     *          </ul>
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If the voice channel does not belongs to this guild.
     * @see ErrorResponse#UNKNOWN_CHANNEL
     *
     * @param channel The new channel.
     * @return A {@link Void} {@link AuditAction}, used to attach audit log reason.
     */
    default AuditAction<Void> moveToVoiceChannel(IVoiceChannel channel) {
        return getMember().getModifier().voiceChannel(channel).modify();
    }

    /**
     * Move this member to a voice channel by ID.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          <ul>
     *              <li>If the identity does not have {@code Move Members} permission.</li>
     *              <li>If the identity does not have {@code Connect} permission to connect to the specific channel.</li>
     *          </ul>
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If the voice channel does not belongs to this guild.
     * @see ErrorResponse#UNKNOWN_CHANNEL
     *
     * @param channelId The new channel's ID.
     * @return A {@link Void} {@link AuditAction}, used to attach audit log reason.
     */
    default AuditAction<Void> moveToVoiceChannel(String channelId) {
        return getMember().getModifier().voiceChannel(channelId).modify();
    }

    /**
     * Mute or unmute this member.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the member does not have {@code Mute Members} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the member is the server owner or have higher role than the identity.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If the member does not belong to this guild.
     * @see ErrorResponse#UNKNOWN_MEMBER
     *
     * @param mute True to mute, false to unmute.
     * @return A {@link Void} {@link AuditAction}, used to attach audit log reason.
     */
    default AuditAction<Void> mute(boolean mute) {
        return getMember().getModifier().mute(mute).modify();
    }

    /**
     * Deafen or undeafen this member.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the member does not have {@code Deafen Members} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the member is the server owner or have higher role than the identity.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException If the member does not belong to this guild.
     * @see ErrorResponse#UNKNOWN_MEMBER
     *
     * @param deafen True to deafen the member, false to undeafen.
     * @return A {@link Void} {@link AuditAction}, used to attach audit log reason.
     */
    default AuditAction<Void> deafen(boolean deafen) {
        return getMember().getModifier().deafen(deafen).modify();
    }

}
