package org.alienideology.jcord.event.client.relation;

import org.alienideology.jcord.handle.client.IClient;
import org.alienideology.jcord.handle.client.relation.IRelationship;

/**
 * @author AlienIdeology
 */
public class RelationshipAddEvent extends RelationshipEvent {

    public RelationshipAddEvent(IClient client, int sequence, IRelationship relationship) {
        super(client, sequence, relationship);
    }

}
