package org.alienideology.jcord.event.client.call.user;

import org.alienideology.jcord.event.client.call.CallEvent;
import org.alienideology.jcord.handle.client.call.ICallUser;
import org.alienideology.jcord.handle.client.call.ICallVoiceState;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.object.client.Client;
import org.alienideology.jcord.internal.object.client.call.Call;
import org.alienideology.jcord.internal.object.client.call.CallUser;

/**
 * @author AlienIdeology
 */
public class CallUserEvent extends CallEvent {

    private CallUser callUser;

    public CallUserEvent(Client client, int sequence, Call call, CallUser callUser) {
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
