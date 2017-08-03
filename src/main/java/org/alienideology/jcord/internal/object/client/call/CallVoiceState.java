package org.alienideology.jcord.internal.object.client.call;

import org.alienideology.jcord.handle.IVoiceState;
import org.alienideology.jcord.handle.client.IClient;
import org.alienideology.jcord.handle.client.call.ICallVoiceState;
import org.alienideology.jcord.internal.object.VoiceState;
import org.alienideology.jcord.internal.object.client.Client;

/**
 * @author AlienIdeology
 */
public class CallVoiceState extends VoiceState implements ICallVoiceState {

    private final Client client;
    private boolean isInCall = false;
    private boolean isWaiting = false;

    public CallVoiceState(Client client, IVoiceState state) {
        super(client.getIdentity(), state.getUser());
        this.client = client;
        setChannel(state.getChannel());
        setSessionId(state.getSessionId());
        setSelfMute(state.isSelfMute());
        setSelfDeafened(state.isSelfDeafened());
    }

    @Override
    public IClient getClient() {
        return client;
    }

    @Override
    public boolean isInCall() {
        return isInCall;
    }

    @Override
    public boolean isWaiting() {
        return isWaiting;
    }

    public void setInCall(boolean inCall) {
        isInCall = inCall;
    }

    public void setWaiting(boolean waiting) {
        isWaiting = waiting;
    }

}
