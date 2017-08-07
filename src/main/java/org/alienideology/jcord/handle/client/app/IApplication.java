package org.alienideology.jcord.handle.client.app;

import org.alienideology.jcord.handle.ISnowFlake;
import org.alienideology.jcord.handle.client.IClientObject;
import org.alienideology.jcord.handle.managers.IApplicationManager;
import org.alienideology.jcord.handle.modifiers.IApplicationModifier;
import org.alienideology.jcord.internal.rest.HttpPath;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * IApplication - An application that the client owns.
 *
 * @author AlienIdeology
 */
public interface IApplication extends IClientObject, ISnowFlake {

    /**
     * The minimum length of an application's name.
     */
    int NAME_LENGTH_MIN = 2;

    /**
     * The maximum length of an application's name.
     */
    int NAME_LENGTH_MAX = 32;

    /**
     * The maximum length of an application's description.
     */
    int DESCRIPTION_LENGTH_MAX = 400;

    /**
     * Checks if an application's name is valid or not.
     *
     * Validations: <br />
     * <ul>
     *     <li>The name may not be null or empty.</li>
     *     <li>The length of the name must be between {@link #NAME_LENGTH_MIN} and {@link #NAME_LENGTH_MAX}.</li>
     * </ul>
     *
     * @param name The name to be check with.
     * @return True if the name is valid.
     */
    static boolean isValidName(String name) {
        return name != null && !name.isEmpty() &&
                name.length() >= NAME_LENGTH_MIN &&
                name.length() <= NAME_LENGTH_MAX;
    }

    /**
     * Check if a description is valid.
     * The description length may not be longer than {@link #DESCRIPTION_LENGTH_MAX} in length.
     *
     * @param description The description to check with.
     * @return True if the description is valid.
     */
    static boolean isValidDescription(String description) {
        return description == null || description.length() <= DESCRIPTION_LENGTH_MAX;
    }

    /**
     * Delete this application.
     *
     * @see org.alienideology.jcord.handle.managers.IClientManager#deleteApplication(IApplication)
     */
    default void delete() {
        getClient().getManager().deleteApplication(this);
    }

    /**
     * Get the manager that manages this application.
     *
     * @return The application manager.
     */
    IApplicationManager getManager();

    /**
     * Get the modifier that modify attributes of this application.
     *
     * @return The application modifier.
     */
    IApplicationModifier getModifier();

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
