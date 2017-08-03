package org.alienideology.jcord.event.client.call.user;

import org.alienideology.jcord.handle.channel.ICallChannel;
import org.alienideology.jcord.internal.object.client.Client;
import org.alienideology.jcord.internal.object.client.call.Call;
import org.alienideology.jcord.internal.object.client.call.CallUser;

/**
 * @author AlienIdeology
 */
public class CallUserJoinEvent extends CallUserEvent {

    public CallUserJoinEvent(Client client, int sequence, Call call, CallUser callUser) {
        super(client, sequence, call, callUser);
    }

    public ICallChannel getChannelJoined() {
        return getCall().getChannel();
    }

}
