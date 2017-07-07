package org.alienideology.jcord.event.message;

import org.alienideology.jcord.handle.emoji.Emoji;
import org.alienideology.jcord.handle.guild.IGuildEmoji;
import org.alienideology.jcord.handle.message.IReaction;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.MessageChannel;
import org.alienideology.jcord.internal.object.message.Message;
import org.alienideology.jcord.internal.object.message.Reaction;
import org.alienideology.jcord.internal.object.user.User;
import org.jetbrains.annotations.Nullable;

/**
 * @author AlienIdeology
 */
public class MessageReactionAddEvent extends MessageEvent {

    private User user;
    private Reaction reaction;

    public MessageReactionAddEvent(IdentityImpl identity, int sequence, MessageChannel channel, Message message, User user, Reaction reaction) {
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
    public Emoji getEmoji() {
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
