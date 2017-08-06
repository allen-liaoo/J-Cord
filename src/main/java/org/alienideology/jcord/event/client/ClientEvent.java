package org.alienideology.jcord.event.client;

import org.alienideology.jcord.event.Event;
import org.alienideology.jcord.handle.client.IClient;
import org.alienideology.jcord.handle.client.IClientObject;

/**
 * @author AlienIdeology
 */
public class ClientEvent extends Event implements IClientObject {

    private final IClient client;

    public ClientEvent(IClient client, int sequence) {
        super(client.getIdentity(), sequence);
        this.client = client;
    }

    @Override
    public IClient getClient() {
        return client;
    }

}
