package org.alienideology.jcord.event.message;

import org.alienideology.jcord.handle.message.IReaction;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.MessageChannel;
import org.alienideology.jcord.internal.object.message.Message;

import java.util.List;

/**
 * @author AlienIdeology
 */
public class MessageReactionRemoveAllEvent extends MessageEvent {

    private List<IReaction> reactions;

    public MessageReactionRemoveAllEvent(IdentityImpl identity, int sequence, MessageChannel channel, Message message, List<IReaction> reactions) {
        super(identity, sequence, channel, message);
        this.reactions = reactions;
    }

    public List<IReaction> getReactions() {
        return reactions;
    }

}
