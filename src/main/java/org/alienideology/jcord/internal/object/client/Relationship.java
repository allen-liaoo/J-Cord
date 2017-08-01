package org.alienideology.jcord.internal.object.client;

import org.alienideology.jcord.handle.client.IRelationship;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.object.user.User;

/**
 * @author AlienIdeology
 */
public final class Relationship extends ClientObject implements IRelationship {

    private Type type;
    private User user;

    public Relationship(Client client, Type type, User user) {
        super(client);
        this.type = type;
        this.user = user;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public IUser getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Relationship)) return false;

        Relationship that = (Relationship) o;

        if (!identity.equals(that.identity)) return false;
        if (type != that.type) return false;
        return user != null ? user.equals(that.user) : that.user == null;
    }

    @Override
    public int hashCode() {
        int result = identity.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Relationship{" +
                "identity=" + identity +
                ", type=" + type +
                ", user=" + user +
                '}';
    }
}
