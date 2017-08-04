package org.alienideology.jcord.event.client.relation.block;

import org.alienideology.jcord.event.client.relation.RelationshipRemoveEvent;
import org.alienideology.jcord.handle.client.relation.IBlockedUser;
import org.alienideology.jcord.internal.object.client.Client;

/**
 * @author AlienIdeology
 */
public class BlockedUserRemoveEvent extends RelationshipRemoveEvent {

    public BlockedUserRemoveEvent(Client client, int sequence, IBlockedUser user) {
        super(client, sequence, user);
    }

    public IBlockedUser getBlockedUser() {
        return (IBlockedUser) getRelationship();
    }

}
