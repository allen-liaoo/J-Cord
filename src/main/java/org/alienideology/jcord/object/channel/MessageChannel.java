package org.alienideology.jcord.object.channel;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import org.alienideology.jcord.Identity;
import org.alienideology.jcord.gateway.HttpPath;
import org.alienideology.jcord.object.message.Message;
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

    public Message getLastMessage() {
        return lastMessage;
    }

    public Message sendMessage(String message) {

        JSONObject msg = new JSONObject()
            .put("content", message);    // message content can by up to 2000 characters

        HttpRequestWithBody http = (HttpRequestWithBody) HttpPath.Channel.CREATE_MESSAGE.request(identity, id);
        http.field("content", message);
        try {
            System.out.println(http.asJson());
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return lastMessage;
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
