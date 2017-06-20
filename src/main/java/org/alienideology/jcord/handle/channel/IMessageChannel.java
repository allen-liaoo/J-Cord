package org.alienideology.jcord.handle.channel;

import com.sun.istack.internal.Nullable;
import org.alienideology.jcord.handle.builders.EmbedMessageBuilder;
import org.alienideology.jcord.handle.builders.StringMessageBuilder;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.message.IEmbedMessage;
import org.alienideology.jcord.handle.message.IMessage;
import org.alienideology.jcord.handle.message.IStringMessage;
import org.alienideology.jcord.internal.exception.PermissionException;

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
     * Get a message by id
     *
     * @param id The id of the message
     * @return The message object
     */
    IMessage getMessage(String id);

    /**
     * Send a string message.
     *
     * @param message The message to be sent.
     * @throws IllegalArgumentException If the message is more than 2000 characters.
     * @exception PermissionException If the user lack Send Messages permission
     * @return The message sent.
     */
    IMessage sendMessage(String message);

    /**
     * Send a string message.
     *
     * @param format The string to be formatted and send.
     * @param args The arguments referenced by the format string.
     * @exception  IllegalArgumentException If the message is more than 2000 characters.
     * @exception PermissionException If the user lack {@code Send Messages} permission
     * @return The message sent.
     */
    IMessage sendMessageFormat(String format, Object... args);

    /**
     * Send a message built by StringMessageBuilder
     *
     * @param message The StringMessage built by {@link StringMessageBuilder}.
     * @exception PermissionException If the user lack {@code Send Messages} permission
     * @return The message sent.
     */
    IMessage sendMessage(IStringMessage message);

    /**
     * Send an embed message.
     *
     * @param embed The EmbedMessage built by {@link EmbedMessageBuilder}.
     * @exception  IllegalStateException If the embed message is built but the embed is empty.
     * @exception PermissionException If the user lack {@code Send Messages} permission
     * @return The message sent.
     */
    IMessage sendMessage(IEmbedMessage embed);

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
     * @param message The IStringMessage built by {@link StringMessageBuilder}.
     * @return The message edited
     */
    IMessage editMessage(String messageId, IStringMessage message);

    /**
     * Edit an embed message by ID
     *
     * @param messageId The message ID
     * @param message The IEmbedMessage built by {@link EmbedMessageBuilder}.
     * @return The message edited
     */
    IMessage editMessage(String messageId, IEmbedMessage message);

    /**
     * Delete a message by ID.
     *
     * @param messageId The Id of the message.
     *
     * @exception IllegalArgumentException If this channel is a PrivateChannel and the message is from another user.
     * @exception PermissionException If this channel is a TextChannel and the user lack Manage Messages permission to delete other's message.
     *
     * @return The message deleted.
     */
     IMessage deleteMessage(String messageId);

    /**
     * Delete a message.
     *
     * @param message The the message.
     *
     * @exception IllegalArgumentException If this channel is a PrivateChannel and the message is from another user.
     * @exception PermissionException If this channel is a TextChannel and the user lack Manage Messages permission to delete other's message.
     *
     * @return The message deleted.
     */
    IMessage deleteMessage(IMessage message);

    /**
     * Pin a message by ID.
     *
     * @param messageId The message id.
     */
    void pinMessage(String messageId);

    /**
     * Pin a message.
     *
     * @param message The message object.
     */
    void pinMessage(IMessage message);

    /**
     * Start the typing status in this channel.
     * Typing status is on when the channel shows {@code YOUR_BOT'S_NAME is typing...}.
     */
    void startTyping();

}
