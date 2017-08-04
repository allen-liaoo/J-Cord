package org.alienideology.jcord.handle.client.relation;

import org.alienideology.jcord.handle.user.IUser;

/**
 * IBlockedUser - An user with the {@link IRelationship.Type#BLOCK} type of relationship.
 *
 * @author AlienIdeology
 */
public interface IBlockedUser extends IRelationship {

    /**
     * Unblock this user.
     *
     * @see org.alienideology.jcord.handle.managers.IClientManager#unblockUser(IUser)
     */
    default void unblock() {
        getClient().getManager().unblockUser(getUser());
    }

}
