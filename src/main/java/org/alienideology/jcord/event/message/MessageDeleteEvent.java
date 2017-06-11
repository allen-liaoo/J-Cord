package org.alienideology.jcord.event.message;


import com.sun.istack.internal.Nullable;
import org.alienideology.jcord.Identity;
import org.alienideology.jcord.object.Message;
import org.alienideology.jcord.object.channel.MessageChannel;

/**
 * @author AlienIdeology
 */
public class MessageDeleteEvent extends MessageEvent {

    private String id;

    public MessageDeleteEvent(Identity identity, int sequence, MessageChannel channel, String id) {
        super(identity, sequence, channel, null);
        this.id = id;
    }

    @Override
    public String getMessageId() {
        return id;
    }

    /**
     * @return Always null because the message is deleted.
     */
    @Override
    @Nullable
    public Message getMessage() {
        return super.getMessage();
    }
}
