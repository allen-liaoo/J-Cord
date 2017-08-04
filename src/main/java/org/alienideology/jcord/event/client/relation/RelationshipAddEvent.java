package org.alienideology.jcord.event.client.relation;

import org.alienideology.jcord.handle.client.relation.IRelationship;
import org.alienideology.jcord.internal.object.client.Client;

/**
 * @author AlienIdeology
 */
public class RelationshipAddEvent extends RelationshipEvent {

    public RelationshipAddEvent(Client client, int sequence, IRelationship relationship) {
        super(client, sequence, relationship);
    }

}
