package org.alienideology.jcord.event.client.call;

import org.alienideology.jcord.event.client.ClientEvent;
import org.alienideology.jcord.handle.client.IClient;
import org.alienideology.jcord.handle.client.call.ICall;

/**
 * @author AlienIdeology
 */
public class CallEvent extends ClientEvent {

    private final ICall call;

    public CallEvent(IClient client, int sequence, ICall call) {
        super(client, sequence);
        this.call = call;
    }

    public ICall getCall() {
        return call;
    }

}
