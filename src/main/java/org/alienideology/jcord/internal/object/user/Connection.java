package org.alienideology.jcord.internal.object.user;

import org.alienideology.jcord.handle.guild.IIntegration;
import org.alienideology.jcord.handle.user.IConnection;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.object.client.Client;
import org.alienideology.jcord.internal.object.client.ClientObject;

import java.util.List;

/**
 * @author AlienIdeology
 */
public final class Connection extends ClientObject implements IConnection {

    private String id;

    private IUser user;
    private String name;
    private IConnection.Type type;

    private boolean displayOnProfile;
    private boolean friendSyncEnabled;
    private boolean verified;
    private boolean revoked;

    private final List<IIntegration> integrations;

    public Connection(Client client, String id, IUser user, String name, IConnection.Type type, boolean displayOnProfile, boolean friendSyncEnabled,
                      boolean verified, boolean revoked, List<IIntegration> integrations) {
        super(client);
        this.id = id;
        this.user = user;
        this.name = name;
        this.type = type;
        this.displayOnProfile = displayOnProfile;
        this.friendSyncEnabled = friendSyncEnabled;
        this.verified = verified;
        this.revoked = revoked;
        this.integrations = integrations;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public IUser getUser() {
        return user;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public boolean displayOnProfile() {
        return displayOnProfile;
    }

    @Override
    public boolean isFriendSyncEnabled() {
        return friendSyncEnabled;
    }

    @Override
    public boolean isVerified() {
        return verified;
    }

    @Override
    public boolean isRevoked() {
        return revoked;
    }

    @Override
    public List<IIntegration> getIntegrations() {
        return integrations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Connection)) return false;
        if (!super.equals(o)) return false;

        Connection that = (Connection) o;

        if (!id.equals(that.id)) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + type.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Connection{" +
                "id='" + id + '\'' +
                ", user=" + user +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
