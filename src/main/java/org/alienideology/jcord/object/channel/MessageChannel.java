package org.alienideology.jcord.object.channel;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import org.alienideology.jcord.Identity;
import org.alienideology.jcord.exception.ErrorResponseException;
import org.alienideology.jcord.gateway.ErrorResponse;
import org.alienideology.jcord.gateway.HttpPath;
import org.alienideology.jcord.object.message.EmbedMessageBuilder;
import org.alienideology.jcord.object.Message;
import org.alienideology.jcord.object.message.MessageBuilder;
import org.json.JSONObject;

/**
 * MessageChannel - A channel that allows users to send message.
 * @author AlienIdeology
 */
public class MessageChannel extends Channel {

    private Message lastMessage;

    public MessageChannel(Identity identity, String id, Type type, Message lastMessage) {
        super(identity, id, type);
        this.lastMessage = lastMessage;
    }

    public Message getLastestMessage() {
        return lastMessage;
    }

    /**
     * Send a string message.
     * @param message The message to be sent.
     * @throws IllegalArgumentException
     *          If the message is more than 2000 characters.
     * @return The message sent.
     */
    public Message sendMessage(String message) {

        if (message.length() > Message.MAX_CONTENT_LENGTH) {  // Message content can by up to 2000 characters
            IllegalArgumentException exception = new IllegalArgumentException("String messages can only contains up to 2000 characters.");
            exception.printStackTrace();
            throw new IllegalArgumentException();
        }

        send(new MessageBuilder().setContent(message).build());
        return lastMessage;
    }

    public Message sendMessageFormat(String format, Object... args) {
        sendMessage(new MessageBuilder().appendContentFormat(format, args));
        return lastMessage;
    }

    public Message sendMessage(MessageBuilder message) {
        send(message.build());
        return lastMessage;
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
        HttpRequestWithBody http = (HttpRequestWithBody) HttpPath.Channel.CREATE_MESSAGE.request(identity, id);
        http.header("Content-Type", "application/json").body(json);
        try {
            JSONObject response = http.asJson().getBody().getObject();
            if (response.has("code")) {
                ErrorResponseException exception = new ErrorResponseException(ErrorResponse.getByKey(response.getInt("code")));
                exception.printStackTrace();
                throw exception;
            }
            System.out.println(response.toString(4));
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }

    /**
     * [API Use Only]
     * @param lastMessage The last message of this channel.
     * @return PrivateChannel for chaining.
     */
    public MessageChannel setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
        return this;
    }

}
