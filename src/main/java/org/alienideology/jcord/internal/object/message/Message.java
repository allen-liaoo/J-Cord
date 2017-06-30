package org.alienideology.jcord.internal.object.message;

import com.sun.istack.internal.Nullable;
import org.alienideology.jcord.handle.channel.IMessageChannel;
import org.alienideology.jcord.handle.channel.ITextChannel;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IGuildEmoji;
import org.alienideology.jcord.handle.guild.IMember;
import org.alienideology.jcord.handle.guild.IRole;
import org.alienideology.jcord.handle.message.IEmbed;
import org.alienideology.jcord.handle.message.IMessage;
import org.alienideology.jcord.handle.message.IReaction;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.object.Buildable;
import org.alienideology.jcord.internal.object.DiscordObject;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.MessageChannel;
import org.alienideology.jcord.internal.object.guild.Role;
import org.alienideology.jcord.internal.object.user.User;
import org.json.JSONObject;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author AlienIdeology
 */
public class Message extends DiscordObject implements IMessage, Buildable {

    /* Instance Field */
    private MessageChannel channel;

    private final String id;
    private final User author;

    private String content;
    private final OffsetDateTime createdTime;

    private List<IEmbed> embeds;
    private List<User> mentions;
    private List<Role> mentionedRoles;
    private List<Attachment> attachments;
    private List<IReaction> reactions;

    private boolean isTTS;
    private boolean mentionedEveryone;
    private boolean isPinned;

    public Message (IdentityImpl identity, String id, User author, String content, String createdTime,
                    List<User> mentions, List<Role> mentionedRoles, List<Attachment> attachments, boolean isTTs, boolean mentionedEveryone, boolean isPinned) {
        super(identity);
        this.id = id;
        this.author = author;
        this.content = content;
        this.createdTime = createdTime == null ? null : OffsetDateTime.parse(createdTime);
        this.embeds = new ArrayList<>();
        this.mentions = mentions;
        this.mentionedRoles = mentionedRoles;
        this.attachments = attachments;
        this.reactions = new ArrayList<>();
        this.isTTS = isTTs;
        this.mentionedEveryone = mentionedEveryone;
        this.isPinned = isPinned;
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
    public String getContent() {
        return content;
    }

    public String getProcessedContent(boolean noMention, boolean noMarkdown) {
        String process = content;
        if (noMention) {

            /* Message from TextChannel */
            if (!channel.isPrivate()) {
                /* Member Mentions */
                for (IMember member : getMentionedMembers()) {
                    process = process.replaceAll(member.mention(), "@" +
                            (member.getNickname().isEmpty()?member.getUser().getName():member.getNickname())
                            +"#"+member.getUser().getDiscriminator());
                }

                /* TextChannel Mentions */
                for (ITextChannel tc : getGuild().getTextChannels()) {
                    process = process.replaceAll(tc.mention(), "#"+tc.getName());
                }

                /* Role Mentions */
                for (Role role : mentionedRoles) {
                    process = process.replaceAll(role.mention(), "@"+role.getName());
                }

                for (IGuildEmoji emoji : getGuild().getGuildEmojis()) {
                    process = process.replaceAll(emoji.mention(), ":"+emoji.getName()+":");
                }

            /* Message from PrivateChannel */
            } else {
                /* User Mentions */
                for (User user : mentions) {
                    process = process.replaceAll(user.mention(), "@"+user.getName()+"#"+user.getDiscriminator());
                }
            }

            // TODO: EmojiTable Mentions

        }

        if (noMarkdown) {
            process = stripSimpleMarkdown(process);
//            final String codeBlock = "```";
//
//            String temp = "";
//            int lastClose = 0;
//
//            /* Code Blocks */
//            while (process.contains(codeBlock)) {
//                int open = process.indexOf(codeBlock);
//                int close = process.lastIndexOf(codeBlock);
//
//                /* Simple Markdowns */
//                temp += stripSimpleMarkdown(process.substring(lastClose, open));
//
//                System.out.println(open+"\t"+close);
//                System.out.println(process.indexOf("\n"));
//
//                // Code block is matched
//                if (open != close) {
//                    String newString = process.substring(0, open) +
//                            process.substring((process.indexOf("\n") >= close-1 || !process.contains("\n")) ? open+3 : process.indexOf("\n"), close);
//                    if (process.length() > close+3)
//                        newString += process.substring(close);
//                    temp += newString;
//                } else {
//                    break;
//                }
//                temp += stripSimpleMarkdown(process.substring(close));
//                lastClose = close;
        }
//            process = temp;

//        }
        return process;
    }

    private String stripSimpleMarkdown (String md) {
        final String[] MARKDOWNS = new String[] {"***", "**", "*", "~~", "__"};
        for (String markdown : MARKDOWNS) {
            System.out.println(markdown);
            while (md.contains(markdown)) {
                int open = md.indexOf(markdown);
                int close = md.lastIndexOf(markdown);
                // Markdown is matched
                if (open != close) {
                    String newString = md.substring(0, open) + md.substring(open+markdown.length(), close);
                    if (md.length() > close+markdown.length())
                        newString += md.substring(close);
                    System.out.println(newString);
                    md = newString;
                } else {
                    break;
                }
            }
        }
        return md;
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
                ", id='" + id + '\'' +
                ", author=" + author +
                ", content='" + content + '\'' +
                '}';
    }

    public Message setChannel(MessageChannel channel) {
        this.channel = channel;
        return this;
    }

    public Message addEmbed(IEmbed embeds) {
        this.embeds.add(embeds);
        return this;

    }

    public void setReactions(List<IReaction> reactions) {
        this.reactions = reactions;
    }

    public Message addReaction(IReaction reaction) {
        this.reactions.add(reaction);
        return this;
    }

    public Message removeReaction(IReaction reaction) {
        this.reactions.remove(reaction);
        return this;
    }

}
