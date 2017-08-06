package org.alienideology.jcord.handle.message;

import org.alienideology.jcord.handle.IDiscordObject;
import org.alienideology.jcord.handle.ISnowFlake;
import org.alienideology.jcord.handle.audit.AuditAction;
import org.alienideology.jcord.handle.builders.EmbedBuilder;
import org.alienideology.jcord.handle.builders.MessageBuilder;
import org.alienideology.jcord.handle.channel.IChannel;
import org.alienideology.jcord.handle.channel.IMessageChannel;
import org.alienideology.jcord.handle.emoji.Emoji;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IGuildEmoji;
import org.alienideology.jcord.handle.guild.IMember;
import org.alienideology.jcord.handle.guild.IRole;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.exception.PermissionException;
import org.alienideology.jcord.internal.object.message.Message;
import org.alienideology.jcord.internal.rest.ErrorResponse;
import org.jetbrains.annotations.Nullable;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Message - A recorded communication sent in MessageChannel.
 * @author AlienIdeology
 */
public interface IMessage extends IDiscordObject, ISnowFlake, Comparable<IMessage> {

    /**
     * The max content length of a message.
     */
    int CONTENT_LENGTH_MAX = 2000;

    /**
     * Check if a message can be sent without causing errors.
     * This checks for content length.
     *
     * @return True if the embed can be sent.
     */
    boolean canSend();

    /**
     * Reply to this message, starting with mentioning the message's author
     * @see IMessageChannel#sendMessage(String)
     *
     * @param content The reply content
     * @return The message sent.
     */
    default IMessage reply(String content) {
        return getChannel().sendMessage(getAuthor().mention(true)+" "+content);
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
        return getChannel().sendMessageFormat("%s "+format, getAuthor().mention(true), args);
    }

    /**
     * Reply to this message
     * @see IMessageChannel#sendMessage(IMessage)
     *
     * @param message The IMessage built by {@link MessageBuilder}
     * @return The message sent.
     */
    default IMessage reply(IMessage message) {
        return getChannel().sendMessage(message);
    }

