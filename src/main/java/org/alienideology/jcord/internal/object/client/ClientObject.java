package org.alienideology.jcord.internal.object.client;

import org.alienideology.jcord.handle.client.IClient;
import org.alienideology.jcord.handle.client.IClientObject;
import org.alienideology.jcord.internal.object.DiscordObject;

/**
 * @author AlienIdeology
 */
public class ClientObject extends DiscordObject implements IClientObject {

    private Client client;

    public ClientObject(IClient client) {
        super(client == null ? null : client.getIdentity());
        this.client = (Client) client;
    }

    @Override
    public Client getClient() {
        return client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IClientObject)) return false;
        if (!super.equals(o)) return false;

        IClientObject that = (IClientObject) o;

        return client.equals(that.getClient());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + client.hashCode();
        return result;
    }

}
