package org.alienideology.jcord.handler;

import org.alienideology.jcord.Identity;
import org.json.JSONObject;

/**
 * The super class of every event handler, for parsing json.
 * @author AlienIdeology
 */
public abstract class EventHandler {

    protected Identity id;

    public EventHandler (Identity id) {
        this.id = id;
    }

    public abstract void handleEvent (JSONObject raw);

}
