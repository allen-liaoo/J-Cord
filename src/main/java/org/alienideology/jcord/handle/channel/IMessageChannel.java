package org.alienideology.jcord.handle.channel;

import org.alienideology.jcord.handle.builders.EmbedBuilder;
import org.alienideology.jcord.handle.builders.MessageBuilder;
import org.alienideology.jcord.handle.emoji.Emoji;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IGuildEmoji;
import org.alienideology.jcord.handle.guild.IMember;
import org.alienideology.jcord.handle.message.IEmbed;
import org.alienideology.jcord.handle.message.IMessage;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.exception.PermissionException;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * MessageChannel - A channel that allows users to send message.
 * @author AlienIdeology
 */
public interface IMessageChannel extends IChannel {

    /**
     * Get the guild this channel belongs to. May be null for private channels.
     *
     * @return The guild (Nullable)
     */
    @Nullable
    IGuild getGuild();

    /**
     * Get the latest message of this channel.
     *
     * @return The latest message.
     */
    IMessage getLatestMessage();

    /**
     * Get the message history of this channel.
     *
     * @return The message history.
     */
    MessageHistory getHistory();

    /**
     * Get a message by key
     *
     * @exception PermissionException
     *          If the identity does not have {@code Read Message History} permission.
     *
     * @param id The key of the message
     * @return The message object
     */
    IMessage getMessage(String id);

    /**
     * Send a string message.
     *
     * @param message The message to be sent.
     * @exception IllegalArgumentException
     *          If the message is more than 2000 characters.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          The message channel is a conversation between two bots.
     * @exception PermissionException
     *          If the user lack Send Messages permission
     * @return The message sent.
     */
    IMessage sendMessage(String message);

    /**
     * Send a string message.
     *
     * @param format The string to be formatted and send.
     * @param args The arguments referenced by the format string.
     * @exception IllegalArgumentException
     *          If the message is more than 2000 characters.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          The message channel is a conversation between two bots.
     * @exception PermissionException
     *          If the user lack Send Messages permission
     * @return The message sent.
     */
    IMessage sendMessageFormat(String format, Object... args);

    /**
     * Send a message built by MessageBuilder
     *
     * @param message The IMessage built by {@link MessageBuilder}.
     * @exception IllegalArgumentException
     *          If the message is more than 2000 characters.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          The message channel is a conversation between two bots.
     * @exception PermissionException
     *          If the user lack Send Messages permission
     * @return The message sent.
     */
    IMessage sendMessage(IMessage message);

    /**
     * Send an embed message.
     *
     * @param embed The Embed built by {@link EmbedBuilder}.
     * @exception IllegalArgumentException
     *          If the message is more than 2000 characters.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          The message channel is a conversation between two bots.
     * @exception PermissionException
     *          If the user lack Send Messages permission
     * @return The message sent.
     */
    IMessage sendMessage(IEmbed embed);

    /**
     * Send an attachment with the string message.
     *
     * @exception PermissionException
     *          <ul>
     *              <li>If the identity does not have {@code Send Messages} permission.</li>
     *              <li>If the identity does not have {@code Add Attachments} permission.</li>
     *          </ul>
     * @exception IllegalArgumentException
     *          If the file is greater than {@code 8 MB}, which is the size limit of an attachment.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          The message channel is a conversation between two bots.
     *
     * @param file The file attachment.
     * @param message The message.
     * @return The message sent.
     * @throws IOException
     *          <ul>
     *              <li>If the file is not found.</li>
     *              <li>If the file is a directory.</li>
     *              <li>If the file is not readable.</li>
     *          </ul>
     */
    IMessage sendAttachment(File file, String message) throws IOException;

    /**
     * Send an attachment with the formatted string message.
     *
     * @exception PermissionException
     *          <ul>
     *              <li>If the identity does not have {@code Send Messages} permission.</li>
     *              <li>If the identity does not have {@code Add Attachments} permission.</li>
     *          </ul>
     * @exception IllegalArgumentException
     *          If the file is greater than {@code 8 MB}, which is the size limit of an attachment.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          The message channel is a conversation between two bots.
     *
     * @param file The file attachment.
     * @param format The string to be formatted and send.
     * @param args The arguments referenced by the format string.
     * @return The message sent.
     * @throws IOException
     *          <ul>
     *              <li>If the file is not found.</li>
     *              <li>If the file is a directory.</li>
     *              <li>If the file is not readable.</li>
     *          </ul>
     */
    IMessage sendAttachmentFormat(File file, String format, Object... args) throws IOException;

