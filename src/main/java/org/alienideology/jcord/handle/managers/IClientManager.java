package org.alienideology.jcord.handle.managers;

import org.alienideology.jcord.handle.channel.ICallChannel;
import org.alienideology.jcord.handle.client.IClientObject;
import org.alienideology.jcord.handle.user.IUser;

/**
 * IClientManager - A manager used to manage the Discord client.
 *
 * @author AlienIdeology
 */
public interface IClientManager extends IClientObject {

    /**
     * Modify a note for a user.
     * To remove a note, simple pass empty or {@code null} note as the parameter.
     *
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If the user id provided is not valid.
     * @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_USER
     *
     * @param userId The user to modify note.
     * @param note The note.
     */
    void modifyNote(String userId, String note);

    /**
     * Send a friend request by username and discriminator.
     *
     * @param username The user's name.
     * @param discriminator The discriminator.
     */
    void sendFriendRequest(String username, int discriminator);

    /**
     * There are three types of actions that may be performed by this method,
     * depending on the relationship between the user and the client.
     * The actions are as follows:
     * <ul>
     *     <li>Add an user as a friend.</li>
     *     <li>Accept an incoming friend request.</li>
     *     <li>Send an outgoing friend request.</li>
     * </ul>
     *
     * @param user The user.
     */
    default void addFriend(IUser user) {
        addFriend(user.getId());
    }

    /**
     * Add an user as a friend by id.
     * @see #addFriend(IUser)
     *
     * @param userId The user id.
     */
    void addFriend(String userId);

    /**
     * There are three types of actions that may be performed by this method, depending on
     * the relationship between the user and the client.
     * The actions are as follows:
     * <ul>
     *     <li>Remove a friend.</li>
     *     <li>Ignore an incoming friend request.</li>
     *     <li>Cancel an outgoing friend request that was sent to the user.</li>
     * </ul>
     *
     * @param user The friend.
     */
    default void removeFriend(IUser user) {
        removeFriend(user.getId());
    }

    /**
     * Remove a friend by id.
     * @see #removeFriend(IUser)
     *
     * @param userId The friend's id.
     */
    void removeFriend(String userId);

    /**
     * Block an user.
     *
     * @param user The user.
     */
    default void blockUser(IUser user) {
        blockUser(user.getId());
    }

    /**
     * Block an user by id.
     *
     * @param userId The user id.
     */
    void blockUser(String userId);

    /**
     * Unblock an user.
     *
     * @param user The user.
     */
    default void unblockUser(IUser user) {
        user.getId();
    }

    /**
     * Block an user by id.
     *
     * @param userId The user id.
     */
    void unblockUser(String userId);

    /**
     * Not supported yet.
     *
     * @param user
     */
    default void call(IUser user) {
        startCall(user.getPrivateChannel());
    }

    /**
     * Not supported yet.
     *
     * @param channel
     */
    void startCall(ICallChannel channel);

    /**
     * Not supported yet.
     *
     * @param channel
     */
    void endCall(ICallChannel channel);

}
