package org.alienideology.jcord.handle.message;

import com.sun.istack.internal.Nullable;
import org.alienideology.jcord.handle.IDiscordObject;
import org.alienideology.jcord.handle.ISnowFlake;
import org.alienideology.jcord.handle.channel.IChannel;
import org.alienideology.jcord.handle.channel.IMessageChannel;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IMember;
import org.alienideology.jcord.handle.guild.IRole;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.object.Message;
import org.alienideology.jcord.internal.object.channel.MessageChannel;
import org.alienideology.jcord.internal.object.message.Reaction;
import org.alienideology.jcord.internal.object.message.StringMessage;

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

    IChannel.Type getFromType();

    boolean fromType(IChannel.Type type);

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

    List<IReaction> getReactions();

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

    class Attachment implements ISnowFlake {

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