    /**
     * Send an attachment with the formatted string message.
     *
     * @exception PermissionException
     *          <ul>
     *              <li>If the identity does not have {@code Send Messages} permission.</li>
     *              <li>If the identity does not have {@code Add Attachments} permission.</li>
     *          </ul>
     * @exception IllegalArgumentException
     *          If the file is greater than {@code 8 MB}, which is the size limit of an attachment.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          The message channel is a conversation between two bots.
     *
     * @param file The file attachment.
     * @param message The message built by {@link MessageBuilder}.
     * @return The message sent.
     * @throws IOException
     *          <ul>
     *              <li>If the file is not found.</li>
     *              <li>If the file is a directory.</li>
     *              <li>If the file is not readable.</li>
     *          </ul>
     */
    IMessage sendAttachment(File file, IMessage message) throws IOException;

    /**
     * Edit a string message by ID
     *
     * @param messageId The message ID
     * @param message The new string content of the message
     * @return The message edited
     */
    IMessage editMessage(String messageId, String message);

    /**
     * Format a string message by ID
     *
     * @param messageId The message ID
     * @param format The string to be formatted.
     * @param args The arguments referenced by the format string.
     * @return The message edited
     */
    IMessage editMessageFormat(String messageId, String format, Object... args);

    /**
     * Edit a message by ID
     *
     * @param messageId The message ID
     * @param message The IMessage built by {@link MessageBuilder}.
     * @return The message edited
     */
    IMessage editMessage(String messageId, IMessage message);

    /**
     * Edit an embed message by ID
     *
     * @param messageId The message ID
     * @param message The IEmbed built by {@link EmbedBuilder}.
     * @return The message edited
     */
    IMessage editMessage(String messageId, IEmbed message);

    /**
     * Delete a message by ID.
     *
     * @param messageId The Id of the message.
     *
     * @exception PermissionException
     *          If this channel is a TextChannel and the user lack Manage Messages permission to delete other's message.
     * @exception IllegalArgumentException
     *          If this channel is a PrivateChannel and the message is from another user.
     *
     * @return The message deleted.
     */
     IMessage deleteMessage(String messageId);

    /**
     * Delete a message.
     *
     * @exception PermissionException
     *          If the identity lack {@code Manager Messages} permission.
     * @exception IllegalArgumentException
     *          If this channel is a PrivateChannel and the message is from another user.
     *
     * @param message The the message.
     * @return The message deleted.
     */
    IMessage deleteMessage(IMessage message);

    /**
     * Build delete a collection of messages.
     *
     * @exception IllegalArgumentException
     *          If this channel is a private channel.
     * @exception PermissionException
     *          If the identity does not have {@code Manage Messages} permission.
     *
     * @param throwEx If true, the the event will check and throw exceptions.
     *                If false, the exceptions below will be ignored.
     *
     * @throws org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If one of the messages is not from this channel.
     *          @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_MESSAGE
     * @throws IllegalArgumentException
     *          If one of the messages is older than 2 weeks.
     *
     * @param messages The messages to delete.
     */
    void bulkDeleteMessages(boolean throwEx, List<IMessage> messages);

    /**
     * Pin a message by ID.
     *
     * @exception PermissionException
     *          If the identity lack {@code Manager Messages} permission.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If the message is not found in this channel.
     *          @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_MESSAGE
     *
     * @param messageId The message key.
     */
    void pinMessage(String messageId);

    /**
     * Pin a message.
     *
     * @exception PermissionException
     *          If the identity lack {@code Manager Messages} permission.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If the message is not found in this channel.
     *          @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_MESSAGE
     *
     * @param message The message object.
     */
    void pinMessage(IMessage message);

    /**
     * Unpin a message by ID.
     *
     * @exception PermissionException
     *          If the identity lack {@code Manager Messages} permission.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If the message is not found in this channel.
     *          @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_MESSAGE
     *
     * @param messageId The message key.
     */
    void unpinMessage(String messageId);

