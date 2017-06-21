package org.alienideology.jcord.internal.object.channel;

import com.sun.istack.internal.Nullable;
import org.alienideology.jcord.handle.builders.StringMessageBuilder;
import org.alienideology.jcord.handle.channel.IMessageChannel;
import org.alienideology.jcord.handle.channel.ITextChannel;
import org.alienideology.jcord.handle.channel.MessageHistory;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.message.IEmbedMessage;
import org.alienideology.jcord.handle.message.IMessage;
import org.alienideology.jcord.handle.message.IStringMessage;
import org.alienideology.jcord.handle.permission.Permission;
import org.alienideology.jcord.internal.exception.PermissionException;
import org.alienideology.jcord.internal.gateway.HttpPath;
import org.alienideology.jcord.internal.gateway.Requester;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.ObjectBuilder;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.message.EmbedMessage;
import org.alienideology.jcord.internal.object.message.Message;
import org.alienideology.jcord.internal.object.message.StringMessage;
import org.alienideology.jcord.internal.object.user.User;
import org.json.JSONObject;

/**
 * @author AlienIdeology
 */
public class MessageChannel extends Channel implements IMessageChannel {

    private Guild guild;
    private MessageHistory history;
    private Message latestMessage;

    /**
     * Constructor for PrivateChannel
     */
    public MessageChannel(IdentityImpl identity, String id, Type type, Message latestMessage) {
        this(identity, id, type, null, latestMessage);
    }

    /**
     * Constructor for TextChannel
     */
    public MessageChannel(IdentityImpl identity, String id, Type type, Guild guild, Message latestMessage) {
        super(identity, id, type);
        this.guild = guild;
        this.history = new MessageHistory(this);
        this.latestMessage = latestMessage;
    }

    @Override
    @Nullable
    public IGuild getGuild() {
        return guild;
    }

    @Override
    public IMessage getLatestMessage() {
        return latestMessage;
    }

    @Override
    public MessageHistory getHistory() {
        return history;
    }

    @Override
    public IMessage getMessage(String id) {
        if (!isPrivate)
        if (latestMessage.getId().equals(id)) return latestMessage;

        JSONObject msg = new Requester(identity, HttpPath.Channel.GET_CHANNEL_MESSAGE).request(this.id, id).getAsJSONObject();
        return new ObjectBuilder(identity).buildMessage(msg);
    }

    @Override
    public IMessage sendMessage(String message) {
        send(((StringMessage) new StringMessageBuilder().setContent(message).build()).toJson());
        return latestMessage;
    }

    @Override
    public IMessage sendMessageFormat(String format, Object... args) {
        sendMessage(new StringMessageBuilder().appendContentFormat(format, args).build().toString());
        return latestMessage;
    }

    @Override
    public IMessage sendMessage(IStringMessage message) {
        return send(((StringMessage)message).toJson());
    }

    @Override
    public IMessage sendMessage(IEmbedMessage embed) {
        return send(((EmbedMessage) embed).toJson());
    }

    private Message send(JSONObject json) {
        checkContentLength(json.getString("content"));
        if (isPrivate) {
        } else if (!getGuild().getSelfMember().hasPermissions(true, Permission.SEND_MESSAGES)) {
            throw new PermissionException(Permission.SEND_MESSAGES);
        }

        JSONObject msg = new Requester(identity, HttpPath.Channel.CREATE_MESSAGE).request(id)
                .updateRequestWithBody(http -> http.header("Content-GameType", "application/json").body(json)).getAsJSONObject();
        Message message = new ObjectBuilder(identity).buildMessage(msg);
        setLatestMessage(message);
        return message;
    }

    @Override
    public IMessage editMessage(String messageId, String message) {
        return edit(((StringMessage) new StringMessageBuilder().setContent(message).build()).toJson(), messageId);
    }

    @Override
    public IMessage editMessageFormat(String messageId, String format, Object... args) {
        return edit(((StringMessage)new StringMessageBuilder().appendContentFormat(format, args).build()).toJson(), messageId);
    }

