package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.IdentityType;
import org.alienideology.jcord.event.client.call.user.CallUserJoinEvent;
import org.alienideology.jcord.event.client.call.user.CallUserLeaveEvent;
import org.alienideology.jcord.event.client.call.user.CallUserSelfDeafenEvent;
import org.alienideology.jcord.event.client.call.user.CallUserSelfMuteEvent;
import org.alienideology.jcord.handle.channel.ICallChannel;
import org.alienideology.jcord.handle.client.call.ICall;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.VoiceState;
import org.alienideology.jcord.internal.object.client.Client;
import org.alienideology.jcord.internal.object.client.call.Call;
import org.alienideology.jcord.internal.object.client.call.CallUser;
import org.alienideology.jcord.internal.object.client.call.CallVoiceState;
import org.alienideology.jcord.util.log.LogLevel;
import org.json.JSONObject;

import java.util.Objects;

/**
 * @author AlienIdeology
 */
public class VoiceStateUpdateEventHandler extends EventHandler {

    public VoiceStateUpdateEventHandler(IdentityImpl identity) {
        super(identity);
    }

    @Override
    public void dispatchEvent(JSONObject json, int sequence) {
        boolean isCallVoiceState = !json.has("guild_id") && identity.getType().equals(IdentityType.CLIENT);

        if (isCallVoiceState) {
            System.out.println(json.toString(4));
            Client client = identity.getClient();

            IUser user = identity.getUser(json.getString("user_id"));
            if (user == null) {
                identity.LOG.log(LogLevel.FETAL, "[UNKNOWN USER][VOICE_STATE_UPDATE_EVENT]");
                return;
            }

            VoiceState state = builder.buildVoiceState(json);
            CallUser cUser = identity.getClient().getCallUsers().get(json.getString("user_id"));

            if (cUser == null) {
                identity.LOG.log(LogLevel.FETAL, "[UNKNOWN CALL USER][VOICE_STATE_UPDATE_EVENT]");
                return;
            }

            VoiceState oldState = (VoiceState) cUser.getVoiceState();
            cUser.setVoiceState(state);

            // Leave a call
            if (state.getChannel() == null) {
                cUser.getCall().getConnectedUsers().remove(cUser);
                ((CallVoiceState) cUser.getVoiceState()).setInCall(false);
                ((CallVoiceState) cUser.getVoiceState()).setWaiting(false);
                dispatchEvent(new CallUserLeaveEvent(client, sequence, (Call) cUser.getCall(), cUser, (ICallChannel) oldState.getChannel()));

            // Join a call
            } else if (oldState.getChannel() == null) {
                ICall call = client.getCallChannel(json.getString("channel_id")).getCurrentCall();
                if (call == null) {
                    identity.LOG.log(LogLevel.FETAL, "[UNKNOWN CALL][VOICE_STATE_UPDATE_EVENT]");
                    return;
                }

                // Call user were cached by ringing array in CallCreateEvent
                call.getConnectedUsers().add(cUser);
                call.getWaitingUsers().remove(cUser); // This might not make any changes
                ((CallVoiceState) cUser.getVoiceState()).setInCall(true);
                ((CallVoiceState) cUser.getVoiceState()).setWaiting(false);
                dispatchEvent(new CallUserJoinEvent(client, sequence, (Call) cUser.getCall(), cUser));
            }

            if (!Objects.equals(state.isSelfMute(), oldState.isSelfMute())) {
                dispatchEvent(new CallUserSelfMuteEvent(client, sequence, (Call) cUser.getCall(), cUser));
            }

            if (!Objects.equals(state.isSelfDeafened(), oldState.isSelfDeafened())) {
                dispatchEvent(new CallUserSelfDeafenEvent(client, sequence, (Call) cUser.getCall(), cUser));
            }

        } else {
            // TODO: Parse IGuildVoiceState
        }

    }

//            try {
//                VoiceState state = builder.buildVoiceState(json);
//                ICallVoiceState oldState;
//
//                CallUser cUser = identity.getClient().getCallUsers().get(json.getString("user_id"));
//                // Encounter new user, which means this user joined the call
//                if (cUser == null) {
//                    // So we copy the old state as same as the new state, so no event will get fired
//                    oldState = new CallVoiceState(client, state);
//                    // But we set old state channel to null, so CallUserJoinEvent will get fired
//                    ((CallVoiceState) oldState).setChannel(null);
//                    cUser = new CallUser(client, (Call) call);
//
//                    client.getCallUsers().put(cUser.getUser().getId(), cUser);
//
//                    // Move from another call, or mute/deaf update
//                    // If move from another call, CallUserJoinEvent is dispatched at CallCreateEventHandler
//                } else {
//                    oldState = cUser.getVoiceState();
//                }
//
//                cUser.setVoiceState(state);
//
//                System.out.println(state.getChannel() + "\t" + oldState.getChannel());
//
//                if (!Objects.equals(state.getChannel(), oldState.getChannel())) {
//
//                    // User Join
//                    // From waiting -> join
//                    // But discord don't always fires CallUpdateEvent, so the waiting status might be unknown
//                    if (oldState.getChannel() == null) {
//                        call.getConnectedUsers().add(cUser);
//                        call.getWaitingUsers().remove(cUser); // This might not make any changes
//                        dispatchEvent(new CallUserJoinEvent(client, sequence, (Call) cUser.getCall(), cUser));
//
//                        // User Leave
//                        // Does not include deny call
//                    } else if (state.getChannel() == null) {
//                        call.getConnectedUsers().remove(cUser);
//                        dispatchEvent(new CallUserLeaveEvent(client, sequence, (Call) cUser.getCall(), cUser, (ICallChannel) oldState.getChannel()));
//                    }
//                }

}
