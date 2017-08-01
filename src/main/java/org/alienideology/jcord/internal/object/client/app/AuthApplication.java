package org.alienideology.jcord.internal.object.client.app;

import org.alienideology.jcord.handle.client.app.IAuthApplication;
import org.alienideology.jcord.handle.oauth.Scope;
import org.alienideology.jcord.internal.object.client.Client;
import org.alienideology.jcord.internal.object.client.ClientObject;

import java.util.List;

/**
 * @author AlienIdeology
 */
public class AuthApplication extends ClientObject implements IAuthApplication {

    private final String id;
    private final String authId;
    private final String name;
    private final String icon;
    private final String description;

    private final List<Scope> scopes;

    private final boolean isPublicBot;
    private final boolean requireCodeGrant;

    public AuthApplication(Client client, String id, String authId, String name, String icon, String description, List<Scope> scopes,
                           boolean isPublicBot, boolean requireCodeGrant) {
        super(client);
        this.id = id;
        this.authId = authId;
        this.name = name;
        this.icon = icon;
        this.description = description;
        this.scopes = scopes;
        this.isPublicBot = isPublicBot;
        this.requireCodeGrant = requireCodeGrant;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getAuthorizeId() {
        return authId;
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
    public List<Scope> getScopes() {
        return scopes;
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
        if (!(o instanceof AuthApplication)) return false;
        if (!super.equals(o)) return false;

        AuthApplication that = (AuthApplication) o;

        if (!id.equals(that.id)) return false;
        return authId.equals(that.authId);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + authId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AuthApplication{" +
                "id='" + id + '\'' +
                ", authId='" + authId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", scopes=" + scopes +
                '}';
    }

}
