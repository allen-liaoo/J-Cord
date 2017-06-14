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
import org.alienideology.jcord.internal.object.message.EmbedMessageBuilder;
import org.alienideology.jcord.internal.object.message.MessageBuilder;
import org.alienideology.jcord.internal.object.message.Reaction;
import org.alienideology.jcord.internal.object.message.StringMessage;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * @author AlienIdeology
 */
public interface IMessage extends IDiscordObject, ISnowFlake, Comparable<Message> {

    /**
     * Reply to this message
     * @see MessageChannel#sendMessage(String)
     *
     * @param content The reply content
     * @return The message sent.
     */
    default IMessage reply(String content) {
        return getChannel().sendMessage(content);
    }

    /**
     * Reply to this message
     * @see MessageChannel#sendMessageFormat(String, Object...)
     *
     * @param format The content string to be formatted
     * @param args The arguments referenced by the format string.
     * @return The message sent.
     */
    default IMessage replyFormat(String format, Object... args) {
        return getChannel().sendMessageFormat(format, args);
    }

    /**
     * Reply to this message
     * @see MessageChannel#sendMessage(MessageBuilder)
     *
     * @param builder The string message builder
     * @return The message sent.
     */
    default IMessage reply(MessageBuilder builder) {
        return getChannel().sendMessage(builder);
    }

    /**
     * Reply to this message
     * @see MessageChannel#sendMessage(EmbedMessageBuilder)
     *
     * @param builder The embed message builder
     * @return The message sent.
     */
    default IMessage reply(EmbedMessageBuilder builder) {
        return getChannel().sendMessage(builder);
    }

    /**
     * Edit this message
     *
     * @see MessageChannel#editMessage(String, String)
     * @param content The new content
     * @return The message edited.
     */
    IMessage edit(String content);

    /**
     * Delete this message
     *
     * @see MessageChannel#deleteMessage(String)
     * @return The message deleted.
     */
    IMessage delete();

    /**
     * Pin this message
     *
     * @see MessageChannel#pinMessage(String)
     */
    void pin();

    /**
     * Get the string content of this message.
     *
     * @return The string content, or null for embed messages.
     */
    String getContent();

    /**
     * Get the guild this message belongs to.
     *
     * @return The guild, or null if this message is sent in PrivateChannel
     */
    @Nullable
    IGuild getGuild();

    /**
     * Get the MessageChannel this message was sent from.
     *
     * @return The message channel.
     */
    IMessageChannel getChannel();

    /**
     * Get the channel type this message was from
     * @return the channel type
     */
    default IChannel.Type getFromType() {
        return getChannel().getType();
    }

    /**
     * Check if the channel is from a specific type.
     *
     * @param type The channel type specified.
     * @return True if the channel is from {@code type}.
     */
    default boolean fromType(IChannel.Type type) {
        return getChannel().isType(type);
    }

    /**
     * Get the author of this message.
     *
     * @return The user
     */
    IUser getAuthor();

    /**
     * @return True if this message is sent by the identity itself
     */
    default boolean isFromSelf() {
        return getAuthor().equals(getIdentity().getSelf());
    }

    /**
     * Get the member instance of this author.
     *
     * @return The member.
     */
    @Nullable
    IMember getMember();

    /**
     * Get the mentioned users of this message.
     *
     * @return A list of mentioned users.
     */
    List<IUser> getMentions();

    /**
     * Get the mentioned members of this message.
     * @see #getMentions() for getting list of users.
     *
     * @return A list of mentioned members, or null if this message is sent in PrivateChannel.
     */
    @Nullable
    List<IMember> getMentionedMembers();

    /**
     * Get the mentioned roles of this message.
     *
     * @return A list of mentioned roles.
     */
    List<IRole> getMentionedRoles();

    /**
     * Get the created time stamp of this message.
     *
     * @return The created time.
     */
    OffsetDateTime getCreatedTime();

    /**
     * Get the attachments of this message.
     *
     * @return The attachments or null if no attachments.
     */
    @Nullable
    List<Message.Attachment> getAttachments();

    /**
     * Get reactions this message has.
     *
     * @return A list of reactions.
     */
    List<IReaction> getReactions();

    /**
     * @return True if this message is an embed message.
     */
    default boolean isEmbed() {
    return !(this instanceof StringMessage);
    }

    /**
     * @return True if this message was sent as a TTS (Text to Speech) message.
     */
    boolean isTTS();

    /**
     * @return True if this message mentioned everyone.
     */
    boolean isMentionedEveryone();

    /**
     * @return True is this message is a pinned message.
     */
    boolean isPinned();

    @Override
    String getId();

    /**
     * Compare this message's created time to another message.
     *
     * @param o The other message.
     * @return the integer result of the comparison.
     * @see Comparable#compareTo(Object) for the returning value.
     */
    @Override
    int compareTo(Message o);

    /**
     * An file or image attachment of a message.
     */
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

        public String getFilename() {
            return filename;
        }

        public int getSize() {
            return size;
        }

        public String getUrl() {
            return url;
        }

        @Override
        public String getId() {
            return id;
        }

    }
}
