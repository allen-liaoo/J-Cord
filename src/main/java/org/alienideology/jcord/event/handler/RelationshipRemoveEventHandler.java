package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.event.client.relation.block.BlockedUserRemoveEvent;
import org.alienideology.jcord.event.client.relation.friend.FriendRemoveEvent;
import org.alienideology.jcord.event.client.relation.request.FriendRequestCancelEvent;
import org.alienideology.jcord.event.client.relation.request.FriendRequestIgnoreEvent;
import org.alienideology.jcord.handle.client.relation.IBlockedUser;
import org.alienideology.jcord.handle.client.relation.IFriend;
import org.alienideology.jcord.handle.client.relation.IRelationship;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.client.Client;
import org.alienideology.jcord.internal.object.client.relation.Relationship;
import org.alienideology.jcord.util.log.LogLevel;
import org.json.JSONObject;

/**
 * @author AlienIdeology
 */
public class RelationshipRemoveEventHandler extends EventHandler {

    public RelationshipRemoveEventHandler(IdentityImpl identity) {
        super(identity);
    }

    @Override
    public void dispatchEvent(JSONObject json, int sequence) {
        String id = json.getString("id");

        IUser user = identity.getUser(id);
        if (user == null) {
            logger.log(LogLevel.FETAL, "[UNKNOWN USER] [RELATIONSHIP_REMOVE_EVENT] ID: " + id);
            return;
        }

        IRelationship relationship = identity.getClient().getRelationship(id);
        if (relationship == null) {
            logger.log(LogLevel.FETAL, "[UNKNOWN RELATIONSHIP] [RELATIONSHIP_REMOVE_EVENT] ID: " + id);
            return;
        }

        Client client = identity.getClient();

        IRelationship.Type type = IRelationship.Type.getByKey(json.getInt("type"));
        ((Relationship) relationship).setType(type);
        switch (type) {
            case NONE:
                break;
            case FRIEND: {
                dispatchEvent(new FriendRemoveEvent(client, sequence, (IFriend) relationship));
                break;
            }
            case BLOCK: {
                dispatchEvent(new BlockedUserRemoveEvent(client, sequence, (IBlockedUser) relationship));
                break;
            }
            case FRIEND_REQUEST_IN: {
                dispatchEvent(new FriendRequestIgnoreEvent(client, sequence, relationship));
                break;
            }
            case FRIEND_REQUEST_OUT: {
                dispatchEvent(new FriendRequestCancelEvent(client, sequence, relationship));
                break;
            }
            default: {
                logger.log(LogLevel.FETAL, "[UNKNOWN RELATIONSHIP TYPE] Json: \n" + json.toString(4));
            }
        }

    }

}
