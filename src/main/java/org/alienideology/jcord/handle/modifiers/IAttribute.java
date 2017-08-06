package org.alienideology.jcord.handle.modifiers;

import java.util.Objects;

/**
 * IAttribute - An updatable field of an {@link IModifier}.
 *
 * @author AlienIdeology
 * @param <M> The modifier this attribute belongs to.
 * @param <T> The type of value for this attribute.
 */
public interface IAttribute<M extends IModifier, T> {

    /**
     * Get the modifier of this attribute.
     *
     * @return The modifier.
     */
    M getModifier();

    /**
     * Set this attribute to a new value.
     * This invokes {@link #checkValue(Object)} before checking the value.
     * All {@link Exception} will be thrown if the new value is invalid.
     *
     * @param newValue The new value.
     * @return The modifier for chaining.
     */
    M setValue(T newValue);

    /**
     * Check this value against the circumstances.
     * The {@link Exception}s thrown will be different, depends on the individual attribute.
     *
     * @exception IllegalArgumentException
     *          This exception is thrown generally to invalid attributes.
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If this attribute involves a certain {@link org.alienideology.jcord.handle.permission.Permission},
     *          then permission exception will be thrown.
     *
     * @param value The value to check with.
     */
    void checkValue(T value) throws IllegalArgumentException;

    /**
     * Check if this value is valid or not under the circumstances.
     * This method does not throws any exceptions.
     *
     * @param value The value to check with.
     * @return True if the value is valid, and it can be set to {@link #setValue(Object)} without any exception thrown.
     */
    default boolean isValidValue(T value) {
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
    M reset();

    /**
     * Get the json key that is used to update.
     *
     * @return The json key.
     */
    String getKey();

    /**
     * Get the old value that is cached by the instance.
     *
     * @return The old value.
     */
    T getOldValue();

    /**
     * Get the new value, or null if no value is set yet.
     *
     * @return The new value.
     */
    T getNewValue();

    /**
     * Get the alternative value.
     * If the value {@link #needUpdate()}, then return the new value.
     * If the value is not changed, then return the old value.
     * If the returning value is null, return {@link org.json.JSONObject#NULL}.
     *
     * @return The alternative value.
     */
    Object getAltValue();

    boolean isChanged();

    /**
     * Check if the value is changed and different from the {@link #getOldValue()}.
     *
     * @return True if the value is changed and different from the old value.
     */
    default boolean needUpdate() {
        return !Objects.equals(getNewValue(), getOldValue());
    }

}
