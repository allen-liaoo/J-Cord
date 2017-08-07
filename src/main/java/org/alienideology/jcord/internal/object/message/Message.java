package org.alienideology.jcord.internal.object.message;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.channel.IMessageChannel;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IMember;
import org.alienideology.jcord.handle.guild.IRole;
import org.alienideology.jcord.handle.message.IEmbed;
import org.alienideology.jcord.handle.message.IMessage;
import org.alienideology.jcord.handle.message.IReaction;
import org.alienideology.jcord.handle.message.MessageProcessor;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.object.DiscordObject;
import org.alienideology.jcord.internal.object.Jsonable;
import org.alienideology.jcord.internal.object.channel.MessageChannel;
import org.alienideology.jcord.internal.object.guild.Role;
import org.alienideology.jcord.internal.object.user.User;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author AlienIdeology
 */
public final class Message extends DiscordObject implements IMessage, Jsonable {

    private MessageChannel channel;

    private final String id;
    private final IUser author;

    private MessageProcessor messageProcessor;
    private String content;
    private Type type;
    private OffsetDateTime createdTime;

    private List<IEmbed> embeds;
    private List<User> mentions;
    private List<Role> mentionedRoles;
    private List<Attachment> attachments;
    private List<IReaction> reactions;

    private boolean isTTS;
    private boolean mentionedEveryone;
    private boolean isPinned;

    public Message(Identity identity, String id, IUser author) {
        super(identity);
        this.id = id;
        this.author = author;
        this.embeds = new ArrayList<>();
        this.messageProcessor = new MessageProcessor(this);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject()
                .put("content", content);
        if (isTTS) json.put("tts", true);
        if (!this.embeds.isEmpty()) {
            // Only get the first embed's json
            // Because the identity can not send multiple embeds as http request
            json.put("embed", ((Embed) embeds.get(0)).toJson());
        }
        return json;
    }

    @Override
    public boolean canSend() {
        return content.length() <= CONTENT_LENGTH_MAX;
    }

    @Override
    public MessageProcessor getMessageProcessor() {
        return messageProcessor;
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
    public Type getType() {
        return type;
    }

    @Override
    public IUser getAuthor() {
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
    public List<IEmbed> getEmbeds() {
        return embeds;
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
        return embeds == null || embeds.isEmpty();
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
    public int compareTo(IMessage o) {
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
                ", key='" + id + '\'' +
                ", author=" + author +
                ", content='" + content + '\'' +
                '}';
    }

    public Message setChannel(MessageChannel channel) {
        this.channel = channel;
        return this;
    }

    public Message setContent(String content) {
        this.content = content;
        return this;
    }

    public Message setType(int type) {
        this.type = Type.getByKey(type);
        return this;
    }

    public Message setCreatedTime(String createdTime) {
        this.createdTime = createdTime == null ? null : OffsetDateTime.parse(createdTime);
        return this;
    }

    public Message addEmbed(IEmbed embeds) {
        this.embeds.add(embeds);
        return this;

    }

    public Message setMentions(List<User> mentions) {
        this.mentions = mentions;
        return this;
    }

    public Message setMentionedRoles(List<Role> mentionedRoles) {
        this.mentionedRoles = mentionedRoles;
        return this;
    }

    public Message setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
        return this;
    }

    public Message setReactions(List<IReaction> reactions) {
        this.reactions = reactions;
        return this;
    }

    public Message addReaction(IReaction reaction) {
        this.reactions.add(reaction);
        return this;
    }

    public Message removeReaction(IReaction reaction) {
        this.reactions.remove(reaction);
        return this;
    }

    public Message setTTS(boolean TTS) {
        isTTS = TTS;
        return this;
    }

    public Message setMentionedEveryone(boolean mentionedEveryone) {
        this.mentionedEveryone = mentionedEveryone;
        return this;
    }

    public Message setPinned(boolean pinned) {
        isPinned = pinned;
        return this;
    }
}
