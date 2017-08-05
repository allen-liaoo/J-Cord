package org.alienideology.jcord.handle.managers;

import org.alienideology.jcord.handle.Icon;
import org.alienideology.jcord.handle.client.IClient;
import org.alienideology.jcord.handle.client.IClientObject;
import org.alienideology.jcord.handle.client.app.IApplication;

import java.util.Arrays;
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
     * @param name The name.
     */
    void modifyName(String name);

    /**
     * Modify the icon of this application.
     *
     * @param icon The image file.
     */
    void modifyIcon(Icon icon);

    /**
     * Modify the description of this application.
     *
     * @param description The description.
     */
    void modifyDescription(String description);

    /**
     * Modify the redirect uris of this application.
     *
     * @param redirectUris The redirect uris.
     */
    void modifyRedirectUris(List<String> redirectUris);

    /**
     * Modify the redirect uris of this application.
     *
     * @param redirectUris The varargs of redirect uris.
     */
    default void modifyRedirectUris(String... redirectUris) {
        modifyRedirectUris(Arrays.asList(redirectUris));
    }

    /**
     * Enable or disable public bot of this application.
     *
     * @param isBotPublic The boolean value. True to enable public bot, false for private bot.
     */
    void modifyIsBotPublic(boolean isBotPublic);

    /**
     * Enable or disable require code grant for this application.
     *
     * @param requireCodeGrant The boolean value. True to enable requiring code grant, false otherwise.
     */
    void modifyRequireCodeGrant(boolean requireCodeGrant);

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