    /**
     * Reply to this message
     * @see IMessageChannel#sendMessage(IEmbed)
     *
     * @param message The Embed built by {@link EmbedBuilder}
     * @return The message sent.
     */
    default IMessage reply(IEmbed message) {
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
     * @param message The IMessage built by {@link MessageBuilder}.
     * @return The message edited
     */
    default IMessage edit(IMessage message) {
        return getChannel().editMessage(getId(), message);
    }

    /**
     * Edit an embed message by ID
     *
     * @param message The IEmbed built by {@link EmbedBuilder}.
     * @return The message edited
     */
    default IMessage edit(IEmbed message) {
        return getChannel().editMessage(getId(), message);
    }

    /**
     * Delete this message
     *
     * @see IMessageChannel#deleteMessage(String)
     * @return A {@link Void} {@link AuditAction}, used to attach audit log reason.
     */
    default AuditAction<IMessage> delete() {
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
     * Add an reaction to a message by unicode.
     *
     * @exception PermissionException
     *          <ul>
     *              <li>If the identity does not have {@code Read Message History} permission to access the message.</li>
     *              <li>If the identity does not have {@code Add Reactions} permission to add a brand new reaction to the message.</li>
     *          </ul>
     *
     * @param unicode The string unicode
     */
    default void addReaction(String unicode) {
        getChannel().addReaction(this.getId(), unicode);
    }

    /**
     * Add an emoji reaction to this message.
     *
     * @exception PermissionException
     *          <ul>
     *              <li>If the identity does not have {@code Read Message History} permission to access the message.</li>
     *              <li>If the identity does not have {@code Add Reactions} permission to add a brand new reaction to the message.</li>
     *          </ul>
     *
     * @param emoji The emoji.
     */
    default void addReaction(Emoji emoji) {
        getChannel().addReaction(this.getId(), emoji);
    }

    /**
     * Add a guild emoji reaction to this message.
     *
     * @exception PermissionException
     *          <ul>
     *              <li>If the identity does not have {@code Read Message History} permission to access the message.</li>
     *              <li>If the identity does not have {@code Add Reactions} permission to add a brand new reaction to the message.</li>
     *          </ul>
     *
     * @param emoji The guild emoji
     */
    default void addReaction(IGuildEmoji emoji) {
        getChannel().addReaction(this.getId(), emoji);
    }

    /**
     * Remove a member's reaction on this message.
     *
     * @exception PermissionException
     *          If the reaction to remove is not reacted by the identity itself, and the identity does not have {@code Manager Messages} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          <ul>
     *              <li>If the member is the server owner.</li>
     *              <li>If the member is at a higher or same hierarchy as the identity.</li>
     *          </ul>
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If the reaction is not found from the message's reactions.
     *          @see ErrorResponse#UNKNOWN_EMOJI
     *
     * @param member The member that reacted on the message.
     * @param unicode The unicode reaction.
     */
    default void removeReaction(IMember member, String unicode) {
        getChannel().removeReaction(member, getId(), unicode);
    }

    /**
     * Remove a member's reaction on this message.
     *
     * @exception PermissionException
     *          If the reaction to remove is not reacted by the identity itself, and the identity does not have {@code Manager Messages} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          <ul>
     *              <li>If the member is the server owner.</li>
     *              <li>If the member is at a higher or same hierarchy as the identity.</li>
     *          </ul>
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If the reaction is not found from the message's reactions.
     *          @see ErrorResponse#UNKNOWN_EMOJI
     *
     * @param member The member that reacted on the message.
     * @param emoji The emoji reaction.
     */
    default void removeReaction(IMember member, Emoji emoji) {
        getChannel().removeReaction(member, getId(), emoji);
    }

    /**
     * Remove a member's reaction on this message.
     *
     * @exception PermissionException
     *          If the reaction to remove is not reacted by the identity itself, and the identity does not have {@code Manager Messages} permission.
     * @exception org.alienideology.jcord.internal.exception.HigherHierarchyException
     *          <ul>
     *              <li>If the member is the server owner.</li>
     *              <li>If the member is at a higher or same hierarchy as the identity.</li>
     *          </ul>
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If the reaction is not found from the message's reactions.
     *          @see ErrorResponse#UNKNOWN_EMOJI
     *
     * @param member The member that reacted on the message.
     * @param guildEmoji The guild emoji reaction.
     */
    default void removeReaction(IMember member, IGuildEmoji guildEmoji) {
        getChannel().removeReaction(member, getId(), guildEmoji);
    }

    /**
     * Remove all reactions from this message.
     *
     * @exception PermissionException
     *          If the identity does not have {@code Manager Messages} permission.
     *
     */
    default void removeAllReactions() {
        getChannel().removeAllReactions(getId());
    }

    /**
     * Get the {@link MessageProcessor} for this message.
     * The processor is used to read the message content and get specified sequences of it.
     *
     * @return The processor.
     */
    MessageProcessor getMessageProcessor();

    /**
     * Get the string content of this message.
     *
     * @return The string content.
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
     * Get the type of this message.
     *
     * @return The message type.
     */
    IMessage.Type getType();

    /**
     * Get the channel type this message was from.
     *
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
     * Get the {@link org.alienideology.jcord.internal.object.message.Embed}s attached to this message.
     *
     * @return A list of embeds.
     */
    List<IEmbed> getEmbeds();

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
    boolean isEmbed();

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
    int compareTo(IMessage o);

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

    /**
     * Types of messages, including types of system messages.
     */
    enum Type {

        DEFAULT (0),
        RECIPIENT_ADD (1),
        RECIPIENT_REMOVE (2),
        CALL (3),
        CHANNEL_NAME_CHANGE (4),
        CHANNEL_ICON_CHANGE (5),
        CHANNEL_PINNED_MESSAGE (6),
        GUILD_MEMBER_JOIN (7),
        UNKNOWN (-1);

        public final int key;

        Type (int key) {
            this.key = key;
        }

        public static Type getByKey (int key) {
            for (Type type : values()) {
                if (type.key == key) {
                    return type;
                }
            }
            return UNKNOWN;
        }

        public boolean isSystemMessage() {
            return this != DEFAULT && this != UNKNOWN;
        }

    }

    /**
     * Types of markdown support in Discord
     */
    enum Markdown {

        ITALICS ("*"),

        BOLD ("**"),

        STRIKEOUT ("~~"),

        UNDERLINE ("__"),

        CODE ("`"),

        CODE_BLOCK ("```");

        public final String markdown;
        public final String markdown_backward;

        Markdown(String markdown) {
            this.markdown = markdown;
            this.markdown_backward = new StringBuilder(markdown).reverse().toString();
        }

        /**
         * Get the combined string from multiple markdowns.
         *
         * @param reverse True to reverse the string
         * @param markdowns The markdowns to combine.
         * @return The combined markdowns
         */
        public static String getCombinedString(boolean reverse, Markdown... markdowns) {
            StringBuilder sb = new StringBuilder();
            for (IMessage.Markdown type : markdowns) {
                sb.append(type.markdown);
            }
            if (reverse) sb.reverse();
            return sb.toString();
        }
    }

}
