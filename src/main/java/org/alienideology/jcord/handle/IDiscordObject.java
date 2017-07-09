package org.alienideology.jcord.handle;

import org.alienideology.jcord.Identity;

/**
 * Generic Discord Objects
 * Such as Guild, User, Channel
 *
 * @author AlienIdeology
 */
public interface IDiscordObject {

    /**
     * Get the identity this object belongs to.
     *
     * @return The identity.
     */
    Identity getIdentity();

}
