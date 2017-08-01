package org.alienideology.jcord.handle.client.app;

import org.alienideology.jcord.handle.ISnowFlake;
import org.alienideology.jcord.handle.client.IClientObject;
import org.alienideology.jcord.handle.oauth.Scope;
import org.alienideology.jcord.internal.gateway.HttpPath;

import java.util.List;

/**
 * IAuthApplication - An application that the client authorized to perform action with certain scopes.
 *
 * @author AlienIdeology
 */
public interface IAuthApplication extends IClientObject, ISnowFlake {

    /**
     * Get the authorized application ID.
     * Note that this is not the authorization ID.
     *
     * @return The authorized application ID.
     */
    @Override
    String getId();

    /**
     * Get the authorization ID.
     * Note that this is not the application ID.
     *
     * @return The authorization ID.
     */
    String getAuthorizeId();

    /**
     * Get the name of this authorized application.
     *
     * @return The name.
     */
    String getName();

    /**
     * Get the icon hash of this authorized application.
     *
     * @return The icon hash.
     */
    String getIconHash();

    /**
     * Get the icon url of this authorized application.
     *
     * @return The icon url.
     */
    default String getIconUrl() {
        return String.format(HttpPath.EndPoint.APPLICATION_ICON, getId(), getIconHash());
    }

    /**
     * Get the description of this authorized application.
     * The description may be empty.
     *
     * @return The description.
     */
    String getDescription();

    /**
     * Get the scopes this application is authorized with upon this client.
     *
     * @return The scopes that are authorized.
     */
    List<Scope> getScopes();

    /**
     * Check if the bot user of this authorized application is a public bot or not.
     *
     * @return True if the bot user of this application is a public bot.
     */
    boolean isPublicBot();

    /**
     * Check if the bot user of this authorized application requires code grant or not.
     *
     * @return True if the bot user of this application requires code grant.
     */
    boolean requireCodeGrant();

}
