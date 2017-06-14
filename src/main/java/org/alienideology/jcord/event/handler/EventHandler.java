package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.internal.object.Identity;
import org.alienideology.jcord.event.Event;
import org.alienideology.jcord.internal.object.ObjectBuilder;
import org.json.JSONObject;

/**
 * EventHandler - Handle general events and dispatch them to more specific ones.
 * @author AlienIdeology
 */
public abstract class EventHandler {

    protected final Identity identity;
    protected final ObjectBuilder builder;

    /**
     * Constructor
     * @param identity The identity events fired will belongs to.
     */
    public EventHandler (Identity identity) {
        this.identity = identity;
        this.builder = new ObjectBuilder(this.identity);
    }

    public Identity getIdentity() {
        return identity;
    }

    /**
     * Fire events in every DispatcherAdaptor
     * @param event The event to get fired
     */
    public void fireEvent (Event event) {
        identity.getEventManager().onEvent(event);
    }

    /**
     * Dispatch event base on the provided json.
     * @param json The json of an Discord Gateway event.
     * @param sequence The Gateway sequence.
     */
    public abstract void dispatchEvent (JSONObject json, int sequence);

}
