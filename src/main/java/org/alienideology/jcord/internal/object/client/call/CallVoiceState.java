package org.alienideology.jcord.internal.object.client.call;

import org.alienideology.jcord.handle.IVoiceState;
import org.alienideology.jcord.handle.client.IClient;
import org.alienideology.jcord.handle.client.call.ICall;
import org.alienideology.jcord.handle.client.call.ICallUser;
import org.alienideology.jcord.handle.client.call.ICallVoiceState;
import org.alienideology.jcord.internal.object.VoiceState;
import org.alienideology.jcord.internal.object.client.Client;

/**
 * @author AlienIdeology
 */
public final class CallVoiceState extends VoiceState implements ICallVoiceState {

    private final Client client;
    private final CallUser user;
    private boolean isInCall = false;
    private boolean isWaiting = false;

    public CallVoiceState(Client client, CallUser user, IVoiceState state) {
        super(client.getIdentity(), state.getUser());
        this.client = client;
        this.user = user;
        setChannel(state.getChannel());
        setSessionId(state.getSessionId());
        setSelfMuted(state.isSelfMuted());
        setSelfDeafened(state.isSelfDeafened());
    }

    @Override
    public IClient getClient() {
        return client;
    }

    @Override
    public ICall getCall() {
        return user.getCall();
    }

    @Override
    public ICallUser getCallUser() {
        return user;
    }

    @Override
    public boolean isInCall() {
        return isInCall;
    }

    @Override
    public boolean isWaiting() {
        return isWaiting;
    }

    //---------------------Internal---------------------
    public void setInCall(boolean inCall) {
        isInCall = inCall;
    }

    public void setWaiting(boolean waiting) {
        isWaiting = waiting;
    }

    @Override
    public String toString() {
        return "CallVoiceState{" +
                "client=" + client +
                ", user=" + user +
                ", isInCall=" + isInCall +
                ", isWaiting=" + isWaiting +
                ", selfMute=" + selfMuted +
                ", selfDeafened=" + selfDeafened +
                '}';
    }

}
