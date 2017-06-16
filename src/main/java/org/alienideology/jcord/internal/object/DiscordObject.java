package org.alienideology.jcord.internal.object;

import org.alienideology.jcord.handle.IDiscordObject;

/**
 * Generic Discord Objects
 * Such as Guild, User, Channel
 * @author AlienIdeology
 */
public class DiscordObject implements IDiscordObject {

    protected final IdentityImpl identity;

    protected DiscordObject(IdentityImpl identity) {
        this.identity = identity;
    }

    @Override
    public org.alienideology.jcord.Identity getIdentity() {
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
