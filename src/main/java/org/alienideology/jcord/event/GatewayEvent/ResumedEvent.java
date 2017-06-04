package org.alienideology.jcord.event.GatewayEvent;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.gateway.GatewayAdaptor;
import org.json.JSONObject;

/**
 * ResumedEvent - Event fired when reconnect to the Discord server
 * @author AlienIdeology
 */
public class ResumedEvent extends GatewayEvent {

    public ResumedEvent(Identity identity, GatewayAdaptor gateway) {
        super(identity, gateway);
    }

    @Override
    public void handleEvent(JSONObject raw) {}
}
