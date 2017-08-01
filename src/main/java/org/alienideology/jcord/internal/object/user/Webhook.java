package org.alienideology.jcord.internal.object.user;

import org.alienideology.jcord.handle.channel.ITextChannel;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.managers.IWebhookManager;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.handle.user.IWebhook;
import org.alienideology.jcord.internal.object.DiscordObject;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.managers.WebhookManager;

/**
 * @author AlienIdeology
 */
public final class Webhook extends DiscordObject implements IWebhook {

    private final String id;
    private final WebhookManager manager;

    private IGuild guild;
    private ITextChannel channel;
    private IUser owner;
    private IUser user;
    private String defaultName;
    private String defaultAvatar;
    private String token;

    public Webhook(IdentityImpl identity, String id) {
        super(identity);
        this.id = id;
        this.manager = new WebhookManager(this);
    }

    @Override
    public IWebhookManager getWebhookManager() {
        return manager;
    }

    @Override
    public IGuild getGuild() {
        return guild;
    }

    @Override
    public ITextChannel getChannel() {
        return channel;
    }

    @Override
    public IUser getOwner() {
        return owner;
    }

    @Override
    public IUser getUser() {
        return user;
    }

    @Override
    public String getDefaultName() {
        return defaultName;
    }

    @Override
    public String getDefaultAvatar() {
        return defaultAvatar;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public String getId() {
        return id;
    }

    /*-----------------------Setters------------------------*/

    public Webhook setGuild(IGuild guild) {
        this.guild = guild;
        return this;
    }

    public Webhook setChannel(ITextChannel channel) {
        this.channel = channel;
        return this;
    }

    public Webhook setOwner(IUser owner) {
        this.owner = owner;
        return this;
    }

    public Webhook setUser(IUser user) {
        this.user = user;
        return this;
    }

    public Webhook setDefaultName(String defaultName) {
        this.defaultName = defaultName;
        return this;
    }

    public Webhook setDefaultAvatar(String defaultAvatar) {
        this.defaultAvatar = defaultAvatar;
        return this;
    }

    public Webhook setToken(String token) {
        this.token = token;
        return this;
    }

    /*---------------------Object Overrides--------------------*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Webhook)) return false;
        if (!super.equals(o)) return false;

        Webhook webhook = (Webhook) o;

        if (!id.equals(webhook.id)) return false;
        return token != null ? token.equals(webhook.token) : webhook.token == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + (token != null ? token.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Webhook{" +
                "key='" + id + '\'' +
                ", channel=" + channel +
                ", defaultName='" + defaultName + '\'' +
                '}';
    }
}
