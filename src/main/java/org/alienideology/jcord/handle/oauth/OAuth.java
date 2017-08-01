package org.alienideology.jcord.handle.oauth;

import fi.iki.elonen.NanoHTTPD;
import org.alienideology.jcord.internal.exception.ErrorResponseException;
import org.alienideology.jcord.internal.gateway.HttpPath;
import org.alienideology.jcord.internal.gateway.Requester;
import org.alienideology.jcord.util.DataUtils;
import org.json.JSONObject;

import java.io.IOException;

/**
 * OAuth - Used to build applications that utilize authentication and data from the Discord API.
 *
 * @author AlienIdeology
 */
public class OAuth {

    private String clientId;
    private String clientSecret;
    private String redirectUrl;
    private Scope[] scopes;

    OAuth(String clientId, String clientSecret, String redirectUrl, Scope[] scopes) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUrl = redirectUrl;
        this.scopes = scopes;
    }

    /**
     * Get the client ID of this OAuth. It is used in authorization.
     *
     * @return The client ID.
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Get the client secret of this OAuth. It is used in authorization.
     *
     * @return The client secret.
     */
    public String getClientSecret() {
        return clientSecret;
    }

    /**
     * Get the redirect of this OAuth. It is used to get access token from an user.
     *
     * @return The redirect url.
     */
    public String getRedirectUrl() {
        return redirectUrl;
    }

    /**
     * Get the scopes of this oauth.
     *
     * @return The scopes.
     */
    public Scope[] getScopes() {
        return scopes;
    }

    /**
     * Authorize the app with an authorization code.
     *
     * @exception ErrorResponseException
     *          For any error responses when authorising.
     *
     * @param authorizeCode The authorization code received from the {@link #redirectUrl} server.
     * @return OAuth for chaining.
     */
    public OAuth authorize(String authorizeCode) {
        JSONObject response = new Requester(authorizeCode, HttpPath.OAuth.TOKEN)
                .request(clientId, clientSecret, authorizeCode, DataUtils.encodeToUrl(redirectUrl))
                .updateRequestWithBody(request -> request.header("Content-Type", "app/x-www-form-urlencoded"))
                .getAsJSONObject();

        if (!response.has("error") || !response.has("code")) {
            System.out.println(response.toString(4));
            // TODO: Parse Json and initialize objects
        } else {
            throw new ErrorResponseException(response.get("error") instanceof Integer ? response.getInt("error") : -1, response.getString("message"));
        }

        return this;
    }

    /**
     * Automatically set up a local server and get the access token.
     *
     * This calls {@link #authorizeAt(int)} with port 8080.
     *
     * @return OAuth for chaining.
     */
    public OAuth autoAuthorize() {
        return authorizeAt(8080);
    }

    /**
     * This set up a server from the {@link #redirectUrl} and bind to the specified port.
     * Then the server will wait for users' authorization. If a user authorized the application,
     * the server will get a redirected url with the authorization code as a query param, then cache the user.
     *
     * @param port The port.
     * @return OAuth for chaining.
     */
    public OAuth authorizeAt(int port) {
        try {
            NanoHTTPD server = new NanoHTTPD(port) {
                @Override
                public Response serve(IHTTPSession session) {
                    if (session.getParms().get("code") != null) {
                        authorize(session.getParms().get("code"));
                        System.out.println(session.getParms().get("code"));
                    } else if (session.getParms().get("error") != null) {
                        // TODO: throw errors
                        System.out.println(session.getParms().get("error"));
                    }
                    return null;
                }
            };
            server.start();
        } catch (IOException e) {
            // TODO: throw errors
            e.printStackTrace();
        }

        return this;
    }

}
