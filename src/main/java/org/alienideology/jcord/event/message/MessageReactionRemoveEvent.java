package org.alienideology.jcord.event.message;

import com.sun.istack.internal.Nullable;
import org.alienideology.jcord.handle.EmojiTable;
import org.alienideology.jcord.handle.guild.IGuildEmoji;
import org.alienideology.jcord.handle.message.IReaction;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.MessageChannel;
import org.alienideology.jcord.internal.object.message.Message;
import org.alienideology.jcord.internal.object.message.Reaction;
import org.alienideology.jcord.internal.object.user.User;

/**
 * @author AlienIdeology
 */
public class MessageReactionRemoveEvent extends MessageEvent {

    private User user;
    private Reaction reaction;

    public MessageReactionRemoveEvent(IdentityImpl identity, int sequence, MessageChannel channel, Message message, User user, Reaction reaction) {
        super(identity, sequence, channel, message);
        this.user = user;
        this.reaction = reaction;
    }

    @Override
    public IUser getUser() {
        return user;
    }

    public IReaction getReaction() {
        return reaction;
    }

    @Nullable
    public EmojiTable.Emoji getEmoji() {
        return reaction.getEmoji();
    }

    @Nullable
    public IGuildEmoji getGuildEmoji() {
        return reaction.getGuildEmoji();
    }

    public boolean isGuildEmoji() {
        return reaction.isGuildEmoji();
    }

}
