package org.alienideology.jcord.handle.oauth;

import org.alienideology.jcord.internal.gateway.HttpPath;
import org.alienideology.jcord.util.DataUtils;

/**
 * OAuthBuilder - A builder for building instance of OAuth and OAuth authorization URL.
 *
 * @author AlienIdeology
 */
public class OAuthBuilder {

    private String clientId;
    private String clientSecret;
    private String redirectUrl;
    private Scope[] scopes;

    public OAuthBuilder() {
    }

    /**
     * Set the client ID of this OAuth.
     *
     * @param clientId The client ID.
     * @return OAuthBuilder for chaining.
     */
    public OAuthBuilder setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    /**
     * Set the client secret of this OAuth.
     *
     * @param clientSecret The client secret.
     * @return OAuthBuilder for chaining.
     */
    public OAuthBuilder setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    /**
     * Set the redirect/callback url of this OAuth.
     * The callback url should be a server you manage, in which you can receive the authorization code from there.
     * You may also use {@link OAuth#autoAuthorize()} to automatically set up a server and wait for the authorization code.
     *
     * @param redirectUrl The url.
     * @return OAuthBuilder for chaining.
     */
    public OAuthBuilder setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
        return this;
    }

    /**
     * Set the scopes to grant to the user.
     *
     * @param scopes The scopes.
     * @return OAuthBuilder for chaining.
     */
    public OAuthBuilder setScopes(Scope... scopes) {
        this.scopes = scopes;
        return this;
    }

    /**
     * Build a OAuth URL for the clients to authorize.
     *
     * @return The string url.
     */
    public String buildUrl() {
        StringBuilder baseUrl = new StringBuilder(HttpPath.OAuth.AUTHORIZATION).append("?client_id=").append(clientId);

        if (scopes != null) {
            baseUrl.append("&scope=");
            for (Scope scope : scopes) {
               baseUrl.append(scope.key).append(" ");
            }
            baseUrl.deleteCharAt(baseUrl.length()-1);
        }

        if (redirectUrl != null) {
            baseUrl.append("&redirect_uri=").append(DataUtils.encodeToUrl(redirectUrl)).append("&response_type=code");
        }

        return baseUrl.toString();
    }

    /**
     * Build an OAuth instance.
     *
     * @return The OAuth object.
     */
    public OAuth buildOAuth() {
        return new OAuth(clientId, clientSecret, redirectUrl, scopes);
    }

}
