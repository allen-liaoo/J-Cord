package org.alienideology.jcord.internal.object;

import org.alienideology.jcord.internal.Identity;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DiscordObject)) return false;

        DiscordObject that = (DiscordObject) o;

        return identity != null ? identity.equals(that.identity) : that.identity == null;
    }

    @Override
    public int hashCode() {
        return identity != null ? identity.hashCode() : 0;
    }
}
