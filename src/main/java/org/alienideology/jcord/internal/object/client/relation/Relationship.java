package org.alienideology.jcord.internal.object.client.relation;

import org.alienideology.jcord.handle.client.relation.IRelationship;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.object.client.Client;
import org.alienideology.jcord.internal.object.client.ClientObject;
import org.alienideology.jcord.internal.object.user.User;
import org.jetbrains.annotations.Nullable;

/**
 * @author AlienIdeology
 */
public class Relationship extends ClientObject implements IRelationship {

    private Type type;
    private User user;

    private IncomingFriendRequest incomingFriendRequest;
    private OutGoingFriendRequest outGoingFriendRequest;

    public Relationship(Client client, Type type, User user) {
        super(client);
        this.type = type;
        this.user = user;
        switch (type) {
            case FRIEND_REQUEST_IN: {
                incomingFriendRequest = new IncomingFriendRequest(this);
                break;
            }
            case FRIEND_REQUEST_OUT: {
                outGoingFriendRequest = new OutGoingFriendRequest(this);
                break;
            }
        }
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public IUser getUser() {
        return user;
    }

    @Nullable
    @Override
    public IncomingFriendRequest getIncomingFriendRequest() {
        return incomingFriendRequest;
    }

    @Nullable
    @Override
    public OutGoingFriendRequest getOutGoingFriendRequest() {
        return outGoingFriendRequest;
    }

    public void setType(Type type) {
        this.type = type;
        switch (type) {
            case FRIEND_REQUEST_IN: {
                incomingFriendRequest = new IncomingFriendRequest(this);
                outGoingFriendRequest = null;
                break;
            }
            case FRIEND_REQUEST_OUT: {
                incomingFriendRequest = null;
                outGoingFriendRequest = new OutGoingFriendRequest(this);
                break;
            }
            // Reset to nulls
            default: {
                incomingFriendRequest = null;
                outGoingFriendRequest = null;
                break;
            }
        }
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
                "type=" + type +
                ", user=" + user +
                '}';
    }

}
