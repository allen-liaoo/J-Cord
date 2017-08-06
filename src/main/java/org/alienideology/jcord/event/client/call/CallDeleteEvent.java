package org.alienideology.jcord.event.client.call;

import org.alienideology.jcord.handle.client.IClient;
import org.alienideology.jcord.handle.client.call.ICall;

/**
 * @author AlienIdeology
 */
public class CallDeleteEvent extends CallEvent {

    public CallDeleteEvent(IClient client, int sequence, ICall call) {
        super(client, sequence, call);
    }

}
