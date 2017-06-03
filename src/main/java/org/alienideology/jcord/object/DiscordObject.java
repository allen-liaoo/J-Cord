package org.alienideology.jcord.object;

import org.alienideology.jcord.Identity;

/**
 * @author liaoyilin
 */
public class DiscordObject {

    private final Identity identity;

    public DiscordObject(Identity identity) {
        this.identity = identity;
    }

    public Identity getIdentity() {
        return identity;
    }
}
