package org.alienideology.jcord.internal.object;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.IVoiceState;
import org.alienideology.jcord.handle.channel.IAudioChannel;
import org.alienideology.jcord.handle.user.IUser;

/**
 * @author AlienIdeology
 */
public class VoiceState extends DiscordObject implements IVoiceState {

    private IUser user;
    private IAudioChannel channel;
    private String sessionId;

    protected boolean selfMuted = false;
    protected boolean selfDeafened = false;

    public VoiceState(Identity identity, IUser user) {
        super(identity);
        this.user = user;
    }

    @Override
    public IUser getUser() {
        return user;
    }

    @Override
    public IAudioChannel getChannel() {
        return channel;
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }

    @Override
    public boolean isSelfMuted() {
        return selfMuted;
    }

    @Override
    public boolean isSelfDeafened() {
        return selfDeafened;
    }

    //---------------------Internal---------------------

    public void setChannel(IAudioChannel channel) {
        this.channel = channel;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setSelfMuted(boolean selfMuted) {
        this.selfMuted = selfMuted;
    }

    public void setSelfDeafened(boolean selfDeafened) {
        this.selfDeafened = selfDeafened;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VoiceState)) return false;
        if (!super.equals(o)) return false;

        VoiceState state = (VoiceState) o;

        if (!user.equals(state.user)) return false;
        if (channel != null ? !channel.equals(state.channel) : state.channel != null) return false;
        return sessionId.equals(state.sessionId);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + user.hashCode();
        result = 31 * result + (channel != null ? channel.hashCode() : 0);
        result = 31 * result + sessionId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "VoiceState{" +
                "user=" + user +
                ", channel=" + channel +
                ", selfMuted=" + selfMuted +
                ", selfDeafened=" + selfDeafened +
                '}';
    }


}
