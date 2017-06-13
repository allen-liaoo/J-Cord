package org.alienideology.jcord.event.message;

import com.sun.istack.internal.Nullable;
import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.Event;
import org.alienideology.jcord.object.user.User;
import org.alienideology.jcord.object.channel.Channel;
import org.alienideology.jcord.object.channel.MessageChannel;
import org.alienideology.jcord.object.channel.PrivateChannel;
import org.alienideology.jcord.object.channel.TextChannel;
import org.alienideology.jcord.object.Guild;
import org.alienideology.jcord.object.guild.Member;
import org.alienideology.jcord.object.Message;

/**
 * @author AlienIdeology
 */
public class MessageEvent extends Event {

    private final MessageChannel channel;
    private final Message message;

    public MessageEvent(Identity identity, int sequence, MessageChannel channel, Message message) {
        super(identity, sequence);
        this.channel = channel;
        this.message = message;
    }

    public String getMessageId() {
        return message.getId();
    }

    public Message getMessage() {
        return message;
    }

    public boolean isEmbed() {
        return message.isEmbed();
    }

    public MessageChannel getChannel() {
        return channel;
    }

    public Channel.Type getType() {
        return channel.getType();
    }

    public boolean fromType(Channel.Type type) {
        return channel.isType(type);
    }

    public User getUser() {
        return message.getAuthor();
    }

    @Nullable
    public Member getMember() {
        return message.getMember();
    }

    @Nullable
    public Guild getGuild() {
        return message.getGuild();
    }

    @Nullable
    public PrivateChannel getPrivateChannel() {
        return message.getChannel().isPrivate() ? (PrivateChannel) message.getChannel() : null;
    }

    @Nullable
    public TextChannel getTextChannel() {
        return message.getChannel().isPrivate() ? null : (TextChannel) message.getChannel();
    }

}