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

    public Event(Identity identity) {
        this.identity = identity;
    }

    public abstract void handleEvent(JSONObject raw);

    public Identity getIdentity() {
        return identity;
    }

    public int getSequence() {
        return sequence;
    }

    public Event setSequence(int sequence) {
        this.sequence = sequence; return this;
    }
}
