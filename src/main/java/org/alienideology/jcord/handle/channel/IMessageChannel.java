package org.alienideology.jcord.handle.channel;

import com.sun.istack.internal.Nullable;
import org.alienideology.jcord.handle.IDiscordObject;
import org.alienideology.jcord.internal.Identity;
import org.alienideology.jcord.internal.exception.PermissionException;
import org.alienideology.jcord.internal.object.Guild;
import org.alienideology.jcord.internal.object.Message;
import org.alienideology.jcord.internal.object.channel.Channel;
import org.alienideology.jcord.internal.object.channel.MessageHistory;
import org.alienideology.jcord.internal.object.message.EmbedMessageBuilder;
import org.alienideology.jcord.internal.object.message.MessageBuilder;

/**
 * MessageChannel - A channel that allows users to send message.
 * @author AlienIdeology
 */
public interface IMessageChannel extends IDiscordObject, IChannel {

    @Override
    Channel.Type getType();

    @Override
    boolean isPrivate();

    @Override
    String getId();

    @Nullable
    Guild getGuild();

    Message getLatestMessage();

    MessageHistory getHistory();

    /**
    * Get a message by id
    * @param id The id of the message
    * @return The message object
    */
    Message getMessage(String id);

    /**
    * Send a string message.
    * @param message The message to be sent.
    * @throws IllegalArgumentException If the message is more than 2000 characters.
    * @exception PermissionException If the user lack Send Messages permission
    * @return The message sent.
    */
    Message sendMessage(String message);

    /**
    * Send a string message.
    * @param format The string to be formatted and send.
    * @param args The arguments referenced by the format string.
    * @exception  IllegalArgumentException If the message is more than 2000 characters.
    * @exception PermissionException If the user lack Send Messages permission
    * @return The message sent.
    */
    Message sendMessageFormat(String format, Object... args);

    /**
    * Send a message built by MessageBuilder
    * @param message The builder
    * @exception PermissionException If the user lack Send Messages permission
    * @return The message sent.
    */
    Message sendMessage(MessageBuilder message);

    /**
    * Send an embed message.
    * @param embed The EmbedMessageBuilder
    * @exception  IllegalStateException If the embed message is built but the embed is empty.
    * @exception PermissionException If the user lack Send Messages permission
    * @return The message sent.
    */
    Message sendMessage(EmbedMessageBuilder embed);

    /**
    * Edit a string message by ID
    * @param messageId The message ID
    * @param message The new string content of the message
    * @return The message edited
    */
    Message editMessage(String messageId, String message);

    /**
    * Format a string message by ID
    * @param messageId The message ID
    * @param format The string to be formatted.
    * @param args The arguments referenced by the format string.
    * @return The message edited
    */
    Message editMessageFormat(String messageId, String format, Object... args);

    /**
    * Edit a message by ID
    * @param messageId The message ID
    * @param message The message builder
    * @return The message edited
    */
    Message editMessage(String messageId, MessageBuilder message);

    /**
    * Edit an embed message by ID
    * @param messageId The message ID
    * @param message The new embed of the message
    * @return The message edited
    */
    Message editMessage(String messageId, EmbedMessageBuilder message);

    /**
    * Delete a message by ID.
    * @param messageId The Id of the message.
    *
    * @exception IllegalArgumentException If this channel is a PrivateChannel and the message is from another user.
    * @exception PermissionException If this channel is a TextChannel and the user lack Manage Messages permission to delete other's message.
    *
    * @return The message deleted.
    */
    Message deleteMessage(String messageId);

    /**
    * Delete a message.
    * @param message The the message.
    *
    * @exception IllegalArgumentException If this channel is a PrivateChannel and the message is from another user.
    * @exception PermissionException If this channel is a TextChannel and the user lack Manage Messages permission to delete other's message.
    *
    * @return The message deleted.
    */
    Message deleteMessage(Message message);

    /**
    * Pin a message by ID
    * @param messageId The message id.
    */
    void pinMessage(String messageId);

    /**
    * Pin a message
    * @param message The message object.
    */
    void pinMessage(Message message);

}
