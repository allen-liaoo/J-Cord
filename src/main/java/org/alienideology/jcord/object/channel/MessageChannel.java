package org.alienideology.jcord.object.channel;

import com.sun.istack.internal.Nullable;
import org.alienideology.jcord.Identity;
import org.alienideology.jcord.Internal;
import org.alienideology.jcord.exception.PermissionException;
import org.alienideology.jcord.gateway.HttpPath;
import org.alienideology.jcord.gateway.Requester;
import org.alienideology.jcord.object.Guild;
import org.alienideology.jcord.object.ObjectBuilder;
import org.alienideology.jcord.object.Permission;
import org.alienideology.jcord.object.message.EmbedMessageBuilder;
import org.alienideology.jcord.object.Message;
import org.alienideology.jcord.object.message.MessageBuilder;
import org.json.JSONObject;

/**
 * MessageChannel - A channel that allows users to send message.
 * @author AlienIdeology
 */
public class MessageChannel extends Channel {

    private Guild guild;
    private MessageHistory history;
    private Message latestMessage;

    /**
     * Constructor for PrivateChannel
     */
    public MessageChannel(Identity identity, String id, Type type, Message latestMessage) {
        this(identity, id, type, null, latestMessage);
    }

    /**
     * Constructor for TextChannel
     */
    public MessageChannel(Identity identity, String id, Type type, Guild guild, Message latestMessage) {
        super(identity, id, type);
        this.guild = guild;
        this.history = new MessageHistory(this);
        this.latestMessage = latestMessage;
    }

    @Nullable
    public Guild getGuild() {
        return guild;
    }

    public Message getLatestMessage() {
        return latestMessage;
    }

    public MessageHistory getHistory() {
        return history;
    }

    /**
     * Get a message by id
     * @param id The id of the message
     * @return The message object
     */
    public Message getMessage(String id) {
        if (!isPrivate)
        if (latestMessage.getId().equals(id)) return latestMessage;

        JSONObject msg = new Requester(identity, HttpPath.Channel.GET_CHANNEL_MESSAGE).request(this.id, id).getAsJSONObject();
        return new ObjectBuilder(identity).buildMessage(msg);
    }

    /**
     * Send a string message.
     * @param message The message to be sent.
     * @throws IllegalArgumentException If the message is more than 2000 characters.
     * @exception PermissionException If the user lack Send Messages permission
     * @return The message sent.
     */
    public Message sendMessage(String message) {
        send(new MessageBuilder().setContent(message).build());
        return latestMessage;
    }

    /**
     * Send a string message.
     * @param format The string to be formatted and send.
     * @param args The arguments referenced by the format string.
     * @exception  IllegalArgumentException If the message is more than 2000 characters.
     * @exception PermissionException If the user lack Send Messages permission
     * @return The message sent.
     */
    public Message sendMessageFormat(String format, Object... args) {
        sendMessage(new MessageBuilder().appendContentFormat(format, args));
        return latestMessage;
    }

    /**
     * Send a message built by MessageBuilder
     * @param message The builder
     * @exception PermissionException If the user lack Send Messages permission
     * @return The message sent.
     */
    public Message sendMessage(MessageBuilder message) {
        return send(message.build());
    }

    /**
     * Send an embed message.
     * @param embed The EmbedMessageBuilder
     * @exception  IllegalStateException If the embed message is built but the embed is empty.
     * @exception PermissionException If the user lack Send Messages permission
     * @return The message sent.
     */
    public Message sendMessage(EmbedMessageBuilder embed) {
        return sendMessage(new MessageBuilder().setAsEmbed(embed));
    }

    @Internal
    private Message send(JSONObject json) {
        checkContentLength(json.getString("content"));
        if (isPrivate) {
        } else if (!getGuild().getMember(identity.getSelf().getId()).hasPermissions(Permission.SEND_MESSAGES)) {
            throw new PermissionException(Permission.SEND_MESSAGES);
        }

        JSONObject msg = new Requester(identity, HttpPath.Channel.CREATE_MESSAGE).request(id)
                .updateRequestWithBody(http -> http.header("Content-Type", "application/json").body(json)).getAsJSONObject();
        Message message = new ObjectBuilder(identity).buildMessage(msg);
        setLatestMessage(message);
        return message;
    }

