package org.alienideology.jcord.event;

import org.alienideology.jcord.Identity;
import org.json.JSONObject;

/**
 * The super class of every event event, for parsing json.
 * @author AlienIdeology
 */
public abstract class Event {

    protected Identity identity;

    private int sequence;

    /**
     * Default Constructor
     * @param identity The identity where this event is fired.
     */
    public Event (Identity identity) {
        this.identity = identity;
    }

    /**
     * Handle events
     * Initializing event object from the json provided.
     * @param raw the JSONObject representing this event.
     */
    public abstract void handleEvent (JSONObject raw);

    public Identity getIdentity() {
        return identity;
    }

    /**
     * @return The sequence of this event
     */
    public int getSequence() {
        return sequence;
    }

    public Event setSequence (int sequence) {
        this.sequence = sequence; return this;
    }
}
