package org.alienideology.jcord.event.client.call.update;

import org.alienideology.jcord.event.client.call.CallUpdateEvent;
import org.alienideology.jcord.handle.Region;
import org.alienideology.jcord.handle.client.IClient;
import org.alienideology.jcord.handle.client.call.ICall;

/**
 * @author AlienIdeology
 */
public class CallRegionUpdateEvent extends CallUpdateEvent {

    private final Region oldRegion;

    public CallRegionUpdateEvent(IClient client, int sequence, ICall call, Region oldRegion) {
        super(client, sequence, call);
        this.oldRegion = oldRegion;
    }

    public Region getOldRegion() {
        return oldRegion;
    }

}
