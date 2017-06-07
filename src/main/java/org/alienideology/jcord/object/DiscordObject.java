package org.alienideology.jcord.object;

import org.alienideology.jcord.Identity;

/**
 * Generic Discord Objects
 * Such as Guild, User, Channel
 * @author AlienIdeology
 */
public class DiscordObject {

    protected final Identity identity;

    protected DiscordObject(Identity identity) {
        this.identity = identity;
    }

    public Identity getIdentity() {
        return identity;
    }
}
