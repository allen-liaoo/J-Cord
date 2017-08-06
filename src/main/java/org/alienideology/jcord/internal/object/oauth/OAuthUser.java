package org.alienideology.jcord.internal.object.oauth;

import org.alienideology.jcord.handle.oauth.IOAuthUser;
import org.alienideology.jcord.handle.oauth.OAuth;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.exception.ErrorResponseException;
import org.alienideology.jcord.internal.rest.HttpPath;
import org.alienideology.jcord.internal.rest.Requester;
import org.json.JSONObject;

/**
 * @author AlienIdeology
 */
public class OAuthUser implements IOAuthUser {

    private OAuth oAuth;
    private IUser user;

    /* Authorization */
    private volatile String accessToken;
    private volatile String refreshToken;
    private volatile long expireIn;
    private volatile long authIn;

    public OAuthUser(OAuth oAuth, IUser user, String accessToken, String refreshToken, long expireIn, long authIn) {
        this.oAuth = oAuth;
        this.user = user;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expireIn = expireIn;
        this.authIn = authIn;
    }

    @Override
    public OAuth getOAuth() {
        return oAuth;
    }

    @Override
    public IUser getUser() {
        return user;
    }

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public String getRefreshToken() {
        return refreshToken;
    }

    @Override
    public long tokenExpiredIn() {
        return expireIn;
    }

    @Override
    public long tokenAuthorizedIn() {
        return authIn;
    }

    @Override
    public void refreshToken() {
        if (shouldRefresh()) {
            JSONObject response = new Requester(refreshToken, HttpPath.OAuth.REVOCATION)
                    .request(oAuth.getClientId(), oAuth.getClientSecret(), refreshToken)
                    .updateRequestWithBody(request -> request.header("Content-Type", "application/x-www-form-urlencoded"))
                    .getAsJSONObject();

            if (!response.has("error")) {
                parseJson(response);
            } else {
                throw new ErrorResponseException(response.get("error") instanceof Integer ? response.getInt("error") : -1, response.getString("message"));
            }
        }
    }

    @Override
    public void setUpAutoRefresh() {
        Thread autoRefresh = new Thread(() -> {
            while (true) {
                final long tilRefresh = expireIn * 1000 + authIn;
                try {
                    Thread.sleep(tilRefresh - System.currentTimeMillis());
                } catch (InterruptedException ignored) { continue; }
                refreshToken();
            }
        });
        autoRefresh.start();
    }

    private boolean shouldRefresh() {
        return System.currentTimeMillis() >
                expireIn * 1000 + authIn; // Add the expired value, in seconds, with the initial time.
    }

    private void parseJson(JSONObject response) {
        accessToken = response.getString("access_token");
        refreshToken = response.getString("refresh_token");
        expireIn = response.getLong("expires_in");
        authIn = System.currentTimeMillis();
    }
}
