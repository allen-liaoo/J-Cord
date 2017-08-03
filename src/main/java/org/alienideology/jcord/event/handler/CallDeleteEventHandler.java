package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.event.client.call.CallDeleteEvent;
import org.alienideology.jcord.handle.channel.ICallChannel;
import org.alienideology.jcord.handle.client.call.ICallUser;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.Group;
import org.alienideology.jcord.internal.object.channel.PrivateChannel;
import org.alienideology.jcord.internal.object.client.Client;
import org.alienideology.jcord.internal.object.client.call.Call;
import org.alienideology.jcord.internal.object.client.call.CallVoiceState;
import org.alienideology.jcord.util.log.LogLevel;
import org.json.JSONObject;

/**
 * @author AlienIdeology
 */
public class CallDeleteEventHandler extends EventHandler {

    public CallDeleteEventHandler(IdentityImpl identity) {
        super(identity);
    }

    @Override
    public void dispatchEvent(JSONObject json, int sequence) {
        Client client = identity.getClient();
        ICallChannel channel = client.getCallChannel(json.getString("channel_id"));
        Call call = (Call) channel.getCurrentCall();

        if (call == null) {
            identity.LOG.log(LogLevel.FETAL, "[UNKNOWN CALL][CALL_DELETE_EVENT_HANDLER]");
            return;
        }

        if (channel instanceof PrivateChannel) {
            ((PrivateChannel) channel).setCurrentCall(null);
        } else {
            ((Group) channel).setCurrentCall(null);
        }

        for (ICallUser user : call.getAllUsers()) {
            ((CallVoiceState) user.getVoiceState()).setWaiting(false);
            ((CallVoiceState) user.getVoiceState()).setInCall(false);
            ((CallVoiceState) user.getVoiceState()).setChannel(null);

            client.getCallUsers().remove(user.getUser().getId());
        }

        call.getConnectedUsers().addAll(call.getWaitingUsers());
        call.getWaitingUsers().clear();

        dispatchEvent(new CallDeleteEvent(client, sequence, call));
    }

}
