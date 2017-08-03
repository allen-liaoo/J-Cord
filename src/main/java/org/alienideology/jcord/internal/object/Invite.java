package org.alienideology.jcord.internal.object;

import org.alienideology.jcord.handle.IInvite;
import org.alienideology.jcord.handle.channel.IGuildChannel;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.user.User;

import java.time.OffsetDateTime;

/**
 * @author AlienIdeology
 */
public final class Invite implements IInvite {

    private final String code;

    /* IInvite Source */
    private Guild guild;
    private IGuildChannel channel;

    /* MetaData Object */
    private User inviter;
    private int uses;
    private int maxUses;
    private long maxAge;
    private boolean isTemporary;
    private boolean isRevoked;
    private OffsetDateTime timeStamp;

    public Invite(String code, Guild guild, IGuildChannel channel) {
        this.code = code;
        this.guild = guild;
        this.channel = channel;
    }

    public Invite setMetaData(User inviter, int uses, int maxUses, long maxAge, boolean isTemporary, boolean isRevoked, String timeStamp) {
        this.inviter = inviter;
        this.uses = uses;
        this.maxUses = maxUses;
        this.maxAge = maxAge;
        this.isTemporary = isTemporary;
        this.isRevoked = isRevoked;
        this.timeStamp = OffsetDateTime.parse(timeStamp);
        return this;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    @Override
    public IGuildChannel getChannel() {
        return channel;
    }

    @Override
    public User getInviter() {
        return inviter;
    }

    @Override
    public int getUses() {
        return uses;
    }

    @Override
    public int getMaxUses() {
        return maxUses;
    }

    @Override
    public long getMaxAge() {
        return maxAge;
    }

    @Override
    public boolean isTemporary() {
        return isTemporary;
    }

    @Override
    public boolean isRevoked() {
        return isRevoked;
    }

    @Override
    public OffsetDateTime getTimeStamp() {
        return timeStamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Invite)) return false;

        Invite invite = (Invite) o;

        if (!code.equals(invite.code)) return false;
        return timeStamp != null ? timeStamp.equals(invite.timeStamp) : invite.timeStamp == null;
    }

    @Override
    public int hashCode() {
        int result = code.hashCode();
        result = 31 * result + (timeStamp != null ? timeStamp.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "IInvite{" +
                "code='" + code + '\'' +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
