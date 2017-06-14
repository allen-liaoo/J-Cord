package org.alienideology.jcord.internal.event.gateway;

import org.alienideology.jcord.internal.Identity;
import org.alienideology.jcord.internal.gateway.GatewayAdaptor;

/**
 * ResumedEvent - Event fired when reconnect to the Discord server
 * @author AlienIdeology
 */
public class ResumedEvent extends GatewayEvent {

    public ResumedEvent(Identity identity, GatewayAdaptor gateway, int sequence) {
        super(identity, gateway, sequence);
    }

}
