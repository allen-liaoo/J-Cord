package org.alienideology.jcord.event.gateway;

import org.alienideology.jcord.internal.object.Identity;
import org.alienideology.jcord.event.Event;
import org.alienideology.jcord.internal.gateway.GatewayAdaptor;

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