    /**
     * Edit a string message by ID
     * @param messageId The message ID
     * @param message The new string content of the message
     * @return The message edited
     */
    public Message editMessage(String messageId, String message) {
        return edit(new MessageBuilder().setContent(message).build(), messageId);
    }

    /**
     * Format a string message by ID
     * @param messageId The message ID
     * @param format The string to be formatted.
     * @param args The arguments referenced by the format string.
     * @return The message edited
     */
    public Message editMessageFormat(String messageId, String format, Object... args) {
        return edit(new MessageBuilder().appendContentFormat(format, args).build(), messageId);
    }

    /**
     * Edit a message by ID
     * @param messageId The message ID
     * @param message The message builder
     * @return The message edited
     */
    public Message editMessage(String messageId, MessageBuilder message) {
        return edit(message.build(), messageId);
    }

    /**
     * Edit an embed message by ID
     * @param messageId The message ID
     * @param message The new embed of the message
     * @return The message edited
     */
    public Message editMessage(String messageId, EmbedMessageBuilder message) {
        return edit(new MessageBuilder().setAsEmbed(message).build(), messageId);
    }

    @Internal
    private Message edit(JSONObject json, String id) {
        checkContentLength(json.getString("content"));

        // The MessageUpdateEvent get fired by this, but will be ignored
        JSONObject msg = new Requester(identity, HttpPath.Channel.EDIT_MESSAGE).request(this.id, id)
                .updateRequestWithBody(http -> http.header("Content-Type", "application/json").body(json)).getAsJSONObject();
        Message edited = new ObjectBuilder(identity).buildMessage(msg);
        if (id.equals(latestMessage.getId())) setLatestMessage(edited); // Set latest message
        return edited;
    }

    /**
     * Delete a message by ID.
     * @param messageId The Id of the message.
     *
     * @exception IllegalArgumentException If this channel is a PrivateChannel and the message is from another user.
     * @exception PermissionException If this channel is a TextChannel and the user lack Manage Messages permission to delete other's message.
     *
     * @return The message deleted.
     */
    public Message deleteMessage(String messageId) {
        return delete(messageId);
    }

    /**
     * Delete a message.
     * @param message The the message.
     *
     * @exception IllegalArgumentException If this channel is a PrivateChannel and the message is from another user.
     * @exception PermissionException If this channel is a TextChannel and the user lack Manage Messages permission to delete other's message.
     *
     * @return The message deleted.
     */
    public Message deleteMessage(Message message) {
        return delete(message.getId());
    }

    @Internal
    private Message delete(String id) {
        JSONObject msg = new Requester(identity, HttpPath.Channel.GET_CHANNEL_MESSAGE).request(this.id, id).getAsJSONObject();
        Message deleted = new ObjectBuilder(identity).buildMessage(msg);
        if (!deleted.getAuthor().equals(identity.getSelf())) {  // Delete a message from others
            if (isPrivate) {
                throw new IllegalArgumentException("Cannot delete the recipient's message in a PrivateChannel!");
            }
            if (!guild.getMember(identity.getSelf().getId()).hasPermissions(Permission.MANAGE_MESSAGES)) {
                throw new PermissionException(Permission.MANAGE_MESSAGES);
            }
        }
        return deleted;
    }

    @Internal
    private void checkContentLength(String content) {
        if (content.length() > Message.MAX_CONTENT_LENGTH) {  // Message content can by up to 2000 characters
            IllegalArgumentException exception = new IllegalArgumentException("String messages can only contains up to 2000 characters.");
            exception.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    /**
     * [API Use Only]
     * @param latestMessage The last message of this channel.
     * @return MessageChannel for chaining.
     */
    @Internal
    public MessageChannel setLatestMessage(Message latestMessage) {
        this.latestMessage = latestMessage;
        return this;
    }

}
