package org.alienideology.jcord.event.client.relation.friend;

import org.alienideology.jcord.event.client.relation.RelationshipAddEvent;
import org.alienideology.jcord.handle.client.relation.IFriend;
import org.alienideology.jcord.internal.object.client.Client;

/**
 * @author AlienIdeology
 */
public class FriendAddEvent extends RelationshipAddEvent {

    public FriendAddEvent(Client client, int sequence, IFriend friend) {
        super(client, sequence, friend);
    }

    public IFriend getFriend() {
        return (IFriend) getRelationship();
    }

}
