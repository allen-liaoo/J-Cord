package org.alienideology.jcord.handle.modifiers;

import org.alienideology.jcord.handle.Icon;
import org.alienideology.jcord.handle.modifiers.attr.IconAttribute;
import org.alienideology.jcord.handle.user.IUser;

/**
 * ISelfModifier - A modifier that modify the self user.
 *
 * @author AlienIdeology
 */
public interface ISelfModifier extends IModifier<Void> {

    /**
     * Get the self user.
     *
     * @return The user.
     */
    IUser getSelf();

    /**
     * Modify the user name of this identity.
     * Changing the username will cause the discriminator to randomize.
     *
     * @exception IllegalArgumentException
     *          If the username is not valid. See {@link IUser#isValidUsername(String)}
     *
     * @param name The new username.
     * @return ISelfModifier for chaining.
     */
    ISelfModifier name(String name);

    /**
     * Modify the avatar of this identity.
     *
     * @param icon The new avatar.
     * @return ISelfModifier for chaining.
     */
    ISelfModifier avatar(Icon icon);

    /**
     * Get the user name attribute, used to modify the user name.
     *
     * @return The user name attribute.
     */
    Attribute<ISelfModifier, String> getUsernameAttr();

    /**
     * Get the avatar attribute, used to modify the avatar.
     *
     * @return The avatar attribute.
     */
    IconAttribute<ISelfModifier> getAvatarAttr();

}
