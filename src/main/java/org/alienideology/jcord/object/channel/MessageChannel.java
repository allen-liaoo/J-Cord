package org.alienideology.jcord.object.channel;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.Internal;
import org.alienideology.jcord.gateway.HttpPath;
import org.alienideology.jcord.gateway.Requester;
import org.alienideology.jcord.object.ObjectBuilder;
import org.alienideology.jcord.object.message.EmbedMessageBuilder;
import org.alienideology.jcord.object.Message;
import org.alienideology.jcord.object.message.MessageBuilder;
import org.alienideology.jcord.util.Cache;
import org.json.JSONObject;

/**
 * MessageChannel - A channel that allows users to send message.
 * @author AlienIdeology
 */
public class MessageChannel extends Channel {

    private Message latestMessage;
    private Cache<Message> cachedMessages = new Cache<>();

    public MessageChannel(Identity identity, String id, Type type, Message latestMessage) {
        super(identity, id, type);
        this.latestMessage = latestMessage;
    }

    public Message getLatestMessage() {
        return latestMessage;
    }

    public Cache<Message> getCachedMessages() {
        return cachedMessages;
    }

    /**
     * Get a message by id
     * @param id The id of the message
     * @return The message object
     */
    public Message getMessage(String id) {
        if (latestMessage.getId().equals(id)) return latestMessage;

        JSONObject msg = new Requester(identity, HttpPath.Channel.GET_CHANNEL_MESSAGE).request(this.id, id).getAsJSONObject();
        return new ObjectBuilder(identity).buildMessage(msg);
    }

    /**
     * Send a string message.
     * @param message The message to be sent.
     * @throws IllegalArgumentException
     *          If the message is more than 2000 characters.
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
     * @throws IllegalArgumentException
     *          If the message is more than 2000 characters.
     * @return The message sent.
     */
    public Message sendMessageFormat(String format, Object... args) {
        sendMessage(new MessageBuilder().appendContentFormat(format, args));
        return latestMessage;
    }

    /**
     * Send a message built by MessageBuilder
     * @param message The builder
     * @return The message sent.
     */
    public Message sendMessage(MessageBuilder message) {
        send(message.build());
        return latestMessage;
    }

    /**
     * Send an embed message.
     * @param embed The EmbedMessageBuilder
     * @throws IllegalStateException
     *          If the embed message is built but the embed is empty.
     * @return The message sent.
     */
    public Message sendMessage(EmbedMessageBuilder embed) {
        return sendMessage(new MessageBuilder().setAsEmbed(embed));
    }

    private void send(JSONObject json) {
        checkContentLength(json.getString("content"));

        new Requester(identity, HttpPath.Channel.CREATE_MESSAGE).request(id)
                .updateRequestWithBody(http -> http.header("Content-Type", "application/json").body(json)).performRequest();
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

    private Message edit(JSONObject json, String id) {
        checkContentLength(json.getString("content"));

        // The MessageUpdateEvent get fired by this, but will be ignored
        JSONObject msg = new Requester(identity, HttpPath.Channel.EDIT_MESSAGE).request(this.id, id)
                .updateRequestWithBody(http -> http.header("Content-Type", "application/json").body(json)).getAsJSONObject();
        Message edited = new ObjectBuilder(identity).buildMessage(msg);
        if (id.equals(latestMessage.getId())) setLatestMessage(edited); // Set latest message
        return edited;
    }

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

    /**
     * [API Use Only]
     * Cache messages
     * @param messages The messages to be cached.
     * @return MessageChannel for chaining.
     */
    public MessageChannel cacheMessage(Message... messages) {
        this.cachedMessages.cacheAll(messages);
        return this;
    }

}
