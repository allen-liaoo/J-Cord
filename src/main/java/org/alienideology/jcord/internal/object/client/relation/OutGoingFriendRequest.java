package org.alienideology.jcord.internal.object.client.relation;

import org.alienideology.jcord.handle.client.relation.IOutGoingFriendRequest;
import org.alienideology.jcord.internal.object.client.ClientObject;

/**
 * @author AlienIdeology
 */
public class OutGoingFriendRequest extends ClientObject implements IOutGoingFriendRequest {

    private Relationship relationship;

    public OutGoingFriendRequest(Relationship relationship) {
        super(relationship.getClient());
        this.relationship = relationship;
    }

    @Override
    public Relationship getRelationship() {
        return relationship;
    }

}
