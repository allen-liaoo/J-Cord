package org.alienideology.jcord.handle.modifiers;

import org.alienideology.jcord.handle.Icon;
import org.alienideology.jcord.handle.channel.IGroup;
import org.alienideology.jcord.handle.client.IClientObject;
import org.alienideology.jcord.handle.modifiers.attr.IconAttribute;

/**
 * IGroupModifier - A modifier that modify a group.
 *
 * @author AlienIdeology
 */
public interface IGroupModifier extends IClientObject, IModifier<Void> {

    /**
     * Get the group this modifier modifies.
     *
     * @return The group.
     */
    IGroup getGroup();

    /**
     * Modify the group name.
     * Use empty or {@code null} name to reset the name.
     *
     * @exception IllegalArgumentException
     *          If the group name is not valid. See {@link IGroup#isValidName(String)}.
     *
     * @param name The name.
     * @return IGroupModifier for chaining.
     */
    IGroupModifier name(String name);

    /**
     * Modify the group icon.
     *
     * @param icon The icon.
     * @return IGroupModifier for chaining.
     */
    IGroupModifier icon(Icon icon);

    /**
     * Get the name attribute, used to modify the group's name.
     *
     * @return The name attribute.
     */
    Attribute<IGroupModifier, String> getNameAttr();

    /**
     * Get the icon attribute, used to modify the group's icon.
     *
     * @return The icon attribute.
     */
    IconAttribute<IGroupModifier> getIconAttr();

}
