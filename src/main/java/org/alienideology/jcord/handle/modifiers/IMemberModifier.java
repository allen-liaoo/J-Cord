package org.alienideology.jcord.handle.modifiers;

import org.alienideology.jcord.handle.audit.AuditAction;
import org.alienideology.jcord.handle.channel.IVoiceChannel;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IMember;
import org.alienideology.jcord.handle.guild.IRole;
import org.alienideology.jcord.internal.rest.ErrorResponse;

import java.util.Collection;

/**
 * IMemberModifier - The modifier that modifies a member.
 *
 * @author AlienIdeology
 */
public interface IMemberModifier extends IModifier<AuditAction<Void>> {

    /**
     * Get the guild this modifier belongs to.
     *
     * @return The guild.
     */
    default IGuild getGuild() {
        return getMember().getGuild();
    }

    /**
     * Get the member this modifier modifies.
     *
     * @return The member.
     */
    IMember getMember();

    /**
     * Modify the member's nickname.
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
     *          <ul>
     *              <li>If the nickname is not valid. See {@link IMember#isValidNickname(String)}</li>
     *              <li>If the member is the identity it self. Use {@link org.alienideology.jcord.handle.managers.IMemberManager#modifyNickname(String)},
     *              which supports modifying self nickname.</li>
     *          </ul>
     *
     * @param nickname The nickname.
     * @return IMemberModifier for chaining.
     */
    IMemberModifier nickname(String nickname);

    /**
     * Modify the member's roles by passing all the roles this member will have.
     * Note that this will override all roles the member once had.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Manage Roles} permission.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If one of the roles does not belong to this guild.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          If the member is the server owner or have higher role than the identity.
     *
     * @param roles The collection of roles.
     * @return IMemberModifier for chaining.
     */
    IMemberModifier roles(Collection<IRole> roles);

    /**
     * Modify, or move, the voice channel this member is connected in.
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
     * @param channel The voice channel.
     * @return IMemberModifier for chaining.
     */
    IMemberModifier voiceChannel(IVoiceChannel channel);

    /**
     * Modify, or move, the voice channel this member is connected in.
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
     * @param channelId The voice channel's id.
     * @return IMemberModifier for chaining.
     */
    default IMemberModifier voiceChannel(String channelId) {
        return voiceChannel(getGuild().getVoiceChannel(channelId));
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
     * @return IMemberModifier for chaining.
     */
    IMemberModifier mute(boolean mute);

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
     * @return IMemberModifier for chaining.
     */
    IMemberModifier deafen(boolean deafen);

    /**
     * Get the nickname attribute, used to modify the member's nickname.
     *
     * @return The nickname attribute.
     */
    Attribute<IMemberModifier, String> getNicknameAttr();

    /**
     * Get the roles attribute, used to modify the member's roles.
     *
     * @return The roles attribute.
     */
    Attribute<IMemberModifier, Collection<IRole>> getRolesAttr();

    /**
     * Get the voice channel attribute, used to move the member to another voice channel.
     *
     * @return The voice channel attribute.
     */
    Attribute<IMemberModifier, IVoiceChannel> getVoiceChannelAttr();

    /**
     * Get the mute attribute, used to mute or unmute the member.
     *
     * @return The mute attribute.
     */
    Attribute<IMemberModifier, Boolean> getMuteAttr();

    /**
     * Get the deafen attribute, used to deafen or undeafen the member.
     *
     * @return The deafen attribute.
     */
    Attribute<IMemberModifier, Boolean> getDeafenAttr();

}
