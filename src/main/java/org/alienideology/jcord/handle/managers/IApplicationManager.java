package org.alienideology.jcord.handle.managers;

import org.alienideology.jcord.handle.Icon;
import org.alienideology.jcord.handle.client.IClient;
import org.alienideology.jcord.handle.client.IClientObject;
import org.alienideology.jcord.handle.client.app.IApplication;

import java.util.List;

/**
 * IApplicationManager - A manager for managing {@link IApplication}.
 *
 * @author AlienIdeology
 */
public interface IApplicationManager extends IClientObject {

    @Override
    default IClient getClient() {
        return getApplication().getClient();
    }

    /**
     * Get the application that this manager manages.
     *
     * @return The application.
     */
    IApplication getApplication();

    /**
     * Modify the name of this application.
     *
     * @exception IllegalArgumentException
     *          If the name is not valid. See {@link IApplication#isValidName(String)}.
     *
     * @param name The name.
     */
    default void modifyName(String name) {
        getApplication().getModifier().name(name).modify();
    }

    /**
     * Modify the icon of this application.
     *
     * @param icon The image file.
     */
    default void modifyIcon(Icon icon) {
        getApplication().getModifier().icon(icon).modify();
    }

    /**
     * Modify the description of this application.
     *
     * @exception IllegalArgumentException
     *          If the description is not valid. See {@link IApplication#isValidDescription(String)}.
     *
     * @param description The description.
     */
    default void modifyDescription(String description) {
        getApplication().getModifier().description(description).modify();
    }

    /**
     * Modify the redirect uris of this application.
     *
     * @param redirectUris The redirect uris.
     */
    default void modifyRedirectUris(List<String> redirectUris) {
        getApplication().getModifier().redirectUris(redirectUris).modify();
    }

    /**
     * Modify the redirect uris of this application.
     *
     * @param redirectUris The varargs of redirect uris.
     */
    default void modifyRedirectUris(String... redirectUris) {
        getApplication().getModifier().redirectUris(redirectUris).modify();
    }

    /**
     * Enable or disable public bot of this application.
     *
     * @param isBotPublic The boolean value. True to enable public bot, false for private bot.
     */
    default void modifyIsBotPublic(boolean isBotPublic) {
        getApplication().getModifier().isBotPublic(isBotPublic).modify();
    }

    /**
     * Enable or disable require code grant for this application.
     *
     * @param requireCodeGrant The boolean value. True to enable requiring code grant, false otherwise.
     */
    default void modifyRequireCodeGrant(boolean requireCodeGrant) {
        getApplication().getModifier().requireCodeGrant(requireCodeGrant).modify();
    }

    /**
     * Creates a bot user, which bundles a {@link IApplication.IBotUser}
     * with the {@link IApplication}. This action is irreversible.
     *
     * @see IApplication#hasBot()
     */
    void createBotUser();

    /**
     * Reset the application's client secret.
     *
     * @see IApplication#getSecret()
     */
    void resetSecret();

    /**
     * Reset the application's bot token.
     *
     * @exception IllegalArgumentException
     *          If the application hasn't have a bot user.
     * @see IApplication#hasBot()
     *
     * @see IApplication.IBotUser#getToken()
     */
    void resetToken();

}