    @Override
    public IMessage editMessage(String messageId, IStringMessage message) {
        return edit(((StringMessage) message).toJson(), messageId);
    }

    @Override
    public IMessage editMessage(String messageId, IEmbedMessage message) {
        return edit(((EmbedMessage) message).toJson(), messageId);
    }

    private Message edit(JSONObject json, String id) {
        checkContentLength(json.getString("content"));

        User author = new ObjectBuilder(identity).buildMessageById(this.id, id).getAuthor();

        if (isPrivate) {
        } else if (!author.isSelf()) {
            // Edit message from other people
            if (!getGuild().getSelfMember().hasPermissions(true, Permission.MANAGE_MESSAGES))
                throw new PermissionException(Permission.MANAGE_MESSAGES);
            // Edit message from server owner
            if (getGuild().getOwner().getUser().equals(author) && !getGuild().getSelfMember().isOwner())
                throw new PermissionException("Can not edit a message sent by the server owner!");
        }

        // The MessageUpdateEvent get fired by this, but will be ignored
        JSONObject msg = new Requester(identity, HttpPath.Channel.EDIT_MESSAGE).request(this.id, id)
                .updateRequestWithBody(http -> http.header("Content-GameType", "application/json").body(json)).getAsJSONObject();
        Message edited = new ObjectBuilder(identity).buildMessage(msg);
        if (id.equals(latestMessage.getId())) setLatestMessage(edited); // Set latest message
        return edited;
    }

    @Override
    public IMessage deleteMessage(String messageId) {
        return delete(messageId);
    }

    @Override
    public IMessage deleteMessage(IMessage message) {
        return delete(message.getId());
    }

    private Message delete(String id) {
        User author = new ObjectBuilder(identity).buildMessageById(this.id, id).getAuthor();
        if (!author.isSelf()) {  // Delete a message from others
            if (isPrivate) {
                throw new IllegalArgumentException("Cannot delete the recipient's message in a PrivateChannel!");
            }
            if (!getGuild().getSelfMember().hasPermissions(true, Permission.MANAGE_MESSAGES)) {
                throw new PermissionException(Permission.MANAGE_MESSAGES);
            }
        }

        JSONObject msg = new Requester(identity, HttpPath.Channel.GET_CHANNEL_MESSAGE).request(this.id, id).getAsJSONObject();
        return new ObjectBuilder(identity).buildMessage(msg);
    }

    private void checkContentLength(String content) {
        if (content.length() > Message.MAX_CONTENT_LENGTH) {  // Message content can by up to 2000 characters
            IllegalArgumentException exception = new IllegalArgumentException("String messages can only contains up to 2000 characters.");
            exception.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void pinMessage(String messageId) {
        pin(messageId);
    }

    @Override
    public void pinMessage(IMessage message) {
        pin(message.getId());
    }

    private void pin(String id) {
        if (isPrivate) {
        } else if (!getGuild().getSelfMember().hasPermissions(true, Permission.MANAGE_MESSAGES)) {
             throw new PermissionException(Permission.MANAGE_MESSAGES);
        }

        new Requester(identity, HttpPath.Channel.ADD_PINNED_MESSAGE).request(this.id, id).performRequest();
    }

    @Override
    public void startTyping() {
        if (this instanceof ITextChannel) {
            if (((ITextChannel) this).hasPermission(getGuild().getSelfMember(), Permission.ADMINISTRATOR, Permission.SEND_MESSAGES)) {
                throw new PermissionException(Permission.ADMINISTRATOR, Permission.SEND_MESSAGES);
            }
        }
        new Requester(identity, HttpPath.Channel.TRIGGER_TYPING_INDICATOR).request(id).performRequest();
    }

    @Override
    public String toString() {
        return "MessageChannel{" +
                "id='" + id + '\'' +
                ", isPrivate=" + isPrivate +
                ", latestMessage=" + latestMessage +
                '}';
    }

    public MessageChannel setLatestMessage(IMessage latestMessage) {
        this.latestMessage = (Message) latestMessage;
        return this;
    }

}
