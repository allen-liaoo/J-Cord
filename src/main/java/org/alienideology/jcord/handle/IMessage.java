package org.alienideology.jcord.handle;

import com.sun.istack.internal.Nullable;
import org.alienideology.jcord.internal.object.Guild;
import org.alienideology.jcord.internal.object.Message;
import org.alienideology.jcord.internal.object.channel.Channel;
import org.alienideology.jcord.internal.object.channel.MessageChannel;
import org.alienideology.jcord.internal.object.guild.Member;
import org.alienideology.jcord.internal.object.guild.Role;
import org.alienideology.jcord.internal.object.message.Reaction;
import org.alienideology.jcord.internal.object.message.StringMessage;
import org.alienideology.jcord.internal.object.user.User;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * @author AlienIdeology
 */
public interface IMessage extends ISnowFlake, Comparable<Message> {

    /**
     * Edit this message
     * @see MessageChannel#editMessage(String, String)
     * @param content The new content
     * @return The message edited.
     */
    public Message edit(String content);

    /**
     * Delete this message
     * @see MessageChannel#deleteMessage(String)
     * @return The message deleted.
     */
    Message delete();

    /**
     * Pin this message
     * @see MessageChannel#pinMessage(String)
     */
    public void pin();

    public String getContent();

    @Nullable
    public Guild getGuild();

    public MessageChannel getChannel();

    public Channel.Type getFromType();

    public boolean fromType(Channel.Type type);

    public User getAuthor();

    public boolean isFromSelf();

    @Nullable
    public Member getMember();

    public List<User> getMentions();

    @Nullable
    public List<Member> getMentionedMembers();

    public List<Role> getMentionedRoles();

    public OffsetDateTime getCreatedTime();

    public List<Message.Attachment> getAttachments();

    public List<Reaction> getReactions();

    default boolean isEmbed() {
        return !(this instanceof StringMessage);
    }

    public boolean isTTS();

    public boolean isMentionedEveryone();

    public boolean isPinned();

    @Override
    public String getId();

    @Override
    public int compareTo(Message o);

}
