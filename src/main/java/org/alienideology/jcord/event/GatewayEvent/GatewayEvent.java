package org.alienideology.jcord.event.GatewayEvent;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.Event;
import org.alienideology.jcord.gateway.GatewayAdaptor;
import org.json.JSONObject;

/**
 * GatewayEvent - Events that are fired at connection
 * @author AlienIdeology
 */
public abstract class GatewayEvent extends Event {

    private GatewayAdaptor gateway;

    public GatewayEvent (Identity identity, GatewayAdaptor gateway) {
        super(identity);
        this.gateway = gateway;
    }

    public GatewayAdaptor getGateway() {
        return gateway;
    }

    @Override
    public abstract void handleEvent(JSONObject raw);

}
