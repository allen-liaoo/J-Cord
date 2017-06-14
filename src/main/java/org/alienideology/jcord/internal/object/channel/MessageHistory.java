package org.alienideology.jcord.internal.object.channel;

import org.alienideology.jcord.internal.gateway.HttpPath;
import org.alienideology.jcord.internal.gateway.Requester;
import org.alienideology.jcord.internal.object.DiscordObject;
import org.alienideology.jcord.internal.object.Message;
import org.alienideology.jcord.internal.object.ObjectBuilder;
import org.alienideology.jcord.util.Cache;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

/**
 * MessageHistory - The history of a MessageChannel, used to get lists of messages.
 * @author AlienIdeology
 */
public class MessageHistory extends DiscordObject {

    private MessageChannel channel;

    /**
     * A list of messages from old to new (Index small to large)
     */
    private Cache<Message> history = new Cache<>();

    public MessageHistory(MessageChannel channel) {
        super(channel.getIdentity());
        this.channel = channel;
    }

    public MessageChannel getChannel() {
        return channel;
    }

    public Cache<Message> getCachedMessages() {
        Collections.sort(history.toList());
        return history;
    }

    public Message getMessage(String id) {
        return channel.getMessage(id);
    }

    public Message getLatestMessage() {
        return channel.getLatestMessage();
    }

    /**
     * Get the latest messages of this channel.
     * @see #getMessagesBefore(String, int). This is equivilant to {@code #getMessagesBefore(MessageChannel#getLatestMessage#getId, int)}.
     * @param amount The integer amounts of messages to get.
     * @return A list of messages sorted from old to new.
     */
    public List<Message> getLatestMessages(int amount) {
        List<Message> messageList = getMessagesAfter(channel.getLatestMessage().getId(), amount);
        if (!messageList.isEmpty()) messageList.remove(messageList.get(0)); // Remove oldest message. Add latest message.
        messageList.add(channel.getLatestMessage());
        return messageList.subList(messageList.size()-amount > 0 ? messageList.size()-amount : 0, messageList.size());
    }

    /**
     * Get the messages sent after a message. (Does not includes the message itself)
     * @param messageId The ID of the message.
     * @param amount The integer amounts of message to get.
     * @exception IllegalArgumentException If the amount is <= 0 or > 100.
     * @return A list of messages sorted from old to new.
     */
    public List<Message> getMessagesAfter(String messageId, int amount) {
        checkAmount(amount);
        JSONArray messages = new Requester(identity, HttpPath.Channel.GET_CHANNEL_MESSAGES_AFTER)
                .request(channel.getId(), String.valueOf(amount), messageId)
                .getAsJSONArray();

        List<Message> messageList = new ArrayList<>();
        for (int i = 0; i < messages.length(); i++) {
            JSONObject msg = messages.getJSONObject(i);
            Message message = new ObjectBuilder(identity).buildMessage(msg);
            messageList.add(message);
        }
        Collections.sort(messageList); // Sort the list by chronological order
        return messageList;
    }

    /**
     * Get the messages sent before a message. (Does not includes the message itself)
     * @param messageId The ID of the message.
     * @param amount The integer amounts of message to get.
     * @exception IllegalArgumentException If the amount is <= 0 or > 100.
     * @return A list of messages sorted from old to new.
     */
    public List<Message> getMessagesBefore(String messageId, int amount) {
        checkAmount(amount);
        JSONArray messages = new Requester(identity, HttpPath.Channel.GET_CHANNEL_MESSAGES_BEFORE)
                .request(channel.getId(), String.valueOf(amount), messageId)
                .getAsJSONArray();
        List<Message> messageList = new ArrayList<>();
        for (int i = 0; i < messages.length(); i++) {
            JSONObject msg = messages.getJSONObject(i);
            Message message = new ObjectBuilder(identity).buildMessage(msg);
            messageList.add(message);
        }
        Collections.sort(messageList);
        history.cacheAll(messageList.toArray(new Message[messageList.size()]));
        return messageList;
    }

    /**
     * Get the messages sent before and after a message. (Includes the message itself)
     * For example, getMessagesAround(amount = 5) returns a list of 6 messages, includes the message itself.
     * @param messageId The ID of the message.
     * @param amount The integer amounts of message to get (before plus after).
     * @exception IllegalArgumentException If the amount is <= 0 or > 100.
     * @return A list of messages sorted from old to new.
     */
    public List<Message> getMessagesAround(String messageId, int amount) {
        checkAmount(amount);
        JSONArray messages = new Requester(identity, HttpPath.Channel.GET_CHANNEL_MESSAGES_AROUND)
                .request(channel.getId(), String.valueOf(amount), messageId)
                .getAsJSONArray();
        List<Message> messageList = new ArrayList<>();
        for (int i = 0; i < messages.length(); i++) {
            JSONObject msg = messages.getJSONObject(i);
            Message message = new ObjectBuilder(identity).buildMessage(msg);
            messageList.add(message);
        }
        Collections.sort(messageList);
        history.cacheAll(messageList.toArray(new Message[messageList.size()]));
        return messageList;
    }

    /**
     * Get a list of all pinned messages of a channel.
     * @return the list of messages, list size without limits.
     */
    public List<Message> getPinnedMessages() {
        JSONArray pins = new Requester(identity, HttpPath.Channel.GET_PINNED_MESSAGES)
                .request(channel.getId()).getAsJSONArray();

        List<Message> pinnedMessages = new ArrayList<>();
        for (int i = 0; i < pins.length(); i++) {
            JSONObject msg = pins.getJSONObject(i);
            Message message = new ObjectBuilder(identity).buildMessage(msg);
            pinnedMessages.add(message);
        }
        return pinnedMessages;
    }

    private void checkAmount(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("The amount of messages to get can not be greater than zero!\nAmount: "+amount);
        } else if (amount > 100) {
            throw new IllegalArgumentException("The amount of messages to get can not be smaller or equal to one hundred!\nAmount: "+amount);
        }
    }

}
