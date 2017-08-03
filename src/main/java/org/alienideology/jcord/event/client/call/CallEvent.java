package org.alienideology.jcord.event.client.call;

import org.alienideology.jcord.event.client.ClientEvent;
import org.alienideology.jcord.internal.object.client.Client;
import org.alienideology.jcord.internal.object.client.call.Call;

/**
 * @author AlienIdeology
 */
public class CallEvent extends ClientEvent {

    private final Call call;

    public CallEvent(Client client, int sequence, Call call) {
        super(client, sequence);
        this.call = call;
    }

    public Call getCall() {
        return call;
    }

}