    /**
     * Unpin a message.
     *
     * @exception PermissionException
     *          If the identity lack {@code Manager Messages} permission.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If the message is not found in this channel.
     *          @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_MESSAGE
     *
     * @param message The message object.
     */
    void unpinMessage(IMessage message);

    /**
     * Get reacted users of a reaction on a message.
     *
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If the message or reaction is not found.
     *          @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN
     *
     * @param messageId The message ID.
     * @param unicode The unicode reaction.
     * @return A list of users reacted to this reaction.
     */
    List<IUser> getReactedUsers(String messageId, String unicode);

    /**
     * Get reacted users of a reaction on a message.
     *
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If the message or reaction is not found.
     *          @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN
     *
     * @param messageId The message ID.
     * @param emoji The emoji reaction.
     * @return A list of users reacted to this reaction.
     */
    default List<IUser> getReactedUsers(String messageId, Emoji emoji) {
        return getReactedUsers(messageId, emoji.getUnicode());
    }

    /**
     * Get reacted users of a reaction on a message.
     *
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If the message or reaction is not found.
     *          @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN
     *
     * @param messageId The message ID.
     * @param guildEmoji The guild emoji reaction.
     * @return A list of users reacted to this reaction.
     */
    List<IUser> getReactedUsers(String messageId, IGuildEmoji guildEmoji);

    /**
     * Add a reaction to a message by unicode.
     *
     * @exception PermissionException
     *          <ul>
     *              <li>If the identity does not have {@code Read Message History} permission to access the message.</li>
     *              <li>If the identity does not have {@code Add Reactions} permission to add a brand new reaction to the message.</li>
     *          </ul>
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If the messages does not belong to this channel.
     *          @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_MESSAGE
     *
     * @param messageId The message ID.
     * @param unicode The string unicode
     */
    void addReaction(String messageId, String unicode);

    /**
     * Add an emoji reaction to a message.
     *
     * @exception PermissionException
     *          <ul>
     *              <li>If the identity does not have {@code Read Message History} permission to access the message.</li>
     *              <li>If the identity does not have {@code Add Reactions} permission to add a brand new reaction to the message.</li>
     *          </ul>
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If the messages does not belong to this channel.
     *          @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_MESSAGE
     *
     * @param messageId The message ID.
     * @param emoji The emoji.
     */
    default void addReaction(String messageId, Emoji emoji) {
        addReaction(messageId, emoji.getUnicode());
    }

    /**
     * Add a guild emoji reaction to a message.
     *
     * @exception PermissionException
     *          <ul>
     *              <li>If the identity does not have {@code Read Message History} permission to access the message.</li>
     *              <li>If the identity does not have {@code Add Reactions} permission to add a brand new reaction to the message.</li>
     *          </ul>
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If the messages does not belong to this channel.
     *          @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_MESSAGE
     *
     * @param messageId The message ID.
     * @param guildEmoji The guild emoji
     */
    void addReaction(String messageId, IGuildEmoji guildEmoji);

    /**
     * Remove a member's reaction from a message.
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
     *          @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_EMOJI
     *
     * @param member The member that reacted on the message.
     * @param messageId The message's ID.
     * @param unicode The unicode reaction.
     */
    void removeReaction(IMember member, String messageId, String unicode);

    /**
     * Remove a member's reaction from a message.
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
     *          @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_EMOJI
     *
     * @param member The member that reacted on the message.
     * @param messageId The message's ID.
     * @param emoji The emoji reaction.
     */
    default void removeReaction(IMember member, String messageId, Emoji emoji) {
        removeReaction(member, messageId, emoji.getUnicode());
    }

    /**
     * Remove a member's reaction from a message.
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
     *          @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_EMOJI
     *
     * @param member The member that reacted on the message.
     * @param messageId The message's ID.
     * @param guildEmoji The guild emoji reaction.
     */
    void removeReaction(IMember member, String messageId, IGuildEmoji guildEmoji);

    /**
     * Remove all reactions from a message.
     *
     * @exception PermissionException
     *          If the identity does not have {@code Manager Messages} permission.
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If the message is not from this channel.
     *          @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_MESSAGE
     *
     * @param messageId The message ID.
     */
    void removeAllReactions(String messageId);

    /**
     * Start the typing status in this channel.
     * Typing status is on when the channel shows {@code YOUR_BOT'S_NAME is typing...}.
     */
    void startTyping();

}
