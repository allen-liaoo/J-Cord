package org.alienideology.jcord.handle;

/**
 * ISnowFlake - Objects that has ID.
 * @author AlienIdeology
 */
public interface ISnowFlake {

    /**
     * @return The ID of an entity.
     */
    String getId();

    /**
     * @return The Long ID of an entity.
     */
    default Long getLongId() {
        return Long.parseLong(getId());
    }

}
