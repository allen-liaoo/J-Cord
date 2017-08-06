package org.alienideology.jcord.event.client.call.user;

import org.alienideology.jcord.handle.channel.ICallChannel;
import org.alienideology.jcord.handle.client.IClient;
import org.alienideology.jcord.handle.client.call.ICall;
import org.alienideology.jcord.handle.client.call.ICallUser;

/**
 * @author AlienIdeology
 */
public class CallUserLeaveEvent extends CallUserEvent {

    private final ICallChannel channelLeft;

    public CallUserLeaveEvent(IClient client, int sequence, ICall call, ICallUser callUser, ICallChannel channelLeft) {
        super(client, sequence, call, callUser);
        this.channelLeft = channelLeft;
    }

    public ICallChannel getChannelLeft() {
        return channelLeft;
    }

}
