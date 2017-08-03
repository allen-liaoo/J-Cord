package org.alienideology.jcord.internal.object.managers;

import org.alienideology.jcord.handle.client.IClient;
import org.alienideology.jcord.handle.managers.IClientManager;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.exception.ErrorResponseException;
import org.alienideology.jcord.internal.gateway.ErrorResponse;
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

}
