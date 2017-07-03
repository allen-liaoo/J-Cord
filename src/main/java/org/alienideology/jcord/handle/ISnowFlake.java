package org.alienideology.jcord.handle;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

/**
 * ISnowFlake - Objects that has ID.
 * @author AlienIdeology
 */
public interface ISnowFlake {

    /**
     * Get the ID, or snowflake.
     *
     * @return The ID of an entity.
     */
    String getId();

    /**
     * Get the long value of this ID.
     *
     * @return The Long ID of an entity.
     */
    default Long getLongId() {
        return Long.parseLong(getId());
    }

    /**
     * Get the created time of this snowflake object.
     *
     * @return The created time.
     */
    default OffsetDateTime getCreatedTime() {
        long id = Long.parseLong(getId());
        // Get the first 22 digits
        id = id >> 22;
        // Add Discord Epoch time, which is the first second of 2015
        id += 1420070400000L;
        return OffsetDateTime.ofInstant(Instant.ofEpochMilli(id), ZoneId.systemDefault());
    }

}
