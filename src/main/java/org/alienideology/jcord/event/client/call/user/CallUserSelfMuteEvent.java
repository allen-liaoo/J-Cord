package org.alienideology.jcord.event.client.call.user;

import org.alienideology.jcord.internal.object.client.Client;
import org.alienideology.jcord.internal.object.client.call.Call;
import org.alienideology.jcord.internal.object.client.call.CallUser;

/**
 * @author AlienIdeology
 */
public class CallUserSelfMuteEvent extends CallUserEvent {

    public CallUserSelfMuteEvent(Client client, int sequence, Call call, CallUser callUser) {
        super(client, sequence, call, callUser);
    }

    public boolean isSelfMuted() {
        return getVoiceState().isSelfMuted();
    }

}
