package org.alienideology.jcord.event.message;

import org.alienideology.jcord.event.Event;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.MessageChannel;

import java.util.List;

/**
 * @author AlienIdeology
 */
public class MessageBulkDeleteEvent extends Event {

    private MessageChannel channel;
    private List<String> messagesId;

    public MessageBulkDeleteEvent(IdentityImpl identity, int sequence, MessageChannel channel, List<String> messagesId) {
        super(identity, sequence);
        this.channel = channel;
        this.messagesId = messagesId;
    }

    public MessageChannel getChannel() {
        return channel;
    }

    /**
     * Get a list of IDs of all the message deleted.
     * Note that the messages may not be returned from invoking {@link MessageChannel#getMessage(String)}.
     *
     * @return A list of messages' IDs.
     */
    public List<String> getMessagesIdList() {
        return messagesId;
    }

}
