package org.alienideology.jcord.handle.channel;

import org.alienideology.jcord.handle.IDiscordObject;
import org.alienideology.jcord.handle.message.IMessage;
import org.alienideology.jcord.handle.permission.Permission;
import org.alienideology.jcord.internal.exception.PermissionException;
import org.alienideology.jcord.internal.gateway.HttpPath;
import org.alienideology.jcord.internal.gateway.Requester;
import org.alienideology.jcord.internal.object.DiscordObject;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.ObjectBuilder;
import org.alienideology.jcord.internal.object.channel.MessageChannel;
import org.alienideology.jcord.internal.object.message.Message;
import org.alienideology.jcord.util.Cache;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * MessageHistory - The history of a MessageChannel, used to get lists of messages.
 *
 * @author AlienIdeology
 */
public class MessageHistory extends DiscordObject implements IDiscordObject {

    private MessageChannel channel;

    /**
     * A list of messages from old to new (Index small to large)
     */
    private Cache<IMessage> history = new Cache<>();

    public MessageHistory(MessageChannel channel) {
        super((IdentityImpl) channel.getIdentity());
        this.channel = channel;
    }

    /**
     * @return The channel this MessageHistory is about.
     */
    public MessageChannel getChannel() {
        return channel;
    }

    /**
     * Get total cached messages this identity have.
     *
     * @return The cached messages. They may or may not be in chronological order.
     */
    public Cache<IMessage> getCachedMessages() {
        Collections.sort(history.toList());
        return history;
    }

    /**
     * Get a message by ID.
     * @see MessageChannel#getMessage(String)
     *
     * @exception PermissionException
     *          If the identity does not have {@code Read Message History} permission.
     *
     * @param id The string id of the message.
     * @return The message, or null if no message is found.
     */
    public IMessage getMessage(String id) {
        return channel.getMessage(id);
    }

    /**
     * Get the latest message of this channel.
     * @see #getLatestMessages(int). This is equivilant to {@code #getLatestMessages(1)}.
     *
     * @exception PermissionException
     *          If the identity does not have {@code Read Messages} permission.
     *
     * @return The latest message
     */
    public IMessage getLatestMessage() {
        return channel.getLatestMessage();
    }

    /**
     * Get the latest messages of this channel.
     *
     * @exception PermissionException
     *          If the identity does not have {@code Read Messages} permission.
     *
     * @param amount The integer amounts of messages to get.
     * @return A list of messages sorted from old to new.
     */
    public List<IMessage> getLatestMessages(int amount) {
        checkAmount(amount);
        checkPerm();

        JSONArray messages = new Requester(identity, HttpPath.Channel.GET_CHANNEL_MESSAGES)
                .request(channel.getId(), String.valueOf(amount))
                .getAsJSONArray();

        ObjectBuilder builder = new ObjectBuilder(identity);
        List<IMessage> messageList = new ArrayList<>();
        for (int i = 0; i < messages.length(); i++) {
            JSONObject msg = messages.getJSONObject(i);
            Message message = builder.buildMessage(msg);
            messageList.add(message);
        }
        Collections.sort(messageList); // Sort the list by chronological order
        history.cacheAll(messageList.toArray(new IMessage[messageList.size()]));
        return messageList;
    }

    /**
     * Get the messages sent after a message. (Does not includes the message itself)
     *
     * @exception IllegalArgumentException
     *          If the amount is smaller than 0 or greater than 100.
     * @exception PermissionException
     *          If the identity does not have {@code Read Messages} permission.
     *
     * @param messageId The ID of the message.
     * @param amount The integer amounts of message to get.
     * @return A list of messages sorted from old to new.
     */
    public List<IMessage> getMessagesAfter(String messageId, int amount) {
        checkAmount(amount);
        checkPerm();
        JSONArray messages = new Requester(identity, HttpPath.Channel.GET_CHANNEL_MESSAGES_AFTER)
                .request(channel.getId(), String.valueOf(amount), messageId)
                .getAsJSONArray();

        ObjectBuilder builder = new ObjectBuilder(identity);
        List<IMessage> messageList = new ArrayList<>();
        for (int i = 0; i < messages.length(); i++) {
            JSONObject msg = messages.getJSONObject(i);
            Message message = builder.buildMessage(msg);
            messageList.add(message);
        }
        Collections.sort(messageList); // Sort the list by chronological order
        history.cacheAll(messageList.toArray(new IMessage[messageList.size()]));
        return messageList;
    }

