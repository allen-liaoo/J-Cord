package org.alienideology.jcord.event.client.relation.request;

import org.alienideology.jcord.event.client.relation.RelationshipRemoveEvent;
import org.alienideology.jcord.handle.client.IClient;
import org.alienideology.jcord.handle.client.relation.IOutGoingFriendRequest;
import org.alienideology.jcord.handle.client.relation.IRelationship;

/**
 * @author AlienIdeology
 */
public class FriendRequestCancelEvent extends RelationshipRemoveEvent {

    public FriendRequestCancelEvent(IClient client, int sequence, IRelationship relationship) {
        super(client, sequence, relationship);
    }

    public IOutGoingFriendRequest getOutGoingFriendRequest() {
        return getRelationship().getOutGoingFriendRequest();
    }

}
