package org.alienideology.jcord.object.channel;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.object.User;
import org.alienideology.jcord.object.message.Message;

import java.util.Objects;

/**
 * PrivateChannel - A one-to-one channel between two users
 * @author AlienIdeology
 */
public class PrivateChannel extends Channel {

    private final User recipient;
    private final Type type;
    private Message lastMessage;

    public PrivateChannel(Identity identity, String id, User recipient) {
        super(identity, id, true);
        this.recipient = recipient;
        this.type = Type.PRIVATE;
    }

    public User getRecipient() {
        return recipient;
    }

    public Type getType() {
        return type;
    }

    public Message getLastMessage() {
        return lastMessage;
    }

    /**
     * [API Use Only]
     * @param lastMessage The last message of this channel.
     * @return PrivateChannel for chaining.
     */
    public PrivateChannel setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
        return this;
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
