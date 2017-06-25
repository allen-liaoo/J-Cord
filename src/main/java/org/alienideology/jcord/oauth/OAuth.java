package org.alienideology.jcord.oauth;

import org.alienideology.jcord.internal.exception.ErrorResponseException;
import org.alienideology.jcord.internal.gateway.HttpPath;
import org.alienideology.jcord.internal.gateway.Requester;
import org.alienideology.jcord.util.DataUtils;
import org.json.JSONObject;

/**
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
     * Automatically authorize the application by setting up a server and get the authorization code.
     *
     * @return OAuth for chaining.
     */
    public OAuth autoAuthorize() {
        // TODO: Set up a web server listener from redirect url, and call #authorize(String) on response
        return this;
    }

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
        // Add the expired value, in seconds, with the initial time.
        return System.currentTimeMillis() > expireIn * 1000 + initIn;
    }

    private void parseJson(JSONObject response) {
        accessToken = response.getString("access_token");
        refreshToken = response.getString("refresh_token");
        expireIn = response.getLong("expires_in");
        initIn = System.currentTimeMillis();

        // TODO: What about Webhook? ("webhook" json)
    }

}
