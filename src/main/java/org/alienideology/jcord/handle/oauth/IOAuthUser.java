package org.alienideology.jcord.handle.oauth;


import org.alienideology.jcord.handle.user.IUser;

/**
 * IOAuthUser - An user that authorized the oauth.
 *
 * @author AlienIdeology
 */
public interface IOAuthUser {

    OAuth getOAuth();

    IUser getUser();

    String getAccessToken();

    String getRefreshToken();

    long tokenExpiredIn();

    long tokenAuthorizedIn();

    void refreshToken();

    void setUpAutoRefresh();

}
