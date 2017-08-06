package org.alienideology.jcord.handle.modifiers.attr;

import org.alienideology.jcord.handle.Icon;
import org.alienideology.jcord.handle.modifiers.Attribute;
import org.alienideology.jcord.handle.modifiers.IModifier;

import java.util.function.Supplier;

/**
 * IconAttribute - An attribute that modify an {@link Icon} instance.
 *
 * @author AlienIdeology
 */
public class IconAttribute<M extends IModifier> extends Attribute<M, Icon> {

    public IconAttribute(String key, M modifier, Supplier<Icon> oldValue) {
        super(key, modifier, oldValue);
    }

    @Override
    public void checkValue(Icon value) {}

    /**
     * Throws {@link UnsupportedOperationException}.
     * Use the getter method to get the icon/avatar url,
     * and fetch the image data from there.
     *
     * @exception UnsupportedOperationException
     *          Cannot supply a {@link Icon} instance for the old value.
     *
     * @return nothing.
     */
    @Override
    public Icon getOldValue() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Cannot get the old value of an icon from cache! Fetch the image data from a url instead.");
    }

    @Override
    public Object getAltValue() {
        if (newValue == null)
            newValue = Icon.DEFAULT_ICON;
        return needUpdate() ? getNewValue().getData() : null;
    }

    @Override
    public boolean needUpdate() {
        return isSet();
    }

}
