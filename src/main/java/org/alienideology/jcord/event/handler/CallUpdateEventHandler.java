package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.event.client.call.update.CallRegionUpdateEvent;
import org.alienideology.jcord.event.client.call.update.CallWaitingUsersUpdateEvent;
import org.alienideology.jcord.event.client.call.user.CallUserStartWaitingEvent;
import org.alienideology.jcord.handle.Region;
import org.alienideology.jcord.handle.client.call.ICallUser;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.ObjectBuilder;
import org.alienideology.jcord.internal.object.client.Client;
import org.alienideology.jcord.internal.object.client.call.Call;
import org.alienideology.jcord.internal.object.client.call.CallUser;
import org.alienideology.jcord.internal.object.client.call.CallVoiceState;
import org.alienideology.jcord.util.log.LogLevel;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author AlienIdeology
 */
public class CallUpdateEventHandler extends EventHandler {

    public CallUpdateEventHandler(IdentityImpl identity) {
        super(identity);
    }

    @Override
    public void dispatchEvent(JSONObject json, int sequence) {
        Client client = identity.getClient();
        Call call = new ObjectBuilder(client).buildCall(json);
        Call oldCall = (Call) call.getChannel().getCurrentCall();

        if (oldCall == null) {
            identity.LOG.log(LogLevel.FETAL, "[UNKNOWN CALL] [CALL_UPDATE_EVENT]");
            return;
        }

        // The call(new) instance is built without any user information
        // So we add all users to it, then change the cache later on
        call.getWaitingUsers().addAll(oldCall.getWaitingUsers());
        call.getConnectedUsers().addAll(oldCall.getWaitingUsers());

        if (!Objects.equals(oldCall.getRegion(), call.getRegion())) {
            Region region = oldCall.getRegion();
            oldCall.setRegion(call.getRegion());
            dispatchEvent(new CallRegionUpdateEvent(client, sequence, oldCall, region));
        }

        List<ICallUser> stopWaitingUsers = new ArrayList<>();
        List<ICallUser> startWaitingUsers = new ArrayList<>();

        for (ICallUser user : oldCall.getWaitingUsers()) {
            // This can essentially means a user join call, stop waiting
            // Or denied the call request
            // So we just set as much information as possible
            // Until a voice state event
            if (!call.getWaitingUsers().contains(user)) {
                // Set voice states, and remove the user from call (Since call has the same user info as oldCall, initially)
                ((CallVoiceState) user.getVoiceState()).setWaiting(false);
                call.getWaitingUsers().remove(user);

                stopWaitingUsers.add(user);

                // Cannot do:
                // 1. ((CallVoiceState) user.getVoiceState()).setInCall(true);
                // 2. call.addUser(user);
                // Since the user might deny the call
            }
        }

        for (ICallUser user : call.getWaitingUsers()) {
            // User join, start waiting
            if (!oldCall.getWaitingUsers().contains(user)) {
                ((CallVoiceState) user.getVoiceState()).setInCall(false);
                ((CallVoiceState) user.getVoiceState()).setWaiting(true);

                startWaitingUsers.add(user);
                call.addWaitingUsers(user);
                client.getCallUsers().put(user.getUser().getId(), (CallUser) user);

                dispatchEvent(new CallUserStartWaitingEvent(client, sequence, call, (CallUser) user));
            }
        }

        if (!stopWaitingUsers.isEmpty() || !startWaitingUsers.isEmpty()) {
            dispatchEvent(new CallWaitingUsersUpdateEvent(client, sequence, call, stopWaitingUsers, startWaitingUsers));
        }

    }

}
