package org.alienideology.jcord.event;

import org.alienideology.jcord.Identity;
import org.json.JSONObject;

/**
 * The super class of every event event, for parsing json.
 * @author AlienIdeology
 */
public abstract class Event {

    protected Identity identity;

    private int opCode;
    private int session;

    public Event(Identity identity) {
        this.identity = identity;
    }

    public abstract void handleEvent(JSONObject raw);

    public Identity getIdentity() {
        return identity;
    }

    public int getOpCode() {
        return opCode;
    }

    public Event setOpCode(int opCode) {
        this.opCode = opCode;
        return this;
    }

    public int getSession() {
        return session;
    }

    public Event setSession(int session) {
        this.session = session; return this;
    }
}
