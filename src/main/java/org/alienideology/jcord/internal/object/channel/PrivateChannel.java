package org.alienideology.jcord.internal.object.channel;

import org.alienideology.jcord.handle.channel.IPrivateChannel;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.user.User;
import org.alienideology.jcord.internal.object.Message;

import java.util.Objects;

/**
 * @author AlienIdeology
 */
public class PrivateChannel extends MessageChannel implements IPrivateChannel {

    private final User recipient;

    public PrivateChannel(IdentityImpl identity, String id, User recipient, Message lastMessage) {
        super(identity, id, Type.PRIVATE, lastMessage);
        this.recipient = recipient;
    }

    @Override
    public IUser getRecipient() {
        return recipient;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof PrivateChannel) && Objects.equals(this.id, ((PrivateChannel) obj).getId());
    }

    @Override
    public String toString() {
        return "ID: "+id+"\tRecipient: "+ recipient.getName();
    }

}
