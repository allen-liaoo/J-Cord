package org.alienideology.jcord.handle.client;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.IDiscordObject;

/**
 * IClientObject - Generic Client Objects.
 *
 * @author AlienIdeology
 */
public interface IClientObject extends IDiscordObject {

    @Override
    default Identity getIdentity() {
        return getClient().getIdentity();
    }

    /**
     * Get the client this object belongs to.
     *
     * @return The client instance.
     */
    IClient getClient();

}
