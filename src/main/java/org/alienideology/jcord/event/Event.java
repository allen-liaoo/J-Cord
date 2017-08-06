package org.alienideology.jcord.event;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.internal.object.DiscordObject;

/**
 * Event - Whenever a change happens to an entity, an event get fired.
 *
 * @author AlienIdeology
 */
public abstract class Event extends DiscordObject {

    private final int sequence;

    /**
     * Default Constructor
     * @param identity The identity where this event is fired.
     */
    public Event (Identity identity, int sequence) {
        super(identity);
        this.sequence = sequence;
    }

    /**
     * @return The sequence of this event
     */
    public int getSequence() {
        return sequence;
    }

}
