package org.alienideology.jcord.internal.object.client;

import org.alienideology.jcord.handle.client.IClientObject;
import org.alienideology.jcord.internal.object.DiscordObject;
import org.alienideology.jcord.internal.object.IdentityImpl;

/**
 * @author AlienIdeology
 */
public class ClientObject extends DiscordObject implements IClientObject {

    private Client client;

    public ClientObject(Client client) {
        super((IdentityImpl) client.getIdentity());
        this.client = client;
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
