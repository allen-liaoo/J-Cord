package org.alienideology.jcord.event.client.relation;

import org.alienideology.jcord.event.client.ClientEvent;
import org.alienideology.jcord.handle.client.IClient;
import org.alienideology.jcord.handle.client.relation.IRelationship;
import org.alienideology.jcord.handle.user.IUser;

/**
 * @author AlienIdeology
 */
public class RelationshipEvent extends ClientEvent {

    private final IRelationship relationship;

    public RelationshipEvent(IClient client, int sequence, IRelationship relationship) {
        super(client, sequence);
        this.relationship = relationship;
    }

    public IRelationship getRelationship() {
        return relationship;
    }

    public IUser getUser() {
        return relationship.getUser();
    }

}
