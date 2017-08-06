package org.alienideology.jcord.internal.object.managers;

import org.alienideology.jcord.handle.channel.ICallChannel;
import org.alienideology.jcord.handle.channel.IGroup;
import org.alienideology.jcord.handle.client.IClient;
import org.alienideology.jcord.handle.client.app.IApplication;
import org.alienideology.jcord.handle.client.app.IAuthApplication;
import org.alienideology.jcord.handle.client.relation.IFriend;
import org.alienideology.jcord.handle.client.relation.IRelationship;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.managers.IClientManager;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.exception.ErrorResponseException;
import org.alienideology.jcord.internal.exception.HttpErrorException;
import org.alienideology.jcord.internal.object.ObjectBuilder;
import org.alienideology.jcord.internal.object.client.Client;
import org.alienideology.jcord.internal.object.client.app.Application;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.rest.ErrorResponse;
import org.alienideology.jcord.internal.rest.HttpCode;
import org.alienideology.jcord.internal.rest.HttpPath;
import org.alienideology.jcord.internal.rest.Requester;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collection;
import java.util.stream.Collectors;

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
    public IGuild createGuild(IGuild guild) {
        JSONObject json = new Requester(getIdentity(), HttpPath.Guild.CREATE_GUILD)
                .request()
                .updateRequestWithBody(request -> request.body(((Guild) guild).toJson()))
                .getAsJSONObject();
        return new ObjectBuilder(getIdentity()).buildGuild(json);
    }

    @Override
    public void deleteGuild(IGuild guild) {
        try {
            new Requester(getIdentity(), HttpPath.Guild.DELETE_GUILD)
                    .request(guild.getId())
                    .performRequest();
        } catch (HttpErrorException ex) {
            if (ex.getCode().equals(HttpCode.NOT_FOUND)) {
                throw new ErrorResponseException(ErrorResponse.UNKNOWN_GUILD);
            } else {
                throw ex;
            }
        }
    }

    @Override
    public IGroup createGroup(Collection<IFriend> friends) {
        return createGroup(friends.stream().map(f -> f.getUser().getId()).collect(Collectors.toList()).toArray(new String[friends.size()]));
    }

    // TODO: How to set name and icon??
    @Override
    public IGroup createGroup(String... friendsIds) {
        JSONArray array = new JSONArray();
        for (String id : friendsIds) {
            array.put(id);
        }

        JSONObject json = new Requester(getIdentity(), HttpPath.User.CREATE_DM)
                .request()
                .updateRequestWithBody(request -> request.body(new JSONObject().put("recipients", array)))
                .getAsJSONObject();
        return new ObjectBuilder(getClient()).buildGroup(json);
    }

    @Override
    public void leaveGroup(IGroup group) {
        new Requester(getIdentity(), HttpPath.Channel.DELETE_CHANNEL)
                .request(group.getId())
                .performRequest();
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

    @Override
    public IApplication createApplication(IApplication application) {

        JSONObject json = new Requester(getIdentity(), HttpPath.Application.CREATE_APPLICATION)
                .request()
                .updateRequestWithBody(request -> request.body(((Application) application).toJson()))
                .getAsJSONObject();

        return new ObjectBuilder(client).buildApplication(json);
    }

    @Override
    public void deleteApplication(IApplication application) {
        try {
            new Requester(getIdentity(), HttpPath.Application.DELETE_APPLICATION)
                    .request(application.getId())
                    .performRequest();
        } catch (HttpErrorException ex) {
            if (ex.getCode().equals(HttpCode.NOT_FOUND)) {
                throw new ErrorResponseException(ErrorResponse.UNKNOWN_APPLICATION);
            } else {
                throw ex;
            }
        }
    }

    @Override
    public void removeAuthApplication(IAuthApplication authApplication) {
        try {
            new Requester(getIdentity(), HttpPath.Application.DELETE_AUTHORIZED_APPLICATION)
                    .request(authApplication.getAuthorizeId())
                    .performRequest();
        } catch (HttpErrorException ex) {
            if (ex.getCode().equals(HttpCode.NOT_FOUND)) {
                throw new ErrorResponseException(ErrorResponse.UNKNOWN_APPLICATION);
            } else {
                throw ex;
            }
        }
    }

}
