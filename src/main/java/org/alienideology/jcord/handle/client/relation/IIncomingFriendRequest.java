package org.alienideology.jcord.handle.client.relation;

import org.alienideology.jcord.handle.client.IClientObject;
import org.alienideology.jcord.handle.user.IUser;

/**
 * IIncomingFriendRequest - A friend request from another other user.
 *
 * @author AlienIdeology
 */
public interface IIncomingFriendRequest extends IClientObject {

    /**
     * Get the relationship of this incoming friend request.
     *
     * @return The relationship.
     */
    IRelationship getRelationship();

    /**
     * Accept this friend request, add the user as a friend.
     *
     * @see org.alienideology.jcord.handle.managers.IClientManager#addFriend(IUser)
     */
    default void accept() {
        getClient().getManager().addFriend(getRelationship().getUser());
    }

    /**
     * Ignore this friend request.
     *
     * @see org.alienideology.jcord.handle.managers.IClientManager#removeFriend(IUser)
     */
    default void ignore() {
        getClient().getManager().removeFriend(getRelationship().getUser());
    }

}
