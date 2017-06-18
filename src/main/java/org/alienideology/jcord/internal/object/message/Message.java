package org.alienideology.jcord.internal.object.message;

import com.sun.istack.internal.Nullable;
import org.alienideology.jcord.handle.channel.IMessageChannel;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IMember;
import org.alienideology.jcord.handle.guild.IRole;
import org.alienideology.jcord.handle.message.IMessage;
import org.alienideology.jcord.handle.message.IReaction;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.object.DiscordObject;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.MessageChannel;
import org.alienideology.jcord.internal.object.channel.PrivateChannel;
import org.alienideology.jcord.internal.object.channel.TextChannel;
import org.alienideology.jcord.internal.object.guild.Role;
import org.alienideology.jcord.internal.object.user.User;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author AlienIdeology
 */
public class Message extends DiscordObject implements IMessage {

    /* Constant */
    public final static int MAX_CONTENT_LENGTH = 2000;

    /* Instance Field */
    protected MessageChannel channel;

    protected final String id;
    protected final User author;

    protected String content;
    private final OffsetDateTime createdTime;

    protected List<User> mentions;
    protected List<Role> mentionedRoles;
    private List<Attachment> attachments;
    private List<IReaction> reactions;

    protected boolean isTTS;

    private boolean mentionedEveryone;
    private boolean isPinned;

    public Message (IdentityImpl identity, String id, User author, String content, String createdTime,
                    List<User> mentions, List<Role> mentionedRoles, List<Attachment> attachments, boolean isTTs, boolean mentionedEveryone, boolean isPinned) {
        super(identity);
        this.id = id;
        this.author = author;
        this.content = content;
        this.createdTime = createdTime == null ? null : OffsetDateTime.parse(createdTime);
        this.mentions = mentions;
        this.mentionedRoles = mentionedRoles;
        this.attachments = attachments;
        this.reactions = new ArrayList<>();
        this.isTTS = isTTs;
        this.mentionedEveryone = mentionedEveryone;
        this.isPinned = isPinned;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    @Nullable
    public IGuild getGuild() {
        if(!channel.isPrivate()) {
            return identity.getTextChannel(channel.getId()).getGuild();
        } else {
            return null;
        }
    }

    @Override
    public IMessageChannel getChannel() {
        return channel;
    }

    @Override
    public User getAuthor() {
        return author;
    }

    @Override
    @Nullable
    public IMember getMember() {
        if (!channel.isPrivate()) {
            return getGuild().getMember(author.getId());
        } else {
            return null;
        }
    }

    @Override
    public List<IUser> getMentions() {
        return Collections.unmodifiableList(mentions);
    }

    @Override
    @Nullable
    public List<IMember> getMentionedMembers() {
        if (channel.isPrivate()) {
            return null;
        } else {
            List<IMember> members = new ArrayList<>();
            IGuild guild = getGuild();
            for (User user : mentions) {
                members.add(guild.getMember(user.getId()));
            }
            return members;
        }
    }

    @Override
    public List<IRole> getMentionedRoles() {
        return Collections.unmodifiableList(mentionedRoles);
    }

    @Override
    public OffsetDateTime getCreatedTime() {
        return createdTime;
    }

    @Override
    public List<Attachment> getAttachments() {
        return attachments;
    }

    @Override
    public List<IReaction> getReactions() {
        return reactions;
    }

    @Override
    public boolean isEmbed() {
        return !(this instanceof StringMessage);
    }

    @Override
    public boolean isTTS() {
        return isTTS;
    }

    @Override
    public boolean isMentionedEveryone() {
        return mentionedEveryone;
    }

    @Override
    public boolean isPinned() {
        return isPinned;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public int compareTo(Message o) {
        return createdTime.compareTo(o.getCreatedTime());
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Message) && (Objects.equals(this.id, ((Message) obj).getId()));
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (author != null ? author.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Message{" +
                "channel=" + channel +
                ", id='" + id + '\'' +
                ", author=" + author +
                '}';
    }

    public Message setChannel(String channel) {
        this.channel = identity.getTextChannel(channel) == null ?
                (PrivateChannel) identity.getPrivateChannel(channel) : (TextChannel) identity.getTextChannel(channel);
        return this;
    }

    public void setReactions(List<IReaction> reactions) {
        this.reactions = reactions;
    }

}
