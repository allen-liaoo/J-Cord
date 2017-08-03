package org.alienideology.jcord.event.client.call;

import org.alienideology.jcord.internal.object.client.Client;
import org.alienideology.jcord.internal.object.client.call.Call;

/**
 * @author AlienIdeology
 */
public class CallUpdateEvent extends CallEvent {

    public CallUpdateEvent(Client client, int sequence, Call call) {
        super(client, sequence, call);
    }

}
