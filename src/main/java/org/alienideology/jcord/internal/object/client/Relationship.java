package org.alienideology.jcord.internal.object.client;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.client.IRelationship;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.user.User;

/**
 * @author AlienIdeology
 */
public class Relationship implements IRelationship {

    private IdentityImpl identity;
    private Type type;
    private User user;

    public Relationship(IdentityImpl identity, Type type, User user) {
        this.identity = identity;
        this.type = type;
        this.user = user;
    }

    @Override
    public Identity getIdentity() {
        return identity;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public IUser getUser() {
        return user;
    }
}
