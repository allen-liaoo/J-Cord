package org.alienideology.jcord.handle.guild;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.Region;
import org.alienideology.jcord.handle.channel.IVoiceChannel;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.exception.HttpErrorException;
import org.alienideology.jcord.internal.object.guild.Guild;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * IGuildManager - The manager that manages and perform actions upon a guild.
 * @author AlienIdeology
 */
public interface IGuildManager {

    /**
     * Get the identity the guild belongs to.
     *
     * @return The identity.
     */
    default Identity getIdentity() {
        return getGuild().getIdentity();
    }

    /**
     * Get the guild this manager manages.
     *
     *
     * @return The guild.
     */
    Guild getGuild();

    /**
     * Modify the guild's name.
     * Null or empty {@code name} will be ignored.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Manager Server} or {@code Administrator} permission.
     *
     * @param name The string name.
     */
    void modifyName(String name);

    /**
     * Modify the guild's owner. The identity must be the guild owner originally in order to modify the owner.
     * This is human only, since bots can not be a guild owner.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Manager Server} or {@code Administrator} permission.
     *
     * @param newOwner The new owner.
     */
    void modifyOwner(IMember newOwner);

    /**
     * Modify the guild's region.
     * {@link Region#UNKNOWN} will be ignored.
     *
     * @exception HttpErrorException
     *          if the region is vip, but the guild is not. See {@link Region#isVIP}
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Manager Server} or {@code Administrator} permission.
     *
     * @param region The region enumeration.
     */
    void modifyRegion(Region region);

    /**
     * Modify the guild's verification level.
     * {@link org.alienideology.jcord.handle.guild.IGuild.Verification#UNKNOWN} will be ignored.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Manager Server} or {@code Administrator} permission.
     *
     * @param verification The verification enumeration.
     */
    void modifyVerification(IGuild.Verification verification);

    /**
     * Modify the guild's notification level.
     * {@link org.alienideology.jcord.handle.guild.IGuild.Notification#UNKNOWN} will be ignored.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Manager Server} or {@code Administrator} permission.
     *
     * @param notification The notification enumeration.
     */
    void modifyNotification(IGuild.Notification notification);

    /**
     * Modify the guild's afk timeout.
     * {@link org.alienideology.jcord.handle.guild.IGuild.AFKTimeout#UNKNOWN} will be ignored.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Manager Server} or {@code Administrator} permission.
     *
     * @param afkTimeout The afk timeout.
     */
    void modifyAFKTimeout(IGuild.AFKTimeout afkTimeout);

    /**
     * Modify the guild's afk channel.
     * Null channel will be ignored.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Manager Server} or {@code Administrator} permission.
     *
     * @param afkChannel The afk channel.
     */
    void modifyAFKChannel(IVoiceChannel afkChannel);

    /**
     * Modify the guild's afk channel by ID.
     * Null or empty ID will be ignored.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Manager Server} or {@code Administrator} permission.
     *
     * @param afkChannelId The afk channel ID.
     */
    void modifyAFKChannel(String afkChannelId);

    /**
     * Modify the guild's icon.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Manager Server} or {@code Administrator} permission.
     * @throws IOException When decoding image.
     *
     * @param image The image file.
     */
    void modifyIcon(BufferedImage image) throws IOException;

    /**
     * Modify the guild's icon.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Manager Server} or {@code Administrator} permission.
     * @throws IOException When decoding image.
     *
     * @param imagePath The image file path.
     */
    void modifyIcon(String imagePath) throws IOException;

    /**
     * Modify the guild's splash icon. This is VIP guild only.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Manager Server} or {@code Administrator} permission.
     * @throws IOException When decoding image.
     *
     * @param image The image file.
     */
    void modifySplash(BufferedImage image) throws IOException;

    /**
     * Modify the guild's splash icon. This is VIP guild only.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Manager Server} or {@code Administrator} permission.
     * @throws IOException When decoding image.
     *
     * @param imagePath The image file path.
     */
    void modifySplash(String imagePath) throws IOException;

    /**
     * Kick a member.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Kick Members} permission.
     * @exception IllegalArgumentException
     *          If the identity tries to kick itself from the guild.
     *          Please use {leave()} to leave a guild.
     *
     * @param member The member.
     * @return True if the member is kicked successfully.
     */
    boolean kickMember(IMember member);

    /**
     * Kick a member by ID.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Kick Members} permission.
     * @exception IllegalArgumentException
     *          If the identity tries to kick itself from the guild.
     *          Please use {leave()} to leave a guild.
     *
     * @param memberId The member's ID.
     * @return True if the member is kicked successfully.
     */
    boolean kickMember(String memberId);

    /**
     * Ban a member.
     * The number of days to delete messages is 7 by default.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Ban Member} or {@code Administrator} permission.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException If the member is not found.
     * @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_MEMBER
     *
     * @param member The member.
     * @return True if the member is banned successfully.
     */
    boolean banMember(IMember member);

    /**
     * Ban a member.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Ban Member} or {@code Administrator} permission.
     * @exception IllegalArgumentException If the days are smaller than 0 or greater than 7.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException If the member is not found.
     * @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_MEMBER
     *
     * @param member The member.
     * @param days The number of days to delete the member's message. Only valid between 0 and 7.
     * @return True if the member is banned successfully.
     */
    boolean banMember(IMember member, int days);

    /**
     * Ban a member by ID.
     * The number of days to delete messages is 7 by default.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Ban Member} or {@code Administrator} permission.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException If the member is not found.
     * @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_MEMBER
     *
     * @param memberId The member's ID.
     * @return True if the member is banned successfully.
     */
    boolean banMember(String memberId);

    /**
     * Ban a member by ID.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Ban Member} or {@code Administrator} permission.
     * @exception IllegalArgumentException If the days are smaller than 0 or greater than 7.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException If the member is not found.
     * @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_MEMBER
     *
     * @param memberId The member's ID.
     * @param days The number of days to delete the member's message. Only valid between 0 and 7.
     * @return True if the member is banned successfully.
     */
    boolean banMember(String memberId, int days);

    /**
     * Unban a user.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Ban Member} or {@code Administrator} permission.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException If the user is not found.
     * @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_USER
     *
     * @param user The user.
     * @return True if the member is unbanned successfully.
     */
    boolean unbanUser(IUser user);

    /**
     * Unban a member by ID.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have either {@code Ban Member} or {@code Administrator} permission.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException If the user is not found.
     * @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_USER
     *
     * @param memberId The user's ID.
     * @return True if the user is unbanned successfully.
     */
    boolean unbanUser(String memberId);

    /**
     * Modify the nickname of a specific member.
     * Null member will be ignored.
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
    void unDeafenMember(IMember member);

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
    void unDeafenMember(String memberId);

}