    /**
     * Get the messages sent before a message. (Does not includes the message itself)
     *
     * @exception IllegalArgumentException If the amount is <= 0 or > 100.
     * @exception PermissionException
     *          If the identity does not have {@code Read Messages} permission.
     *
     * @param messageId The ID of the message.
     * @param amount The integer amounts of message to get.
     * @return A list of messages sorted from old to new.
     */
    public List<IMessage> getMessagesBefore(String messageId, int amount) {
        checkAmount(amount);
        checkPerm();
        JSONArray messages = new Requester(identity, HttpPath.Channel.GET_CHANNEL_MESSAGES_BEFORE)
                .request(channel.getId(), String.valueOf(amount), messageId)
                .getAsJSONArray();

        ObjectBuilder builder = new ObjectBuilder(identity);
        List<IMessage> messageList = new ArrayList<>();
        for (int i = 0; i < messages.length(); i++) {
            JSONObject msg = messages.getJSONObject(i);
            Message message = builder.buildMessage(msg);
            messageList.add(message);
        }
        Collections.sort(messageList);
        history.cacheAll(messageList.toArray(new IMessage[messageList.size()]));
        return messageList;
    }

    /**
     * Get the messages sent before and after a message. (Includes the message itself)
     * For bot, {@code #getMessagesAround(5)} returns a list of 6 messages, includes the message itself.
     *
     * @exception IllegalArgumentException If the amount is <= 0 or > 100.
     * @exception PermissionException
     *          If the identity does not have {@code Read Messages} permission.
     *
     * @param messageId The ID of the message.
     * @param amount The integer amounts of message to get (before plus after).
     * @return A list of messages sorted from old to new.
     */
    public List<IMessage> getMessagesAround(String messageId, int amount) {
        checkAmount(amount);
        checkPerm();
        JSONArray messages = new Requester(identity, HttpPath.Channel.GET_CHANNEL_MESSAGES_AROUND)
                .request(channel.getId(), String.valueOf(amount), messageId)
                .getAsJSONArray();

        ObjectBuilder builder = new ObjectBuilder(identity);
        List<IMessage> messageList = new ArrayList<>();
        for (int i = 0; i < messages.length(); i++) {
            JSONObject msg = messages.getJSONObject(i);
            IMessage message = builder.buildMessage(msg);
            messageList.add(message);
        }
        Collections.sort(messageList);
        history.cacheAll(messageList.toArray(new IMessage[messageList.size()]));
        return messageList;
    }

    /**
     * Get a list of all pinned messages of a channel.
     *
     * @return the list of messages, list size without limits.
     */
    public List<IMessage> getPinnedMessages() {
        JSONArray pins = new Requester(identity, HttpPath.Channel.GET_PINNED_MESSAGES)
                .request(channel.getId()).getAsJSONArray();

        ObjectBuilder builder = new ObjectBuilder(identity);
        List<IMessage> pinnedMessages = new ArrayList<>();
        for (int i = 0; i < pins.length(); i++) {
            JSONObject msg = pins.getJSONObject(i);
            Message message = builder.buildMessage(msg);
            pinnedMessages.add(message);
        }
        history.cacheAll(pinnedMessages.toArray(new IMessage[pinnedMessages.size()]));
        return pinnedMessages;
    }

    private void checkAmount(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("The amount of messages to get can not be greater than zero!\nAmount: "+amount);
        } else if (amount > 100) {
            throw new IllegalArgumentException("The amount of messages to get can not be smaller or equal to one hundred!\nAmount: "+amount);
        }
    }

    private void checkPerm() {
        if (!channel.isPrivate()) {
            if (!((ITextChannel) channel).hasPermission(channel.getGuild().getSelfMember(), Permission.ADMINISTRATOR, Permission.READ_MESSAGES)) {
                throw new PermissionException(Permission.ADMINISTRATOR, Permission.READ_MESSAGES);
            }
        }
    }

}
