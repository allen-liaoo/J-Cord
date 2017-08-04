package org.alienideology.jcord.event.client.relation;

import org.alienideology.jcord.event.client.ClientEvent;
import org.alienideology.jcord.handle.client.relation.IRelationship;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.object.client.Client;

/**
 * @author AlienIdeology
 */
public class RelationshipEvent extends ClientEvent {

    private final IRelationship relationship;

    public RelationshipEvent(Client client, int sequence, IRelationship relationship) {
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
