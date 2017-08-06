package org.alienideology.jcord.internal.object.modifiers;

import org.alienideology.jcord.handle.modifiers.IAttribute;
import org.alienideology.jcord.handle.modifiers.IModifier;
import org.json.JSONObject;

/**
 * @author AlienIdeology
 */
public abstract class Attribute<M extends IModifier, T> implements IAttribute<M, T> {

    private final String key;
    private final M modifier;
    protected T oldValue;
    protected T newValue;
    protected boolean isChanged;

    public Attribute(String key, M modifier, T oldValue) {
        this.key = key;
        this.modifier = modifier;
        this.oldValue = oldValue;
        this.newValue = null;
        this.isChanged = false;
    }

    @Override
    public M getModifier() {
        return modifier;
    }

    @Override
    public M setValue(T newValue) {
        checkValue(newValue);
        this.newValue = newValue;
        this.isChanged = true;
        return modifier;
    }

    @Override
    public abstract void checkValue(T value);

    @Override
    public M reset() {
        newValue = null;
        this.isChanged = false;
        return modifier;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public T getOldValue() {
        return oldValue;
    }

    @Override
    public T getNewValue() {
        return newValue;
    }

    @Override
    public Object getAltValue() {
        return needUpdate() ? newValue == null ? JSONObject.NULL : newValue : oldValue == null ? JSONObject.NULL : oldValue;
    }

    @Override
    public boolean isChanged() {
        return isChanged;
    }

    //-------------------------Internal-------------------------

    // If the value is changed, update the old value. Then reset the new value.
    public void update() {
        if (needUpdate())
            oldValue = newValue;
        reset();
    }

}
