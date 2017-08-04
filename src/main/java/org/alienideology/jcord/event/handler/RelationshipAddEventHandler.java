package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.event.client.relation.block.BlockedUserAddEvent;
import org.alienideology.jcord.event.client.relation.friend.FriendAddEvent;
import org.alienideology.jcord.event.client.relation.request.FriendRequestReceivedEvent;
import org.alienideology.jcord.event.client.relation.request.FriendRequestSentEvent;
import org.alienideology.jcord.handle.client.relation.IBlockedUser;
import org.alienideology.jcord.handle.client.relation.IFriend;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.ObjectBuilder;
import org.alienideology.jcord.internal.object.client.Client;
import org.alienideology.jcord.internal.object.client.relation.Relationship;
import org.alienideology.jcord.util.log.LogLevel;
import org.json.JSONObject;

/**
 * @author AlienIdeology
 */
public class RelationshipAddEventHandler extends EventHandler {

    public RelationshipAddEventHandler(IdentityImpl identity) {
        super(identity);
    }

    @Override
    public void dispatchEvent(JSONObject json, int sequence) {
        Client client = identity.getClient();

        Relationship relationship = new ObjectBuilder(client).buildRelationship(json);
        client.updateRelationship(relationship);

        switch (relationship.getType()) {
            case NONE:
                break;
            case FRIEND: {
                dispatchEvent(new FriendAddEvent(client, sequence, (IFriend) relationship));
                break;
            }
            case BLOCK: {
                dispatchEvent(new BlockedUserAddEvent(client, sequence, (IBlockedUser) relationship));
                break;
            }
            case FRIEND_REQUEST_IN: {
                dispatchEvent(new FriendRequestReceivedEvent(client, sequence, relationship));
                break;
            }
            case FRIEND_REQUEST_OUT: {
                dispatchEvent(new FriendRequestSentEvent(client, sequence, relationship));
                break;
            }
            case UNKNOWN:
                break;
            default: {
                logger.log(LogLevel.FETAL, "[UNKNOWN RELATIONSHIP TYPE] Json: \n" + json.toString(4));
            }
        }
    }

}
