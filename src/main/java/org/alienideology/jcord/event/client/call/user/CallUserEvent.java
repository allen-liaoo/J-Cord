package org.alienideology.jcord.event.client.call.user;

import org.alienideology.jcord.event.client.call.CallEvent;
import org.alienideology.jcord.handle.client.IClient;
import org.alienideology.jcord.handle.client.call.ICall;
import org.alienideology.jcord.handle.client.call.ICallUser;
import org.alienideology.jcord.handle.client.call.ICallVoiceState;
import org.alienideology.jcord.handle.user.IUser;

/**
 * @author AlienIdeology
 */
public class CallUserEvent extends CallEvent {

    private final ICallUser callUser;

    public CallUserEvent(IClient client, int sequence, ICall call, ICallUser callUser) {
        super(client, sequence, call);
        this.callUser = callUser;
    }

    public IUser getUser() {
        return callUser.getUser();
    }

    public ICallUser getCallUser() {
        return callUser;
    }

    public ICallVoiceState getVoiceState() {
        return callUser.getVoiceState();
    }

}
