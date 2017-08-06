package org.alienideology.jcord.event.client.relation;

import org.alienideology.jcord.handle.client.IClient;
import org.alienideology.jcord.handle.client.relation.IRelationship;

/**
 * @author AlienIdeology
 */
public class RelationshipRemoveEvent extends RelationshipEvent {

    public RelationshipRemoveEvent(IClient client, int sequence, IRelationship relationship) {
        super(client, sequence, relationship);
    }

}
