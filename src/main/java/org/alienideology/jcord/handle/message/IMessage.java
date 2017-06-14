package org.alienideology.jcord.handle.message;

import com.sun.istack.internal.Nullable;
import org.alienideology.jcord.handle.IDiscordObject;
import org.alienideology.jcord.handle.ISnowFlake;
import org.alienideology.jcord.handle.channel.IMessageChannel;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IMember;
import org.alienideology.jcord.handle.guild.IRole;
import org.alienideology.jcord.handle.user.IUser;
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
public interface IMessage extends IDiscordObject, ISnowFlake, Comparable<Message> {

    /**
     * Edit this message
     * @see MessageChannel#editMessage(String, String)
     * @param content The new content
     * @return The message edited.
     */
    IMessage edit(String content);

    /**
     * Delete this message
     * @see MessageChannel#deleteMessage(String)
     * @return The message deleted.
     */
    IMessage delete();

    /**
     * Pin this message
     * @see MessageChannel#pinMessage(String)
     */
    void pin();

    String getContent();

    @Nullable
    IGuild getGuild();

    IMessageChannel getChannel();

    Channel.Type getFromType();

    boolean fromType(Channel.Type type);

    IUser getAuthor();

    boolean isFromSelf();

    @Nullable
    IMember getMember();

    List<IUser> getMentions();

    @Nullable
    List<IMember> getMentionedMembers();

    List<IRole> getMentionedRoles();

    OffsetDateTime getCreatedTime();

    List<Message.Attachment> getAttachments();

    List<Reaction> getReactions();

    default boolean isEmbed() {
    return !(this instanceof StringMessage);
    }

    boolean isTTS();

    boolean isMentionedEveryone();

    boolean isPinned();

    @Override
    String getId();

    @Override
    int compareTo(Message o);

}
