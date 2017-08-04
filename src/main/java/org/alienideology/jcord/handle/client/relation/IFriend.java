package org.alienideology.jcord.handle.client.relation;

import org.alienideology.jcord.handle.user.IUser;

/**
 * IFriend - An user with the {@link IRelationship.Type#FRIEND} type of relationship.
 *
 * @author AlienIdeology
 */
public interface IFriend extends IRelationship {

    /**
     * Remove this friend.
     *
     * @see org.alienideology.jcord.handle.managers.IClientManager#removeFriend(IUser)
     */
    default void remove() {
        getClient().getManager().removeFriend(getUser());
    }

}
