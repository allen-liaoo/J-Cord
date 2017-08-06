package org.alienideology.jcord.event.client.relation.friend;

import org.alienideology.jcord.event.client.relation.RelationshipAddEvent;
import org.alienideology.jcord.handle.client.IClient;
import org.alienideology.jcord.handle.client.relation.IFriend;

/**
 * @author AlienIdeology
 */
public class FriendAddEvent extends RelationshipAddEvent {

    public FriendAddEvent(IClient client, int sequence, IFriend friend) {
        super(client, sequence, friend);
    }

    public IFriend getFriend() {
        return (IFriend) getRelationship();
    }

}
