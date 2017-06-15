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
import org.alienideology.jcord.internal.object.message.Message;
import org.alienideology.jcord.internal.object.message.StringMessage;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Message - A recorded communication sent in MessageChannel.
 * @author AlienIdeology
 */
public interface IMessage extends IDiscordObject, ISnowFlake, Comparable<Message> {

    /**
     * Reply to this message, starting with mentioning the message's author
     * @see IMessageChannel#sendMessage(String)
     *
     * @param content The reply content
     * @return The message sent.
     */
    default IMessage reply(String content) {
        return getChannel().sendMessage(getMember().mention()+" "+content);
    }

    /**
     * Reply to this message, starting with mentioning the message's author
     * @see IMessageChannel#sendMessageFormat(String, Object...)
     *
     * @param format The content string to be formatted
     * @param args The arguments referenced by the format string.
     * @return The message sent.
     */
    default IMessage replyFormat(String format, Object... args) {
        return getChannel().sendMessageFormat("%s "+format, getMember().mention(), args);
    }

    /**
     * Reply to this message
     * @see IMessageChannel#sendMessage(IStringMessage)
     *
     * @param message The StringMessage built by {@link StringMessageBuilder}
     * @return The message sent.
     */
    default IMessage reply(IStringMessage message) {
        return getChannel().sendMessage(message);
    }

    /**
     * Reply to this message
     * @see IMessageChannel#sendMessage(IEmbedMessage)
     *
     * @param message The EmbedMessage built by {@link EmbedMessageBuilder}
     * @return The message sent.
     */
    default IMessage reply(IEmbedMessage message) {
        return getChannel().sendMessage(message);
    }

    /**
     * Edit this message
     *
     * @see IMessageChannel#editMessage(String, String)
     * @param content The new content
     * @return The message edited.
     */
    default IMessage edit(String content) {
        return getChannel().editMessage(getId(), content);
    }

    /**
     * Edit this message
     *
     * @see IMessageChannel#editMessageFormat(String, String, Object...)
     * @param format The string to be formatted.
     * @param args The arguments referenced by the format string.
     * @return The message edited.
     */
    default IMessage editFormat(String format, Object... args) {
        return getChannel().editMessageFormat(getId(), format, args);
    }

    /**
     * Edit this message
     *
     * @param message The IStringMessage built by {@link StringMessageBuilder}.
     * @return The message edited
     */
    default IMessage edit(IStringMessage message) {
        return getChannel().editMessage(getId(), message);
    }

    /**
     * Edit an embed message by ID
     *
     * @param message The IEmbedMessage built by {@link EmbedMessageBuilder}.
     * @return The message edited
     */
    default IMessage edit(IEmbedMessage message) {
        return getChannel().editMessage(getId(), message);
    }

    /**
     * Delete this message
     *
     * @see IMessageChannel#deleteMessage(String)
     * @return The message deleted.
     */
    default IMessage delete() {
        return getChannel().deleteMessage(getId());
    }

    /**
     * Pin this message
     *
     * @see IMessageChannel#pinMessage(String)
     */
    default void pin() {
        getChannel().pinMessage(getId());
    }

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
