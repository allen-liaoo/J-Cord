package org.alienideology.jcord.event.client.call.update;

import org.alienideology.jcord.event.client.call.CallUpdateEvent;
import org.alienideology.jcord.handle.Region;
import org.alienideology.jcord.internal.object.client.Client;
import org.alienideology.jcord.internal.object.client.call.Call;

/**
 * @author AlienIdeology
 */
public class CallRegionUpdateEvent extends CallUpdateEvent {

    private final Region oldRegion;

    public CallRegionUpdateEvent(Client client, int sequence, Call call, Region oldRegion) {
        super(client, sequence, call);
        this.oldRegion = oldRegion;
    }

    public Region getOldRegion() {
        return oldRegion;
    }

}
