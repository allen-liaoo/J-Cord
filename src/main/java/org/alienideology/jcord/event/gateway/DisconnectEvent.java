package org.alienideology.jcord.event.gateway;

import org.alienideology.jcord.internal.gateway.DisconnectionCode;
import org.alienideology.jcord.internal.gateway.GatewayAdaptor;
import org.alienideology.jcord.internal.object.IdentityImpl;

/**
 * @author AlienIdeology
 */
public class DisconnectEvent extends GatewayEvent {

    private final boolean closeByServer;
    private final int closeCode;
    private final String closeReason;

    public DisconnectEvent(IdentityImpl identity, GatewayAdaptor gateway, int sequence, boolean closeByServer, int closeCode, String closeReason) {
        super(identity, gateway, sequence);
        this.closeByServer = closeByServer;
        this.closeCode = closeCode;
        this.closeReason = closeReason;
    }

    public boolean isCloseByServer() {
        return closeByServer;
    }

    public int getCloseCode() {
        return closeCode;
    }

    public DisconnectionCode getDisconnectionCode() {
        return DisconnectionCode.getCode(closeCode);
    }

    public String getCloseReason() {
        return closeReason;
    }
}
