package org.alienideology.jcord.event.client.relation.request;

import org.alienideology.jcord.event.client.relation.RelationshipRemoveEvent;
import org.alienideology.jcord.handle.client.IClient;
import org.alienideology.jcord.handle.client.relation.IIncomingFriendRequest;
import org.alienideology.jcord.handle.client.relation.IRelationship;

/**
 * @author AlienIdeology
 */
public class FriendRequestIgnoreEvent extends RelationshipRemoveEvent {

    public FriendRequestIgnoreEvent(IClient client, int sequence, IRelationship relationship) {
        super(client, sequence, relationship);
    }

    public IIncomingFriendRequest getIncomingFriendRequest() {
        return getRelationship().getIncomingFriendRequest();
    }

}
