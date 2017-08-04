package org.alienideology.jcord.event.client.relation.request;

import org.alienideology.jcord.event.client.relation.RelationshipRemoveEvent;
import org.alienideology.jcord.handle.client.relation.IOutGoingFriendRequest;
import org.alienideology.jcord.handle.client.relation.IRelationship;
import org.alienideology.jcord.internal.object.client.Client;

/**
 * @author AlienIdeology
 */
public class FriendRequestCancelEvent extends RelationshipRemoveEvent {

    public FriendRequestCancelEvent(Client client, int sequence, IRelationship relationship) {
        super(client, sequence, relationship);
    }

    public IOutGoingFriendRequest getOutGoingFriendRequest() {
        return getRelationship().getOutGoingFriendRequest();
    }

}
