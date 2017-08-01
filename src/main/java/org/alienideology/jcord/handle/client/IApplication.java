package org.alienideology.jcord.handle.client;

import org.alienideology.jcord.handle.ISnowFlake;
import org.alienideology.jcord.internal.gateway.HttpPath;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * IApplication - An application that the client owns.
 *
 * @author AlienIdeology
 */
public interface IApplication extends IClientObject, ISnowFlake {

    /**
     * Get the client secret of this application.
     *
     * @return The client secret.
     */
    String getSecret();

    /**
     * Get the name of this application.
     *
     * @return The name.
     */
    String getName();

    /**
     * Get the icon hash of this application.
     *
     * @return The icon hash.
     */
    String getIconHash();

    /**
     * Get the icon url of this application.
     *
     * @return The icon url.
     */
    default String getIconUrl() {
        return String.format(HttpPath.EndPoint.APPLICATION_ICON, getId(), getIconHash());
    }

    /**
     * Get the description of this application.
     * The description may be empty.
     *
     * @return The description.
     */
    String getDescription();

    /**
     * Get a list of redirect uris of this application.
     *
     * @return The redirect uris.
     */
    List<String> getRedirectUris();

    /**
     * Get the {@link IBotUser} entity of this application.
     * If this application haven't creates a bot user, then this will returns {@code null}.
     *
     * @return The bot of this application.
     */
    @Nullable
    IApplication.IBotUser getBot();

    /**
     * Check if the application has a bot user or not.
     *
     * @return True if the application has a bot user.
     */
    boolean hasBot();

    /**
     * Check if the bot user of this application is a public bot or not.
     * Always returns false if {@link #hasBot()} returns false.
     *
     * @return True if the bot user of this application is a public bot.
     */
    boolean isPublicBot();

    /**
     * Check if the bot user of this application requires code grant or not.
     * Always returns false if {@link #hasBot()} returns false.
     *
     * @return True if the bot user of this application requires code grant.
     */
    boolean requireCodeGrant();

    /**
     * The bot user of an application.
     */
    interface IBotUser {

        /**
         * Get the ID of this bot.
         *
         * @return The bot id.
         */
        String getId();

        /**
         * The token of this bot user.
         *
         * @return The token.
         */
        String getToken();

        /**
         * Get the name of this bot user.
         *
         * @return The name.
         */
        String getName();

        /**
         * Get the discriminator of this bot user.
         *
         * @return The discriminator.
         */
        String getDiscriminator();

        /**
         * Get the avatar hash of this bot user.
         *
         * @return The avatar hash.
         */
        String getAvatarHash();

        /**
         * Get the avatar url of this bot user.
         *
         * @return The avatar url.
         */
        default String getAvatarUrl() {
            return  getAvatarHash() == null ? String.format(HttpPath.EndPoint.DEFAULT_AVATAR, String.valueOf(Integer.parseInt(getDiscriminator()) % 5)) :
                    String.format(HttpPath.EndPoint.AVATAR, getId(), getAvatarHash(), (getAvatarHash().startsWith("a_") ? "gif" : "png"));
        }

    }

}
