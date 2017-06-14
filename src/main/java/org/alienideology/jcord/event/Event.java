package org.alienideology.jcord.event;

import org.alienideology.jcord.internal.object.IdentityImpl;

/**
 * Event - Whenever a change happens to an entity, an event get fired
 * @author AlienIdeology
 */
public abstract class Event {

    protected final IdentityImpl identity;
    private final int sequence;

    /**
     * Default Constructor
     * @param identity The identity where this event is fired.
     */
    public Event (IdentityImpl identity, int sequence) {
        this.identity = identity;
        this.sequence = sequence;
    }

    public IdentityImpl getIdentity() {
        return identity;
    }

    /**
     * @return The sequence of this event
     */
    public int getSequence() {
        return sequence;
    }

}
