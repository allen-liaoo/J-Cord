package org.alienideology.jcord.internal.object;

import com.sun.istack.internal.Nullable;
import org.alienideology.jcord.internal.Identity;
import org.alienideology.jcord.internal.Internal;
import org.alienideology.jcord.internal.object.guild.Role;
import org.alienideology.jcord.internal.object.message.Reaction;
import org.alienideology.jcord.internal.object.message.StringMessage;
import org.alienideology.jcord.internal.object.user.User;
import org.alienideology.jcord.internal.object.channel.Channel;
import org.alienideology.jcord.internal.object.channel.MessageChannel;
import org.alienideology.jcord.internal.object.guild.Member;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author AlienIdeology
 */
public class Message extends DiscordObject implements SnowFlake, Comparable<Message> {

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
    private List<Reaction> reactions;

    private boolean isTTS;

    private boolean mentionedEveryone;
    private boolean isPinned;

    public Message (Identity identity, String id, User author, String content, String createdTime,
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

    /**
     * Edit this message
     * @see MessageChannel#editMessage(String, String)
     * @param content The new content
     * @return The message edited.
     */
    public Message edit(String content) {
        return channel.editMessage(id, content);
    }

    /**
     * Delete this message
     * @see MessageChannel#deleteMessage(String)
     * @return The message deleted.
     */
    public Message delete() {
        return channel.deleteMessage(id);
    }

    /**
     * Pin this message
     * @see MessageChannel#pinMessage(String)
     */
    public void pin() {
        channel.pinMessage(id);
    }

    public String getContent() {
        return content;
    }

    @Nullable
    public Guild getGuild() {
        if(!channel.isPrivate()) {
            return identity.getTextChannel(channel.getId()).getGuild();
        } else {
            return null;
        }
    }

    public MessageChannel getChannel() {
        return channel;
    }

    public Channel.Type getFromType() {
        return channel.getType();
    }

    public boolean fromType(Channel.Type type) {
        if (channel == null) return false;
        return channel.getType().equals(type);
    }

    public User getAuthor() {
        return author;
    }

    public boolean isFromSelf() {
        return author.equals(identity.getSelf());
    }

    @Nullable
    public Member getMember() {
        if (!channel.isPrivate()) {
            return getGuild().getMember(author.getId());
        } else {
            return null;
        }
    }

    public List<User> getMentions() {
        return mentions;
    }

    @Nullable
    public List<Member> getMentionedMembers() {
        if (channel.isPrivate()) {
            return null;
        } else {
            List<Member> members = new ArrayList<>();
            Guild guild = getGuild();
            for (User user : mentions) {
                members.add(guild.getMember(user.getId()));
            }
            return members;
        }
    }

    public List<Role> getMentionedRoles() {
        return mentionedRoles;
    }

    public OffsetDateTime getCreatedTime() {
        return createdTime;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public List<Reaction> getReactions() {
        return reactions;
    }

    public boolean isEmbed() {
        return !(this instanceof StringMessage);
    }

    public boolean isTTS() {
        return isTTS;
    }

    public boolean isMentionedEveryone() {
        return mentionedEveryone;
    }

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
        return "ID: "+id;
    }

    @Internal
    protected Message setChannel(String channel) {
        this.channel = identity.getTextChannel(channel) == null ?
                identity.getPrivateChannel(channel) : identity.getTextChannel(channel);
        return this;
    }

    @Internal
    protected void setReactions(List<Reaction> reactions) {
        this.reactions = reactions;
    }

    public static class Attachment implements SnowFlake {

        private final String id;

        private String filename;
        private int size;
        private String url;

        public Attachment(String id, String filename, int size, String url) {
            this.id = id;
            this.filename = filename;
            this.size = size;
            this.url = url;
        }

        @Override
        public String getId() {
            return id;
        }

    }

}
