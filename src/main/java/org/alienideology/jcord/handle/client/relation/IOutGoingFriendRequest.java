package org.alienideology.jcord.handle.client.relation;

import org.alienideology.jcord.handle.client.IClientObject;
import org.alienideology.jcord.handle.user.IUser;

/**
 * IOutGoingFriendRequest - A friend request from the client to another user.
 *
 * @author AlienIdeology
 */
public interface IOutGoingFriendRequest extends IClientObject {

    /**
     * Get the relationship of this out going friend request.
     *
     * @return The relationship.
     */
    IRelationship getRelationship();

    /**
     * Cancel the friend request sent.
     *
     * @see org.alienideology.jcord.handle.managers.IClientManager#removeFriend(IUser)
     */
    default void cancel() {
        getClient().getManager().removeFriend(getRelationship().getUser());
    }

}
