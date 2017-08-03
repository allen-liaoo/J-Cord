package org.alienideology.jcord.event.client.call.user;

import org.alienideology.jcord.handle.channel.ICallChannel;
import org.alienideology.jcord.internal.object.client.Client;
import org.alienideology.jcord.internal.object.client.call.Call;
import org.alienideology.jcord.internal.object.client.call.CallUser;

/**
 * @author AlienIdeology
 */
public class CallUserLeaveEvent extends CallUserEvent {

    private final ICallChannel channelLeft;

    public CallUserLeaveEvent(Client client, int sequence, Call call, CallUser callUser, ICallChannel channelLeft) {
        super(client, sequence, call, callUser);
        this.channelLeft = channelLeft;
    }

    public ICallChannel getChannelLeft() {
        return channelLeft;
    }

}
