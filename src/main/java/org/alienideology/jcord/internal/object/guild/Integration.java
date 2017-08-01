package org.alienideology.jcord.internal.object.guild;

import org.alienideology.jcord.handle.guild.IIntegration;
import org.alienideology.jcord.handle.guild.IRole;
import org.alienideology.jcord.handle.user.IConnection;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.object.DiscordObject;
import org.alienideology.jcord.internal.object.IdentityImpl;

import java.time.OffsetDateTime;

/**
 * @author AlienIdeology
 */
// TODO: Create, Modify, Delete, Sync Integration
public final class Integration extends DiscordObject implements IIntegration {

    private final String id;
    private String name;
    private IConnection.Type type;

    private IUser user;
    private IIntegration.Account account;
    private IRole role;

    private OffsetDateTime lastSynced;

    private boolean enabled;
    private boolean syncing;

    public Integration(IdentityImpl identity, String id, String name, IConnection.Type type,
                       String lastSynced, boolean enabled, boolean syncing) {
        super(identity);
        this.id = id;
        this.name = name;
        this.type = type;
        this.lastSynced = OffsetDateTime.parse(lastSynced);
        this.enabled = enabled;
        this.syncing = syncing;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public IConnection.Type getType() {
        return type;
    }

    @Override
    public IUser getUser() {
        return user;
    }

    @Override
    public Account getAccount() {
        return account;
    }

    @Override
    public IRole getSubscriberRole() {
        return role;
    }

    @Override
    public OffsetDateTime getLastSynced() {
        return lastSynced;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean isSyncing() {
        return syncing;
    }

    @Override
    public String getId() {
        return id;
    }

    //---------------Internal---------------

    public void setUser(IUser user) {
        this.user = user;
    }

    public void setAccount(IIntegration.Account account) {
        this.account = account;
    }

    public void setRole(IRole role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Integration)) return false;

        Integration that = (Integration) o;

        if (!id.equals(that.id)) return false;
        if (type != that.type) return false;
        return lastSynced != null ? lastSynced.equals(that.lastSynced) : that.lastSynced == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + (lastSynced != null ? lastSynced.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Integration{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", lastSynced=" + lastSynced +
                '}';
    }

}
