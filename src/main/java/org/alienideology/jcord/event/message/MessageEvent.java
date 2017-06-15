package org.alienideology.jcord.event.message;

import com.sun.istack.internal.Nullable;
import org.alienideology.jcord.handle.channel.IChannel;
import org.alienideology.jcord.handle.channel.IMessageChannel;
import org.alienideology.jcord.handle.channel.IPrivateChannel;
import org.alienideology.jcord.handle.channel.ITextChannel;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IMember;
import org.alienideology.jcord.handle.message.IMessage;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.event.Event;
import org.alienideology.jcord.internal.object.channel.MessageChannel;
import org.alienideology.jcord.internal.object.channel.PrivateChannel;
import org.alienideology.jcord.internal.object.channel.TextChannel;
import org.alienideology.jcord.internal.object.message.Message;

/**
 * @author AlienIdeology
 */
public class MessageEvent extends Event {

    private final IMessageChannel channel;
    private final IMessage message;

    public MessageEvent(IdentityImpl identity, int sequence, MessageChannel channel, Message message) {
        super(identity, sequence);
        this.channel = channel;
        this.message = message;
    }

    public String getMessageId() {
        return message.getId();
    }

    public IMessage getMessage() {
        return message;
    }

    public boolean isEmbed() {
        return message.isEmbed();
    }

    public IMessageChannel getChannel() {
        return channel;
    }

    public IChannel.Type getType() {
        return channel.getType();
    }

    public boolean fromType(IChannel.Type type) {
        return channel.isType(type);
    }

    public IUser getUser() {
        return message.getAuthor();
    }

    @Nullable
    public IMember getMember() {
        return message.getMember();
    }

    @Nullable
    public IGuild getGuild() {
        return message.getGuild();
    }

    @Nullable
    public IPrivateChannel getPrivateChannel() {
        return message.getChannel().isPrivate() ? (PrivateChannel) message.getChannel() : null;
    }

    @Nullable
    public ITextChannel getTextChannel() {
        return message.getChannel().isPrivate() ? null : (TextChannel) message.getChannel();
    }

}
