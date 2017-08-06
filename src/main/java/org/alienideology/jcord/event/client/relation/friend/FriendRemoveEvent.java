package org.alienideology.jcord.event.client.relation.friend;

import org.alienideology.jcord.event.client.relation.RelationshipRemoveEvent;
import org.alienideology.jcord.handle.client.IClient;
import org.alienideology.jcord.handle.client.relation.IFriend;

/**
 * @author AlienIdeology
 */
public class FriendRemoveEvent extends RelationshipRemoveEvent {

    public FriendRemoveEvent(IClient client, int sequence, IFriend friend) {
        super(client, sequence, friend);
    }

    public IFriend getFriend() {
        return (IFriend) getRelationship();
    }

}
