package org.alienideology.jcord.event.client.call.user;

import org.alienideology.jcord.handle.channel.ICallChannel;
import org.alienideology.jcord.handle.client.IClient;
import org.alienideology.jcord.handle.client.call.ICall;
import org.alienideology.jcord.handle.client.call.ICallUser;

/**
 * @author AlienIdeology
 */
public class CallUserJoinEvent extends CallUserEvent {

    public CallUserJoinEvent(IClient client, int sequence, ICall call, ICallUser callUser) {
        super(client, sequence, call, callUser);
    }

    public ICallChannel getChannelJoined() {
        return getCall().getChannel();
    }

}
