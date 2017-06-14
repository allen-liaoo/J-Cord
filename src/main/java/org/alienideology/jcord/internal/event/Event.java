package org.alienideology.jcord.internal.event;

import org.alienideology.jcord.internal.Identity;

/**
 * Event - Whenever a change happens to an entity, an event get fired
 * @author AlienIdeology
 */
public abstract class Event {

    protected final Identity identity;
    private final int sequence;

    /**
     * Default Constructor
     * @param identity The identity where this event is fired.
     */
    public Event (Identity identity, int sequence) {
        this.identity = identity;
        this.sequence = sequence;
    }

    public Identity getIdentity() {
        return identity;
    }

    /**
     * @return The sequence of this event
     */
    public int getSequence() {
        return sequence;
    }

}
