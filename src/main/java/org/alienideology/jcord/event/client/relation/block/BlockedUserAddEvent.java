package org.alienideology.jcord.event.client.relation.block;

import org.alienideology.jcord.event.client.relation.RelationshipAddEvent;
import org.alienideology.jcord.handle.client.relation.IBlockedUser;
import org.alienideology.jcord.internal.object.client.Client;

/**
 * @author AlienIdeology
 */
public class BlockedUserAddEvent extends RelationshipAddEvent  {

    public BlockedUserAddEvent(Client client, int sequence, IBlockedUser user) {
        super(client, sequence, user);
    }

    public IBlockedUser getBlockedUser() {
        return (IBlockedUser) getRelationship();
    }

}
