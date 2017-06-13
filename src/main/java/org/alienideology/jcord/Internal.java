package org.alienideology.jcord;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Internal - An annotation for a internal method or class.
 * <p>
 *     The user should not use any methods or classes that is marked as Internal.
 *     Using them will only mess up the objects and their states, which will not be beneficial to the user.
 * </p>
 * @author AlienIdeology
 * @since 0.0.2
 */
@Retention(RetentionPolicy.SOURCE)
public @interface Internal {

    Type type() default Type.SETTER;

    enum Type {

        /**
         * Setter methods
         */
        SETTER,

        /**
         * Methods for Updating the object's state
         */
        UPDATER;

    }

}
