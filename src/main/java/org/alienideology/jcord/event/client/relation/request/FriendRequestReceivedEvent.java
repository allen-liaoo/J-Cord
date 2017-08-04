package org.alienideology.jcord.event.client.relation.request;

import org.alienideology.jcord.event.client.relation.RelationshipAddEvent;
import org.alienideology.jcord.handle.client.relation.IIncomingFriendRequest;
import org.alienideology.jcord.handle.client.relation.IRelationship;
import org.alienideology.jcord.internal.object.client.Client;

/**
 * @author AlienIdeology
 */
public class FriendRequestReceivedEvent extends RelationshipAddEvent {

    public FriendRequestReceivedEvent(Client client, int sequence, IRelationship relationship) {
        super(client, sequence, relationship);
    }

    public IIncomingFriendRequest getIncomingFriendRequest() {
        return getRelationship().getIncomingFriendRequest();
    }

}
