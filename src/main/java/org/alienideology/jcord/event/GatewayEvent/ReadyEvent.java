package org.alienideology.jcord.event.GatewayEvent;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.gateway.GatewayAdaptor;
import org.json.JSONObject;

/**
 * @author AlienIdeology
 */
public class ReadyEvent extends GatewayEvent {

    public ReadyEvent(Identity identity, GatewayAdaptor gateway) {
        super(identity, gateway);
    }

    @Override
    public void handleEvent(JSONObject raw) {
        System.out.println("Ready:\n"+raw.toString(4));
    }
}
