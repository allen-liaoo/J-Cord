package org.alienideology.jcord.internal.object.client.call;

import org.alienideology.jcord.handle.client.call.ICall;
import org.alienideology.jcord.handle.client.call.ICallUser;
import org.alienideology.jcord.handle.client.call.ICallVoiceState;
import org.alienideology.jcord.internal.object.VoiceState;
import org.alienideology.jcord.internal.object.client.Client;
import org.alienideology.jcord.internal.object.client.ClientObject;

/**
 * @author AlienIdeology
 */
public final class CallUser extends ClientObject implements ICallUser {

    private Call call;
    private CallVoiceState voiceState;

    public CallUser(Client client, Call call) {
        super(client);
        this.call = call;
    }

    @Override
    public ICall getCall() {
        return call;
    }

    @Override
    public ICallVoiceState getVoiceState() {
        return voiceState;
    }

    public void setVoiceState(VoiceState voiceState) {
        this.voiceState = new CallVoiceState(getClient(), this, voiceState);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CallUser)) return false;
        if (!super.equals(o)) return false;

        CallUser user = (CallUser) o;

        if (call != null ? !call.equals(user.call) : user.call != null) return false;
        return voiceState.equals(user.voiceState);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (call != null ? call.hashCode() : 0);
        result = 31 * result + voiceState.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "CallUser{" +
                "call=" + call +
                ", voiceState=" + voiceState +
                '}';
    }

}
