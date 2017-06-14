package org.alienideology.jcord.event.gateway;

import org.alienideology.jcord.internal.object.Identity;
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
