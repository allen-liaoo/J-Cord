package org.alienideology.jcord.object.message;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.object.DiscordObject;
import org.alienideology.jcord.object.SnowFlake;
import org.alienideology.jcord.object.User;
import org.alienideology.jcord.object.channel.Channel;
import org.alienideology.jcord.object.channel.MessageChannel;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author AlienIdeology
 */
public class Message extends DiscordObject implements SnowFlake {

    protected MessageChannel channel;

    protected final String id;
    protected final User author;

    private final OffsetDateTime createdTime;

    private List<User> mentions;
//    private List<Role> mentionedRoles;
//    private List<Attachment> attachments;
//    private List<Embeds> embeds;
//    private List<Emoji> reactions;

    private boolean isTTS;

    private boolean mentionedEveryone;
    private boolean isPinned;

    public Message (Identity identity, String channelId, String id, User author, String createdTime, List<User> mentions,
                    boolean isTTs, boolean mentionedEveryone, boolean isPinned) {
        super(identity);
        channel = identity.getTextChannel(channelId) == null ? identity.getPrivateChannel(channelId) : identity.getTextChannel(channelId);
        this.id = id;
        this.author = author;
        this.createdTime = createdTime == null ? null : OffsetDateTime.parse(createdTime);
        this.mentions = mentions;
        this.isTTS = isTTs;
        this.mentionedEveryone = mentionedEveryone;
        this.isPinned = isPinned;
    }

    public MessageChannel getChannel() {
        return channel;
    }

    public Channel.Type getFromType() {
        return channel.getType();
    }

    public boolean fromType(Channel.Type type) {
        return channel.getType().equals(type);
    }

    public User getAuthor() {
        return author;
    }

    public List<User> getMentionedUsers() {
        return mentions;
    }

    public OffsetDateTime getCreatedTime() {
        return createdTime;
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
    public boolean equals(Object obj) {
        return (obj instanceof Message) && (Objects.equals(this.id, ((Message) obj).getId()));
    }

    @Override
    public String toString() {
        return "ID: "+id;
    }

}
