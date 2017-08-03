package org.alienideology.jcord.internal.object.channel;

import org.alienideology.jcord.handle.channel.IPrivateChannel;
import org.alienideology.jcord.handle.client.call.ICall;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.client.call.Call;
import org.alienideology.jcord.internal.object.user.User;

import java.util.Objects;

/**
 * @author AlienIdeology
 */
public final class PrivateChannel extends MessageChannel implements IPrivateChannel {

    private final User recipient;

    private Call currentCall;

    public PrivateChannel(IdentityImpl identity, String id, User recipient) {
        super(identity, id, Type.DM);
        this.recipient = recipient;
    }

    @Override
    public IUser getRecipient() {
        return recipient;
    }

    @Override
    public ICall getCurrentCall() {
        return currentCall;
    }

    public void setCurrentCall(Call call) {
        this.currentCall = call;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof PrivateChannel) && Objects.equals(this.id, ((PrivateChannel) obj).getId());
    }

    @Override
    public String toString() {
        return "PrivateChannel{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", recipient=" + recipient +
                '}';
    }

}
