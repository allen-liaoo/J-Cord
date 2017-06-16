package org.alienideology.jcord.handle.guild;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.internal.object.guild.Guild;

/**
 * IMemberManager - The manager that manages and perform actions upon a member.
 * If the action involves scopes in the guild level (For example: kick, ban, unban), then the
 * method can be found in {@link IGuildManager}.
 *
 * @author AlienIdeology
 */
public interface IMemberManager {

    /**
     * Get the identity the manager's guild belongs to.
     *
     * @return The identity.
     */
    default Identity getIdentity() {
        return getGuild().getIdentity();
    }

    /**
     * Get the guild this member manager manages.
     *
     *
     * @return The guild.
     */
    Guild getGuild();

    /**
     * Modify the nickname of the identity itself.
     * Use empty or null nicknames to reset the nickname.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Change Nickname} permission to modify itself.
     * @exception IllegalArgumentException If the nickname is longer than 32 letters.
     *
     * @param newNickname The new nickname.
     */
    void modifySelfNickname(String newNickname);

    /**
     * Modify the nickname of a specific member.
     * Null member will be ignored.
     * Use empty or null nicknames to reset the nickname.
     * @see #modifySelfNickname(String) For modifying identity nickname in the guild.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Change Nickname} permission to modify itself,
     *              or {@code Manage Nicknames} permission to manage other nicknames,
     *              or managing the server owner's nickname.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException If the member is not found.
     * @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_MEMBER
     * @exception IllegalArgumentException If the nickname is longer than 32 letters.
     *
     * @param member The member.
     * @param newNickname The new nickname.
     */
    void modifyMemberNickname(IMember member, String newNickname);

    /**
     * Modify the nickname of a specific member by ID.
     * Use empty or null nicknames to reset the nickname.
     * @see #modifySelfNickname(String) For modifying identity nickname in the guild.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Change Nickname} permission to modify itself,
     *              or {@code Manage Nicknames} permission to manage other nicknames,
     *              or managing the server owner's nickname.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException If the member is not found.
     * @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_MEMBER
     * @exception IllegalArgumentException If the nickname is longer than 32 letters.
     *
     * @param memberId The member's ID.
     * @param newNickname The new nickname.
     */
    void modifyMemberNickname(String memberId, String newNickname);

    /**
     * Mute a member.
     * Null member will be ignored.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the member does not have {@code Mute Members} permission.
     *              or if the member is server owner.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException If the member is not found.
     * @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_MEMBER
     *
     * @param member The member to be muted.
     */
    void muteMember(IMember member);

    /**
     * Mute a member by ID.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the member does not have {@code Mute Members} permission.
     *              or if the member is server owner.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException If the member is not found.
     * @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_MEMBER
     *
     * @param memberId The member's ID..
     */
    void muteMember(String memberId);

    /**
     * Unmute a member.
     * Null member will be ignored.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the member does not have {@code Mute Members} permission,
     *              or if the member is server owner.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException If the member is not found.
     * @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_MEMBER
     *
     * @param member The member to be unmuted.
     */
    void unmuteMember(IMember member);

    /**
     * Unmute a member.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the member does not have {@code Mute Members} permission.
     *              or if the member is server owner.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException If the member is not found.
     * @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_MEMBER
     *
     * @param memberId The member's ID.
     */
    void unmuteMember(String memberId);

    /**
     * Deafen a member.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the member does not have {@code Deafen Members} permission.
     *              or if the member is server owner.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException If the member is not found.
     * @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_MEMBER
     *
     * @param member The member.
     */
    void deafenMember(IMember member);

    /**
     * Deafen a member.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the member does not have {@code Deafen Members} permission.
     *              or if the member is server owner.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException If the member is not found.
     * @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_MEMBER
     *
     * @param memberId The member's ID.
     */
    void deafenMember(String memberId);

    /**
     * Undeafen a member.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the member does not have {@code Deafen Members} permission.
     *              or if the member is server owner.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException If the member is not found.
     * @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_MEMBER
     *
     * @param member The member.
     */
    void undeafenMember(IMember member);

    /**
     * Undeafen a member.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the member does not have {@code Deafen Members} permission.
     *              or if the member is server owner.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException If the member is not found.
     * @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_MEMBER
     *
     * @param memberId The member's ID.
     */
    void undeafenMember(String memberId);

}
