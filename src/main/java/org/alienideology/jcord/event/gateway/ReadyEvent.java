package org.alienideology.jcord.event.gateway;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.gateway.GatewayAdaptor;

/**
 * ReadyEvent - Event that fired when the Discord server is ready
 * @author AlienIdeology
 */
public class ReadyEvent extends GatewayEvent {

    private final String session_id;

    public ReadyEvent(Identity identity, GatewayAdaptor gateway, int sequence, String session_id) {
        super(identity, gateway, sequence);
        this.session_id = session_id;
    }

    public String getSessionId() {
        return session_id;
    }
}
