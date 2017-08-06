package org.alienideology.jcord.event.client.relation.block;

import org.alienideology.jcord.event.client.relation.RelationshipAddEvent;
import org.alienideology.jcord.handle.client.IClient;
import org.alienideology.jcord.handle.client.relation.IBlockedUser;

/**
 * @author AlienIdeology
 */
public class BlockedUserAddEvent extends RelationshipAddEvent  {

    public BlockedUserAddEvent(IClient client, int sequence, IBlockedUser user) {
        super(client, sequence, user);
    }

    public IBlockedUser getBlockedUser() {
        return (IBlockedUser) getRelationship();
    }

}
