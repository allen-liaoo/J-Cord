package org.alienideology.jcord.event.client;

import org.alienideology.jcord.event.Event;
import org.alienideology.jcord.handle.client.IClient;
import org.alienideology.jcord.handle.client.IClientObject;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.client.Client;

/**
 * @author AlienIdeology
 */
public class ClientEvent extends Event implements IClientObject {

    private Client client;

    public ClientEvent(Client client, int sequence) {
        super((IdentityImpl) client.getIdentity(), sequence);
        this.client = client;
    }

    @Override
    public IClient getClient() {
        return client;
    }

}
