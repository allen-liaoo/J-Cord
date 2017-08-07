package org.alienideology.jcord.handle.modifiers;

import org.alienideology.jcord.handle.Icon;
import org.alienideology.jcord.handle.client.IClientObject;
import org.alienideology.jcord.handle.client.app.IApplication;
import org.alienideology.jcord.handle.modifiers.attr.IconAttribute;

import java.util.Arrays;
import java.util.List;

/**
 * IApplicationModifier - A modifier that modify an application.
 *
 * @author AlienIdeology
 */
public interface IApplicationModifier extends IClientObject, IModifier<Void> {

    /**
     * Get the application this modifier belongs to.
     *
     * @return The application.
     */
    IApplication getApplication();

    /**
     * Modify the application's name.
     * Use empty or {@code null} string to reset the name.
     *
     * @exception IllegalArgumentException
     *          If the name is not valid. See {@link IApplication#isValidName(String)}.
     *
     * @param name The name.
     * @return IApplicationModifier for chaining.
     */
    IApplicationModifier name(String name);

    /**
     * Modify the application's icon.
     * 
     * @param icon The icon.
     * @return IApplicationModifier for chaining.
     */
    IApplicationModifier icon(Icon icon);

    /**
     * Modify the application's description.
     * Use empty of {@code null} string to reset the description.
     *
     * @exception IllegalArgumentException
     *          If the description is not valid. See {@link IApplication#isValidDescription(String)}.
     *
     * @param description The description.
     * @return IApplicationModifier for chaining.
     */
    IApplicationModifier description(String description);

    /**
     * Modify the application's redirectUris.
     * Use empty list or {@code null} to reset the redirect uris.
     * 
     * @param redirectUris The redirect uris.
     * @return IApplicationModifier for chaining.
     */
    IApplicationModifier redirectUris(List<String> redirectUris);

    /**
     * Modify the application's redirectUris.
     * Use empty array or varargs to reset the redirect uris.
     * 
     * @param redirectUris The redirect uris.
     * @return IApplicationModifier for chaining.
     */
    default IApplicationModifier redirectUris(String... redirectUris) {
        return redirectUris(Arrays.asList(redirectUris));
    }

    /**
     * Modify if the application's bot user is public or not.
     * 
     * @param isBotPublic Is application's bot user public.
     * @return IApplicationModifier for chaining.
     */
    IApplicationModifier isBotPublic(boolean isBotPublic);

    /**
     * Modify if the application require code grant or not.
     * 
     * @param requireCodeGrant Does application require code grant.
     * @return IApplicationModifier for chaining.
     */
    IApplicationModifier requireCodeGrant(boolean requireCodeGrant);

    /**
     * Get the name attribute, used to modify the application's name.
     *
     * @return The name attribute.
     */
    Attribute<IApplicationModifier, String> getNameAttr();

    /**
     * Get the icon attribute, used to modify the application's icon.
     *
     * @return The icon attribute.
     */
    IconAttribute<IApplicationModifier> getIconAttr();

    /**
     * Get the description attribute, used to modify the application's description.
     *
     * @return The description attribute.
     */
    Attribute<IApplicationModifier, String> getDescriptionAttr();

    /**
     * Get the redirect uris attribute, used to modify the application's redirect uris.
     *
     * @return The redirect uris attribute.
     */
    Attribute<IApplicationModifier, List<String>> getRedirectUrisAttr();

    /**
     * Get the is bot public attribute, used to modify if the application's bot user is public or not.
     *
     * @return The is bot public attribute.
     */
    Attribute<IApplicationModifier, Boolean> getIsBotPublicAttr();

    /**
     * Get the require code grant attribute, used to modify if the application require code grant or not.
     *
     * @return The require code grant attribute.
     */
    Attribute<IApplicationModifier, Boolean> getRequireCodeGrantAttr();

}
