package org.alienideology.jcord.internal.event.gateway;

import org.alienideology.jcord.internal.Identity;
import org.alienideology.jcord.internal.event.Event;
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
