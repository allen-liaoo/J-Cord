package org.alienideology.jcord.event.client.call;

import org.alienideology.jcord.internal.object.client.Client;
import org.alienideology.jcord.internal.object.client.call.Call;

/**
 * @author AlienIdeology
 */
public class CallCreateEvent extends CallEvent{

    public CallCreateEvent(Client client, int sequence, Call call) {
        super(client, sequence, call);
    }

}
