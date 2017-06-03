package org.alienideology.jcord.event.GatewayEvent;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.Event;
import org.json.JSONObject;

/**
 * @author AlienIdeology
 */
public class ReadyEvent extends Event {

    public ReadyEvent(Identity identity) {
        super(identity);
    }

    @Override
    public void handleEvent(JSONObject raw) {
        System.out.println("Ready:\n"+raw.toString(4));
    }
}
