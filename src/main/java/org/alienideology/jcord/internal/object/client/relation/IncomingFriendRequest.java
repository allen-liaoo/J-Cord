package org.alienideology.jcord.internal.object.client.relation;

import org.alienideology.jcord.handle.client.relation.IIncomingFriendRequest;
import org.alienideology.jcord.internal.object.client.ClientObject;

/**
 * @author AlienIdeology
 */
public class IncomingFriendRequest extends ClientObject implements IIncomingFriendRequest {

    private Relationship relationship;

    public IncomingFriendRequest(Relationship relationship) {
        super(relationship.getClient());
        this.relationship = relationship;
    }

    @Override
    public Relationship getRelationship() {
        return relationship;
    }

}
