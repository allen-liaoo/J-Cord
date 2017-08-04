package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.gateway.ResumedEvent;
import org.alienideology.jcord.internal.gateway.GatewayAdaptor;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.json.JSONObject;

/**
 * @author AlienIdeology
 */
public class ResumedEventHandler extends EventHandler {

    private final GatewayAdaptor gateway;

    public ResumedEventHandler(IdentityImpl identity, GatewayAdaptor gateway) {
        super(identity);
        this.gateway = gateway;
    }

    @Override
    public void dispatchEvent(JSONObject json, int sequence) {

        identity.CONNECTION = Identity.Connection.RESUMING;

        dispatchEvent(new ResumedEvent(identity, gateway, sequence));

        identity.CONNECTION = Identity.Connection.READY;
    }
}
