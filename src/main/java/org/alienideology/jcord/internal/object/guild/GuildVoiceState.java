package org.alienideology.jcord.internal.object.guild;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.IVoiceState;
import org.alienideology.jcord.handle.channel.IVoiceChannel;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IGuildVoiceState;
import org.alienideology.jcord.handle.guild.IMember;
import org.alienideology.jcord.internal.object.VoiceState;

/**
 * @author AlienIdeology
 */
public final class GuildVoiceState extends VoiceState implements IGuildVoiceState {

    private final IMember member;

    private boolean muted = false;
    private boolean deafened = false;
    private boolean suppressed = false;

    public GuildVoiceState(Identity identity, IMember member, IVoiceState state) {
        super(identity, member.getUser());
        this.member = member;
        setChannel(state.getChannel());
        setSessionId(state.getSessionId());
        setSelfMuted(state.isSelfMuted());
        setSelfDeafened(state.isSelfDeafened());
    }

    @Override
    public IGuild geGuild() {
        return member.getGuild();
    }

    @Override
    public IVoiceChannel getVoiceChannel() {
        return (IVoiceChannel) getChannel();
    }

    @Override
    public IMember getMember() {
        return member;
    }

    @Override
    public boolean isMutedByServer() {
        return muted;
    }

    @Override
    public boolean isDeafenedByServer() {
        return deafened;
    }

    @Override
    public boolean isSuppressed() {
        return suppressed;
    }

    //---------------------Internal---------------------

    public void setMuted(boolean muted) {
        this.muted = muted;
    }

    public void setDeafened(boolean deafened) {
        this.deafened = deafened;
    }

    public void setSuppressed(boolean suppressed) {
        this.suppressed = suppressed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GuildVoiceState)) return false;
        if (!super.equals(o)) return false;

        GuildVoiceState that = (GuildVoiceState) o;

        return member.equals(that.member);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + member.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "GuildVoiceState{" +
                "member=" + member +
                ", muted=" + muted +
                ", deafened=" + deafened +
                ", suppressed=" + suppressed +
                ", selfMute=" + selfMuted +
                ", selfDeafened=" + selfDeafened +
                '}';
    }

}

