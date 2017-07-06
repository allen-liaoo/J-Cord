package org.alienideology.jcord.oauth;

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

    /* Authorization */
    private String accessToken;
    private String refreshToken;
    private long expireIn;
    private long initIn;

    OAuth(String clientId, String clientSecret, String redirectUrl, Scope[] scopes) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUrl = redirectUrl;
        this.scopes = scopes;
    }

    /**
     * Authorize the application with an authorization code.
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
                .updateRequestWithBody(request -> request.header("Content-Type", "application/x-www-form-urlencoded"))
                .getAsJSONObject();

        if (!response.has("error") || !response.has("code")) {
            parseJson(response);
        } else {
            throw new ErrorResponseException(response.get("error") instanceof Integer ? response.getInt("error") : -1, response.getString("message"));
        }

        return this;
    }

    /**
     * This set up a server from the {@link #redirectUrl} and bind to the sepcified port.
     * Then the server will wait for user's authorization. If the user authorized the application,
     * the server will get a redirected url with the authorization code as a query param.
     *
     * @param port The port.
     * @return OAuth for chaining.
     */
    public OAuth authorizeAt(int port) {
        try {
            NanoHTTPD server = new NanoHTTPD(redirectUrl, port) {
                @Override
                public Response serve(IHTTPSession session) {
                    if (session.getParms().get("code") != null) {
                        authorize(session.getParms().get("code"));
                    } else if (session.getParms().get("error") != null) {
                        // TODO: throw errors
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

    /**
     * Automatically authorize the application and get the access token.
     *
     * This calls {@link #authorizeAt(int)} with port 8080.
     *
     * @return OAuth for chaining.
     */
    public OAuth autoAuthorize() {
        return authorizeAt(8080);
    }

    /**
     * Refresh the access token.
     * This revokes the access token by posting a http request with the refresh token.
     * If the access token is still functional, then this will not perform any action.
     *
     * @return OAuth for chaining.
     */
    public OAuth refreshToken() {
        if (shouldRefresh()) {
            JSONObject response = new Requester(refreshToken, HttpPath.OAuth.REVOCATION)
                    .request(clientSecret, clientSecret, refreshToken)
                    .updateRequestWithBody(request -> request.header("Content-Type", "application/x-www-form-urlencoded"))
                    .getAsJSONObject();

            if (!response.has("error")) {
                parseJson(response);
            } else {
                throw new ErrorResponseException(response.get("error") instanceof Integer ? response.getInt("error") : -1, response.getString("message"));
            }
        }

        return this;
    }

    private boolean shouldRefresh() {
        return System.currentTimeMillis() >
                expireIn * 1000 + initIn; // Add the expired value, in seconds, with the initial time.
    }

    private void parseJson(JSONObject response) {
        accessToken = response.getString("access_token");
        refreshToken = response.getString("refresh_token");
        expireIn = response.getLong("expires_in");
        initIn = System.currentTimeMillis();

        // TODO: What about Webhook? ("webhook" json)
    }

}
