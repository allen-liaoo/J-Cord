package org.alienideology.jcord.event.client.relation.friend;

import org.alienideology.jcord.event.client.relation.RelationshipRemoveEvent;
import org.alienideology.jcord.handle.client.relation.IFriend;
import org.alienideology.jcord.internal.object.client.Client;

/**
 * @author AlienIdeology
 */
public class FriendRemoveEvent extends RelationshipRemoveEvent {

    public FriendRemoveEvent(Client client, int sequence, IFriend friend) {
        super(client, sequence, friend);
    }

    public IFriend getFriend() {
        return (IFriend) getRelationship();
    }

}
