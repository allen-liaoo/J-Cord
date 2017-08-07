package org.alienideology.jcord.handle.modifiers;

import org.json.JSONObject;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Attribute - An updatable field of an {@link IModifier}.
 *
 * @author AlienIdeology
 * @param <M> The modifier this attribute belongs to.
 * @param <T> The type of value for this attribute.
 */
public abstract class Attribute<M extends IModifier, T> {

    private final String key;
    private final M modifier;
    protected Supplier<T> oldValue;
    protected T newValue;
    protected boolean isSet;

    /**
     * Construct an attribute object.
     *
     * @param key The json key for constructing the json object.
     * @param modifier The modifier this attribute belongs to.
     * @param oldValue The method reference of the old value.
     */
    public Attribute(String key, M modifier, Supplier<T> oldValue) {
        this.key = key;
        this.modifier = modifier;
        this.oldValue = oldValue;
        this.newValue = null;
        this.isSet = false;
    }

    /**
     * Get the modifier of this attribute.
     *
     * @return The modifier.
     */
    public M getModifier() {
        return modifier;
    }

    /**
     * Set this attribute to a new value.
     * This invokes {@link #checkValue(Object)} before checking the value.
     * All {@link RuntimeException} will be thrown if the new value is invalid.
     *
     * @param newValue The new value.
     * @return The modifier for chaining.
     */
    public M setValue(T newValue) {
        checkValue(newValue);
        this.newValue = newValue;
        this.isSet = true;
        return modifier;
    }

    /**
     * Set this attribute to a new value, if the value is valid.
     * If the value is not valid, then invoke the {@link Consumer} error handler to handle the exception.
     *
     * @param newValue The new value.
     * @param errorHandler The handler for handling exceptions when setting value.
     * @return The modifier for chaining.
     */
    public M setValue(T newValue, Consumer<RuntimeException> errorHandler) {
        try {
            setValue(newValue);
        } catch (RuntimeException ex) {
            if (errorHandler != null) errorHandler.accept(ex);
        }
        return modifier;
    }

    /**
     * Set this attribute to a new value, if the value is valid.
     * This method is guaranteed to not throw any exception.
     *
     * @param newValue The new value.
     * @return The modifier for chaining.
     */
    public M setValueIfValid(T newValue) {
        if (isValidValue(newValue)) {
            this.newValue = newValue;
            this.isSet = true;
        }
        return modifier;
    }

    /**
     * Check this value against the circumstances.
     * The {@link RuntimeException} thrown will be different, depends on the individual attribute.
     *
     * @exception IllegalArgumentException
     *          This exception is thrown generally to invalid attributes.
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If this attribute involves a certain {@link org.alienideology.jcord.handle.permission.Permission},
     *          then permission exception will be thrown.
     *
     * @param value The value to check with.
     */
    public abstract void checkValue(T value) throws IllegalArgumentException;

    /**
     * Check if this value is valid or not under the circumstances.
     * This method does not throws any exceptions.
     *
     * @param value The value to check with.
     * @return True if the value is valid, and it can be set to {@link #setValue(Object)} without any exception thrown.
     */
    public boolean isValidValue(T value) {
        try {
            checkValue(value);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Reset this value by discarding the new value.
     *
     * @return The modifier for chaining.
     */
    public M reset() {
        newValue = null;
        this.isSet = false;
        return modifier;
    }

    /**
     * Get the json key that is used to update.
     *
     * @return The json key.
     */
    public String getKey() {
        return key;
    }

    /**
     * Get the old value that is cached by the instance.
     * Some attributes may not support this operation.
     *
     * @exception UnsupportedOperationException
     *          If the cache is unable to provide the old value.
     *
     * @return The old value.
     */
    public T getOldValue() throws UnsupportedOperationException {
        return oldValue.get();
    }

    /**
     * Get the new value, or null if no value is set yet.
     *
     * @return The new value.
     */
    public T getNewValue() {
        return newValue;
    }

    /**
     * Get the alternative value.
     * If the value {@link #needUpdate()}, then return the new value.
     * If the value is not changed, then return the old value.
     * If the returning value is null, return {@link org.json.JSONObject#NULL}.
     *
     * @return The alternative value.
     */
    public Object getAltValue() {
        return needUpdate() ? newValue == null ? JSONObject.NULL : newValue : getOldValue() == null ? JSONObject.NULL : getOldValue();
    }

    /**
     * Check if the new value is set.
     *
     * @return True if the new value is set.
     */
    public boolean isSet() {
        return isSet;
    }

    /**
     * Check if the value is set and different from the {@link #getOldValue()}.
     *
     * @return True if the value is set and different from the old value.
     */
    public boolean needUpdate() {
        return isSet && !Objects.equals(getNewValue(), getOldValue());
    }

}
