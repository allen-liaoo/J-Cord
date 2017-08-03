package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.IdentityType;
import org.alienideology.jcord.event.client.call.user.CallUserJoinEvent;
import org.alienideology.jcord.event.client.call.user.CallUserLeaveEvent;
import org.alienideology.jcord.event.client.call.user.CallUserSelfDeafenEvent;
import org.alienideology.jcord.event.client.call.user.CallUserSelfMuteEvent;
import org.alienideology.jcord.event.guild.voice.*;
import org.alienideology.jcord.handle.channel.ICallChannel;
import org.alienideology.jcord.handle.client.call.ICall;
import org.alienideology.jcord.handle.guild.IGuildVoiceState;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.VoiceState;
import org.alienideology.jcord.internal.object.client.Client;
import org.alienideology.jcord.internal.object.client.call.Call;
import org.alienideology.jcord.internal.object.client.call.CallUser;
import org.alienideology.jcord.internal.object.client.call.CallVoiceState;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.guild.GuildVoiceState;
import org.alienideology.jcord.internal.object.guild.Member;
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

        IUser user = identity.getUser(json.getString("user_id"));
        if (user == null) {
            identity.LOG.log(LogLevel.FETAL, "[UNKNOWN USER][VOICE_STATE_UPDATE_EVENT]");
            return;
        }

        VoiceState state = builder.buildVoiceState(json);

        if (isCallVoiceState) {
            Client client = identity.getClient();

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

            if (!Objects.equals(state.isSelfMuted(), oldState.isSelfMuted())) {
                dispatchEvent(new CallUserSelfMuteEvent(client, sequence, (Call) cUser.getCall(), cUser));
            }

            if (!Objects.equals(state.isSelfDeafened(), oldState.isSelfDeafened())) {
                dispatchEvent(new CallUserSelfDeafenEvent(client, sequence, (Call) cUser.getCall(), cUser));
            }

        } else {
            Guild guild = (Guild) identity.getGuild(json.getString("guild_id"));
            if (guild == null) {
                identity.LOG.log(LogLevel.FETAL, "[UNKNOWN GUILD][VOICE_STATE_UPDATE_EVENT]");
                return;
            }

            Member member = (Member) guild.getMember(json.getString("user_id"));
            if (member == null) {
                identity.LOG.log(LogLevel.FETAL, "[UNKNOWN MEMBER][VOICE_STATE_UPDATE_EVENT]");
                return;
            }

            IGuildVoiceState oldVs = member.getVoiceState();
            GuildVoiceState newVs = new GuildVoiceState(identity, member, state);
            newVs.setMuted(json.getBoolean("mute"));
            newVs.setDeafened(json.getBoolean("deaf"));
            newVs.setSuppressed(json.getBoolean("suppress"));
            member.setVoiceState(newVs);

            if (!Objects.equals(oldVs.getVoiceChannel(), newVs.getVoiceChannel())) {
                if (oldVs.getVoiceChannel() == null) {
                    dispatchEvent(new GuildMemberJoinVoiceEvent(identity, sequence, guild, member));
                } else if (newVs.getVoiceChannel() == null) {
                    dispatchEvent(new GuildMemberLeaveVoiceEvent(identity, sequence, guild, member, oldVs.getVoiceChannel()));
                } else {
                    dispatchEvent(new GuildMemberMoveVoiceEvent(identity, sequence, guild, member, oldVs.getVoiceChannel()));
                }
            }

            if (!Objects.equals(oldVs.isMuted(), newVs.isMuted())) {
                dispatchEvent(new GuildMemberMuteEvent(identity, sequence, guild, member));
            }
            if (!Objects.equals(oldVs.isSelfMuted(), newVs.isSelfMuted())) {
                dispatchEvent(new GuildMemberSelfMuteEvent(identity, sequence, guild, member));
            }
            if (!Objects.equals(oldVs.isMutedByServer(), newVs.isMutedByServer())) {
                dispatchEvent(new GuildMemberMuteByServerEvent(identity, sequence, guild, member));
            }
            if (!Objects.equals(oldVs.isDeafened(), newVs.isDeafened())) {
                dispatchEvent(new GuildMemberDeafenEvent(identity, sequence, guild, member));
            }
            if (!Objects.equals(oldVs.isSelfDeafened(), newVs.isSelfDeafened())) {
                dispatchEvent(new GuildMemberSelfDeafenEvent(identity, sequence, guild, member));
            }
            if (!Objects.equals(oldVs.isDeafenedByServer(), newVs.isDeafenedByServer())) {
                dispatchEvent(new GuildMemberDeafenByServerEvent(identity, sequence, guild, member));
            }
            if (!Objects.equals(oldVs.isSuppressed(), newVs.isSuppressed())) {
                dispatchEvent(new GuildMemberSuppressEvent(identity, sequence, guild, member));
            }
        }

    }

}
