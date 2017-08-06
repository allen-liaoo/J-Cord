package org.alienideology.jcord.handle.modifiers;

import org.alienideology.jcord.handle.IDiscordObject;

/**
 * IModifier - A modifier that support chainable methods to update multiple {@link IAttribute} of an instance at once.
 *
 * @author AlienIdeology
 * @param <R> The returning value of the method {@link #modify()}.
 */
public interface IModifier<R> extends IDiscordObject {

    /**
     * Modify this instance, if possible.
     * This will constructs an json object with every {@link IAttribute} that requires update,
     * then send it as an http request to complete the modification.
     *
     * @return Void or other actions such as {@link org.alienideology.jcord.handle.audit.AuditAction}.
     */
    R modify();

    /**
     * Reset this modifier by resetting all changed values.
     */
    void reset();

    /**
     * Check if this modifier's values are changed, different from the original values.
     *
     * @return True if this modifier's values are changed.
     */
    boolean needUpdate();

}
