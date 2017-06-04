package org.alienideology.jcord.event.gateway;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.Event;
import org.alienideology.jcord.gateway.GatewayAdaptor;
import org.json.JSONObject;

/**
 * gateway - Events that are fired at connection
 * @author AlienIdeology
 */
public abstract class GatewayEvent extends Event {

    private GatewayAdaptor gateway;

    public GatewayEvent (Identity identity, GatewayAdaptor gateway, int sequence) {
        super(identity, sequence);
        this.gateway = gateway;
    }

    public GatewayAdaptor getGateway() {
        return gateway;
    }

}
