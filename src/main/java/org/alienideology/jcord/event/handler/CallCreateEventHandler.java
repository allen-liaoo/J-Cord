package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.event.client.call.CallCreateEvent;
import org.alienideology.jcord.event.client.call.user.CallUserJoinEvent;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.ObjectBuilder;
import org.alienideology.jcord.internal.object.VoiceState;
import org.alienideology.jcord.internal.object.channel.Group;
import org.alienideology.jcord.internal.object.channel.PrivateChannel;
import org.alienideology.jcord.internal.object.client.Client;
import org.alienideology.jcord.internal.object.client.call.Call;
import org.alienideology.jcord.internal.object.client.call.CallUser;
import org.alienideology.jcord.internal.object.client.call.CallVoiceState;
import org.alienideology.jcord.util.log.LogLevel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author AlienIdeology
 */
public class CallCreateEventHandler extends EventHandler {

    public CallCreateEventHandler(IdentityImpl identity) {
        super(identity);
    }

    @Override
    public void dispatchEvent(JSONObject json, int sequence) {
        Client client = identity.getClient();
        Call call = new ObjectBuilder(client).buildCall(json);

        if (call.isGroupCall()) {
            ((Group) call.getGroup()).setCurrentCall(call);
        } else {
            ((PrivateChannel) call.getPrivateChannel()).setCurrentCall(call);
        }

        /* Voice States */
        // Users represented in the voice states array are users that already joined the call
        // Therefore dispatch CallUserJoinEvent
        List<CallUserJoinEvent> joinEvents = new ArrayList<>(); // Cache events to dispatch, then dispatch them after CallCreateEvent to ensure the order
        JSONArray voiceStates = json.getJSONArray("voice_states");
        for (int i = 0; i < voiceStates.length(); i++) {
            JSONObject vs = voiceStates.getJSONObject(i);
            String id = vs.getString("user_id");
            IUser user = identity.getUser(id);
            if (user != null) {
                if (client.getCallUsers().get(id) != null) {
                    identity.LOG.log(LogLevel.FETAL, "Encounter a user that is already in call! ID: " + id);
                    continue;
                }

                CallUser in = new CallUser(identity.getClient(), call);

                in.setVoiceState(builder.buildVoiceState(vs));
                ((CallVoiceState) in.getVoiceState()).setInCall(true);
                ((CallVoiceState) in.getVoiceState()).setWaiting(false);

                call.addUser(in);
                client.getCallUsers().put(id, in);

                joinEvents.add(new CallUserJoinEvent(client, sequence, call, in));

            } else {
                identity.LOG.log(LogLevel.FETAL, "[UNKNOWN VOICE STATE USER][CALL_CREATE_EVENT] ID: " + voiceStates.getString(i));
            }
        }

        /* Ringing */
        JSONArray ringings = json.getJSONArray("ringing");
        for (int i = 0; i < ringings.length(); i++) {
            String id = ringings.getString(i);
            IUser user = identity.getUser(id);
            if (user != null) {
                if (client.getCallUsers().get(id) != null) {
                    identity.LOG.log(LogLevel.FETAL, "Encounter a user that is already in call! ID: " + id);
                    continue;
                }

                CallUser waiting = new CallUser(identity.getClient(), call);

                waiting.setVoiceState(new VoiceState(identity, user));
                ((CallVoiceState) waiting.getVoiceState()).setInCall(false);
                ((CallVoiceState) waiting.getVoiceState()).setWaiting(true);

                call.addWaitingUsers(waiting);
                client.getCallUsers().put(id, waiting);
            } else {
                identity.LOG.log(LogLevel.FETAL, "[UNKNOWN USER][CALL_CREATE_EVENT] ID: " + ringings.getString(i));
            }
        }

        dispatchEvent(new CallCreateEvent(identity.getClient(), sequence, call));
        joinEvents.forEach(this::dispatchEvent);
    }

}
