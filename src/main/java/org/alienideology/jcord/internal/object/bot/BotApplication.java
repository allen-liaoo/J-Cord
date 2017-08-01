package org.alienideology.jcord.internal.object.bot;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.bot.IBotApplication;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.user.User;

import java.util.List;

/**
 * @author AlienIdeology
 */
public class BotApplication implements IBotApplication {

    private final IdentityImpl identity;

    private final String id;
    private final String name;
    private final String icon;
    private final String description;

    private final User owner;
    private List<String> rpcOrigins;

    private final boolean isPublicBot;
    private final boolean requireCodeGrant;

    public BotApplication(IdentityImpl identity, String id, String name, String icon, String description, User owner,
                          List<String> rpcOrigins, boolean isPublicBot, boolean requireCodeGrant) {
        this.identity = identity;
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.description = description;
        this.owner = owner;
        this.rpcOrigins = rpcOrigins;
        this.isPublicBot = isPublicBot;
        this.requireCodeGrant = requireCodeGrant;
    }

    @Override
    public Identity getIdentity() {
        return identity;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getIconHash() {
        return icon;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public IUser getOwner() {
        return owner;
    }

    @Override
    public List<String> getRpcOrigins() {
        return rpcOrigins;
    }

    @Override
    public boolean isPublicBot() {
        return isPublicBot;
    }

    @Override
    public boolean requireCodeGrant() {
        return requireCodeGrant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BotApplication)) return false;

        BotApplication that = (BotApplication) o;

        if (!id.equals(that.id)) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return owner != null ? owner.equals(that.owner) : that.owner == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BotApplication{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", owner=" + owner +
                '}';
    }
}
