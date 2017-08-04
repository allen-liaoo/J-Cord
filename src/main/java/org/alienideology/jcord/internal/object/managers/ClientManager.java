package org.alienideology.jcord.internal.object.managers;

import org.alienideology.jcord.handle.channel.ICallChannel;
import org.alienideology.jcord.handle.client.IClient;
import org.alienideology.jcord.handle.client.relation.IRelationship;
import org.alienideology.jcord.handle.managers.IClientManager;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.exception.ErrorResponseException;
import org.alienideology.jcord.internal.exception.HttpErrorException;
import org.alienideology.jcord.internal.gateway.ErrorResponse;
import org.alienideology.jcord.internal.gateway.HttpCode;
import org.alienideology.jcord.internal.gateway.HttpPath;
import org.alienideology.jcord.internal.gateway.Requester;
import org.alienideology.jcord.internal.object.client.Client;
import org.json.JSONObject;

/**
 * @author AlienIdeology
 */
public final class ClientManager implements IClientManager {

    private Client client;

    public ClientManager(Client client) {
        this.client = client;
    }

    @Override
    public IClient getClient() {
        return client;
    }

    @Override
    public void modifyNote(String userId, String note) {
        IUser user = getIdentity().getUser(userId);
        if (user == null) {
            throw new ErrorResponseException(ErrorResponse.UNKNOWN_USER);
        }

        new Requester(getIdentity(), HttpPath.Client.SET_NOTE)
                .request(userId)
                .updateRequestWithBody(request ->
                        request.body(new JSONObject()
                                .put("id", userId)
                                .put("note", note == null ? "" : note)))
                .performRequest();
    }

    @Override
    public void startCall(ICallChannel channel) {
        throw new UnsupportedOperationException();
//        if (channel.getCurrentCall() != null) {
//            throw new IllegalArgumentException("Cannot start a new call when a call is already started!")
//        }
//        new Requester(getIdentity(), HttpPath.Channel.START_CALL)
//                .request(channel.getId())
//                .performRequest();
    }

    @Override
    public void endCall(ICallChannel channel) {
        throw new UnsupportedOperationException();
//                new Requester(getIdentity(), HttpPath.Channel.STOP_CALL)
//                .request(channel.getId())
//                .performRequest();
    }

    @Override
    public void sendFriendRequest(String username, int discriminator) {
        try {
            new Requester(getIdentity(), HttpPath.Client.SEND_FRIEND_REQUEST)
                    .request()
                    .updateRequestWithBody(request ->
                            request.body(new JSONObject().put("username", username).put("discriminator", discriminator)))
                    .performRequest();
        } catch (HttpErrorException ex) {
            if (ex.getCode().equals(HttpCode.NOT_FOUND)) {
                throw new ErrorResponseException(ErrorResponse.UNKNOWN_USER);
            } else {
                throw ex;
            }
        }
    }

    @Override
    public void addFriend(String userId) {
        addRelationship(userId, IRelationship.Type.FRIEND);
    }

    @Override
    public void removeFriend(String userId) {
        removeRelationship(userId, IRelationship.Type.FRIEND);
    }

    @Override
    public void blockUser(String userId) {
        addRelationship(userId, IRelationship.Type.BLOCK);
    }

    @Override
    public void unblockUser(String userId) {
        removeRelationship(userId, IRelationship.Type.BLOCK);
    }

    public void addRelationship(String userId, IRelationship.Type type) {
        try {
            new Requester(getIdentity(), HttpPath.Client.ADD_RELATIONSHIP)
                    .request(userId)
                    .updateRequestWithBody(request -> request.body(new JSONObject().put("type", type.key)))
                    .performRequest();
        } catch (HttpErrorException ex) {
            if (ex.getCode().equals(HttpCode.NOT_FOUND)) {
                throw new ErrorResponseException(ErrorResponse.UNKNOWN_USER);
            } else {
                throw ex;
            }
        }
    }

    public void removeRelationship(String userId, IRelationship.Type type) {
        try {
            new Requester(getIdentity(), HttpPath.Client.DELETE_RELATIONSHIP)
                    .request(userId)
                    .updateRequestWithBody(request -> request.body(new JSONObject().put("type", type.key)))
                    .performRequest();
        } catch (HttpErrorException ex) {
            if (ex.getCode().equals(HttpCode.NOT_FOUND)) {
                throw new ErrorResponseException(ErrorResponse.UNKNOWN_USER);
            } else {
                throw ex;
            }
        }
    }

}
