package org.alienideology.jcord.handle.builders;

/**
 * Buildable - An interface for builders.
 *
 * @author AlienIdeology
 */
public interface Buildable<Builder, T> {

    /**
     * Build a new instance.
     *
     * @return The object built.
     */
    T build();

    /**
     * Clear and reset the builder.
     *
     * @return The reset builder.
     */
    Builder clear();

}
