package org.alienideology.jcord.internal.object.channel;

import org.alienideology.jcord.IdentityType;
import org.alienideology.jcord.handle.builders.MessageBuilder;
import org.alienideology.jcord.handle.channel.IMessageChannel;
import org.alienideology.jcord.handle.channel.ITextChannel;
import org.alienideology.jcord.handle.channel.MessageHistory;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IGuildEmoji;
import org.alienideology.jcord.handle.guild.IMember;
import org.alienideology.jcord.handle.message.IEmbed;
import org.alienideology.jcord.handle.message.IMessage;
import org.alienideology.jcord.handle.permission.Permission;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.exception.ErrorResponseException;
import org.alienideology.jcord.internal.exception.HigherHierarchyException;
import org.alienideology.jcord.internal.exception.HttpErrorException;
import org.alienideology.jcord.internal.exception.PermissionException;
import org.alienideology.jcord.internal.gateway.ErrorResponse;
import org.alienideology.jcord.internal.gateway.HttpCode;
import org.alienideology.jcord.internal.gateway.HttpPath;
import org.alienideology.jcord.internal.gateway.Requester;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.ObjectBuilder;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.message.Message;
import org.alienideology.jcord.internal.object.user.User;
import org.alienideology.jcord.util.DataUtils;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author AlienIdeology
 */
public class MessageChannel extends Channel implements IMessageChannel {

    private Guild guild;
    private MessageHistory history;

    /**
     * Constructor for PrivateChannel
     */
    public MessageChannel(IdentityImpl identity, String id, Type type) {
        this(identity, id, type, null);
    }

    /**
     * Constructor for TextChannel
     */
    public MessageChannel(IdentityImpl identity, String id, Type type, Guild guild) {
        super(identity, id, type);
        this.guild = guild;
        this.history = new MessageHistory(this);
    }

    @Override
    @Nullable
    public IGuild getGuild() {
        return guild;
    }

    @Override
    public MessageHistory getHistory() {
        return history;
    }

    @Override
    public IMessage getMessage(String id) {
        if (!isPrivate && !((ITextChannel)this).hasPermission(getGuild().getSelfMember(), Permission.ADMINISTRATOR, Permission.READ_MESSAGE_HISTORY)) {
            throw new PermissionException(Permission.ADMINISTRATOR, Permission.READ_MESSAGE_HISTORY);
        }

        if (getHistory().getCachedMessages().get(id) != null) return getHistory().getCachedMessages().get(id);

        JSONObject msg = new Requester(identity, HttpPath.Channel.GET_CHANNEL_MESSAGE).request(this.id, id).getAsJSONObject();
        return new ObjectBuilder(identity).buildMessage(msg);
    }

    @Override
    public IMessage sendMessage(String message) {
        return send(((Message) new MessageBuilder().setContent(message).build()).toJson());
    }

    @Override
    public IMessage sendMessageFormat(String format, Object... args) {
        return sendMessage(new MessageBuilder().appendContentFormat(format, args).build().toString());
    }

    @Override
    public IMessage sendMessage(IMessage message) {
        return send(((Message)message).toJson());
    }

    @Override
    public IMessage sendMessage(IEmbed embed) {
        return send(((Message) new MessageBuilder().setEmbed(embed).build()).toJson());
    }

    private Message send(JSONObject json) {
        checkContentLength(json.getString("content"));
        if (isPrivate && identity.getType() == IdentityType.BOT) { // Cannot send a private message from bot to bot
            throw new ErrorResponseException(ErrorResponse.CANNOT_SEND_MESSAGES_TO_THIS_USER);
        }

        if (!isPrivate && !((ITextChannel)this).hasPermission(getGuild().getSelfMember(), Permission.ADMINISTRATOR, Permission.SEND_MESSAGES)) {
            throw new PermissionException(Permission.ADMINISTRATOR, Permission.SEND_MESSAGES);
        }

        JSONObject msg = new Requester(identity, HttpPath.Channel.CREATE_MESSAGE).request(id)
                .updateRequestWithBody(http -> http.body(json)).getAsJSONObject();
        Message message = new ObjectBuilder(identity).buildMessage(msg);
        return message;
    }

    @Override
    public IMessage sendAttachment(File file, String message) throws IOException {
        return attach(file, ((Message) new MessageBuilder().setContent(message).build()).toJson());
    }

    @Override
    public IMessage sendAttachmentFormat(File file, String format, Object... args) throws IOException {
        return sendAttachment(file, String.format(format, args));
    }

    @Override
    public IMessage sendAttachment(File file, IMessage message) throws IOException {
        return attach(file, ((Message) message).toJson());
    }

    private IMessage attach(File file, JSONObject message) throws IOException {
        if (isPrivate) {
            if (identity.getType() == IdentityType.BOT) { // Cannot send a private message from bot to bot
                throw new ErrorResponseException(ErrorResponse.CANNOT_SEND_MESSAGES_TO_THIS_USER);
            }
        } else {
            if (!((ITextChannel) this).hasPermission(getGuild().getSelfMember(), Permission.ADMINISTRATOR, Permission.SEND_MESSAGES)) {
                throw new PermissionException(Permission.ADMINISTRATOR, Permission.SEND_MESSAGES);
            }

            if (!((ITextChannel) this).hasPermission(getGuild().getSelfMember(), Permission.ADMINISTRATOR, Permission.ATTACH_FILES)) {
                throw new PermissionException(Permission.ADMINISTRATOR, Permission.ATTACH_FILES);
            }
        }

        if (!file.exists() || file.isDirectory() || !file.canRead()) {
            throw new FileNotFoundException("The provided file to send can not be found or read from!");
        }

        if (file.length() / (1024 ^ 2) > 8) {
            throw new IllegalArgumentException("The file provided is too large to send!");
        }

        // Get the RequestWithBody object, use it to get MultipartBody object (#field)
        JSONObject msg = new Requester(identity, HttpPath.Channel.CREATE_MESSAGE, false)
                .request(id).updateRequestWithBody(request ->
                        request.field("file", file, file.getName())
                            .field("payload_json", message.toString())).getAsJSONObject();

        return new ObjectBuilder(identity).buildMessage(msg);
    }

    @Override
    public IMessage editMessage(String messageId, String message) {
        return edit(((Message) new MessageBuilder().setContent(message).build()).toJson(), messageId);
    }

    @Override
    public IMessage editMessageFormat(String messageId, String format, Object... args) {
        return edit(((Message)new MessageBuilder().appendContentFormat(format, args).build()).toJson(), messageId);
    }

    @Override
    public IMessage editMessage(String messageId, IMessage message) {
        return edit(((Message) message).toJson(), messageId);
    }

    @Override
    public IMessage editMessage(String messageId, IEmbed message) {
        return edit(((Message) new MessageBuilder().setEmbed(message).build()).toJson(), messageId);
    }

    private Message edit(JSONObject json, String id) {
        checkContentLength(json.getString("content"));

        User author = new ObjectBuilder(identity).buildMessageById(this.id, id).getAuthor();

        if (!isPrivate && !author.isSelf()) {
            // Edit message from other people
            if (!((ITextChannel)this).hasPermission(getGuild().getSelfMember(), Permission.ADMINISTRATOR, Permission.MANAGE_MESSAGES))
                throw new PermissionException(Permission.MANAGE_MESSAGES);
            // Edit message from server owner
            if (getGuild().getOwner().getUser().equals(author) && !getGuild().getSelfMember().isOwner())
                throw new PermissionException("Can not edit a message sent by the server owner!");
        }

        // The MessageUpdateEvent get fired by this, but will be ignored
        JSONObject msg = new Requester(identity, HttpPath.Channel.EDIT_MESSAGE).request(this.id, id)
                .updateRequestWithBody(http -> http.header("Content-Type", "application/json").body(json)).getAsJSONObject();
        Message edited = new ObjectBuilder(identity).buildMessage(msg);
        return edited;
    }

    private void checkContentLength(String content) {
        if (content.length() > IMessage.CONTENT_LENGTH_MAX) {  // Message content can by up to 2000 characters
            IllegalArgumentException exception = new IllegalArgumentException("String messages can only contains up to 2000 characters.");
            exception.printStackTrace();
            throw new IllegalArgumentException();
        }
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
            } else if (!((ITextChannel)this).hasPermission(getGuild().getSelfMember(), Permission.ADMINISTRATOR, Permission.MANAGE_MESSAGES)) {
                throw new PermissionException(Permission.ADMINISTRATOR, Permission.MANAGE_MESSAGES);
            }
        }

        JSONObject msg = new Requester(identity, HttpPath.Channel.DELETE_MESSAGE).request(this.id, id).getAsJSONObject();

        return new ObjectBuilder(identity).buildMessage(msg);
    }

    @Override
    public void bulkDeleteMessages(boolean throwEx, List<IMessage> messages) {
        if (isPrivate) {
            throw new IllegalArgumentException("Cannot bulk delete messages in a PrivateChannel!");
        } else if (!((ITextChannel)this).hasPermission(getGuild().getSelfMember(), Permission.ADMINISTRATOR, Permission.MANAGE_MESSAGES)) {
            throw new PermissionException(Permission.ADMINISTRATOR, Permission.MANAGE_MESSAGES);
        }
        if (messages.isEmpty()) {
            throw new IllegalArgumentException("A list of messages to delete may not be empty!");
        }

        // Throw Exceptions
        if (throwEx) {
            for (IMessage message : messages) {
                // Check if the message is from this channel
                if (!message.getChannel().equals(this)) {
                    throw new ErrorResponseException(ErrorResponse.UNKNOWN_CHANNEL);
                }

                // Check if message is older than 2 weeks
                if (message.getCreatedTime().isBefore(message.getCreatedTime().minusWeeks(2))) {
                    throw new IllegalArgumentException("Cannot delete messages older than two weeks!");
                }
            }
        }

        List<String> ids = messages.isEmpty() ? new ArrayList<>() : messages.stream().map(IMessage::getId).collect(Collectors.toList());

        new Requester(identity, HttpPath.Channel.BULK_DELETE_MESSAGE).request(this.id)
                .updateRequestWithBody(request -> request.body(new JSONObject().put("messages", ids)))
                .performRequest();
    }

    @Override
    public void pinMessage(String messageId) {
        if (isPrivate) { // Ignore private messages
        } else if (!((ITextChannel)this).hasPermission(getGuild().getSelfMember(), Permission.ADMINISTRATOR, Permission.MANAGE_MESSAGES)) {
            throw new PermissionException(Permission.ADMINISTRATOR, Permission.MANAGE_MESSAGES);
        }

        try {
            new Requester(identity, HttpPath.Channel.ADD_PINNED_MESSAGE).request(this.id, id).performRequest();
        } catch (HttpErrorException ex) {
            if (ex.getCode().equals(HttpCode.BAD_REQUEST)) {
                throw new ErrorResponseException(ErrorResponse.UNKNOWN_MESSAGE);
            } else {
                throw ex;
            }
        }
    }

    @Override
    public void pinMessage(IMessage message) {
        pinMessage(message.getId());
    }

    @Override
    public void unpinMessage(String messageId) {
        if (isPrivate) {
        } else if (!((ITextChannel)this).hasPermission(getGuild().getSelfMember(), Permission.ADMINISTRATOR, Permission.MANAGE_MESSAGES)) {
            throw new PermissionException(Permission.ADMINISTRATOR, Permission.MANAGE_MESSAGES);
        }

        try {
            new Requester(identity, HttpPath.Channel.DELETE_PINNED_MESSAGE).request(this.id, messageId).performRequest();
        } catch (HttpErrorException ex) {
            if (ex.getCode().equals(HttpCode.BAD_REQUEST)) {
                throw new ErrorResponseException(ErrorResponse.UNKNOWN_MESSAGE);
            } else {
                throw ex;
            }
        }
    }

    @Override
    public void unpinMessage(IMessage message) {
        unpinMessage(message.getId());
    }

    @Override
    public List<IUser> getReactedUsers(String messageId, String unicode) {
        return getReacts(messageId, unicode);
    }

    @Override
    public List<IUser> getReactedUsers(String messageId, IGuildEmoji guildEmoji) {
        return getReacts(messageId, guildEmoji.getName()+":"+guildEmoji.getId());
    }

    private List<IUser> getReacts(String messageId, String emoji) {
        try {
            final JSONArray reacters = new Requester(identity, HttpPath.Channel.GET_REACTIONS).request(this.id, messageId, emoji)
                    .getAsJSONArray();

            List<IUser> users = new ArrayList<>();
            for (int i = 0; i < reacters.length(); i++) {
                users.add(identity.getUser(reacters.getJSONObject(i).getString("id")));
            }

            return users;
        } catch (HttpErrorException ex) {
            if (ex.getCode().equals(HttpCode.NOT_FOUND)) { // Can be unknown message or reaction
                throw new ErrorResponseException(ErrorResponse.UNKNOWN);
            } else {
                throw ex;
            }
        }
    }

    @Override
    public void addReaction(String messageId, String unicode) {
        react(messageId, DataUtils.encodeToUrl(unicode));
    }

    @Override
    public void addReaction(String messageId, IGuildEmoji guildEmoji) {
        react(messageId, guildEmoji.getName()+":"+ guildEmoji.getId());
    }

    private void react(String messageId, String emoji) {
        if (!isPrivate && !((ITextChannel) this).hasPermission(getGuild().getSelfMember(), Permission.ADMINISTRATOR, Permission.READ_MESSAGE_HISTORY)) {
            throw new PermissionException(Permission.ADMINISTRATOR, Permission.READ_MESSAGE_HISTORY);
        }

        try {
            new Requester(identity, HttpPath.Channel.CREATE_REACTION).request(this.id, messageId, emoji)
                    .performRequest();
        } catch (HttpErrorException ex) {
            if (ex.isPermissionException()) { // If nobody else has reacted to the message using this emoji
                throw new PermissionException(Permission.ADD_REACTIONS);
            } else if (ex.getCode().equals(HttpCode.NOT_FOUND)) {
                throw new ErrorResponseException(ErrorResponse.UNKNOWN_MESSAGE);
            } else {
                throw ex;
            }
        }
    }

    @Override
    public void removeReaction(IMember member, String messageId, String unicode) {
        unreact(member, messageId, DataUtils.encodeToUrl(unicode));
    }

    @Override
    public void removeReaction(IMember member, String messageId, IGuildEmoji guildEmoji) {
        unreact(member, messageId, guildEmoji.getName()+":"+ guildEmoji.getId());
    }

    private void unreact(IMember member, String messageId, String emoji) {
        // Removing self reaction
        if (member.getId().equals(identity.getSelf().getId())) {
            try {
                new Requester(identity, HttpPath.Channel.DELETE_REACTION_BY_SELF).request(this.id, messageId, emoji)
                        .performRequest();
            } catch (HttpErrorException ex) {
                if (ex.getCode().equals(HttpCode.NOT_FOUND)) {
                    throw new ErrorResponseException(ErrorResponse.UNKNOWN_EMOJI);
                } else {
                    throw ex;
                }
            }
        // Remove reactions by others
        } else {
            if (!((ITextChannel) this).hasPermission(getGuild().getSelfMember(), Permission.ADMINISTRATOR, Permission.MANAGE_MESSAGES)) {
                throw new PermissionException(Permission.ADMINISTRATOR, Permission.MANAGE_MESSAGES);
            }

            if (member.isOwner()) {
                throw new HigherHierarchyException(HigherHierarchyException.HierarchyType.OWNER);
            }
            if (!getGuild().getSelfMember().canModify(member)) {
                throw new HigherHierarchyException(HigherHierarchyException.HierarchyType.MEMBER);
            }

            try {
                new Requester(identity, HttpPath.Channel.DELETE_REACTION_BY_USER).request(this.id, messageId, emoji, member.getId())
                        .performRequest();
            } catch (HttpErrorException ex) {
                if (ex.getCode().equals(HttpCode.NOT_FOUND)) {
                    throw new ErrorResponseException(ErrorResponse.UNKNOWN_EMOJI);
                } else {
                    throw ex;
                }
            }
        }
    }

    @Override
    public void removeAllReactions(String messageId) {
        if (isPrivate) {
            throw new IllegalArgumentException("Cannot remove reactions in a PrivateChannel!");
        }

        if (!((ITextChannel) this).hasPermission(getGuild().getSelfMember(), Permission.ADMINISTRATOR, Permission.MANAGE_MESSAGES)) {
            throw new PermissionException(Permission.ADMINISTRATOR, Permission.MANAGE_MESSAGES);
        }

        try {
            new Requester(identity, HttpPath.Channel.DELETE_REACTION_ALL).request(this.id, messageId)
                    .performRequest();
        } catch (HttpErrorException ex) {
            if (ex.getCode().equals(HttpCode.NOT_FOUND)) {
                throw new ErrorResponseException(ErrorResponse.UNKNOWN_MESSAGE);
            } else {
                throw ex;
            }
        }
    }

    @Override
    public void startTyping() {
        if (!isPrivate && !((ITextChannel) this).hasPermission(getGuild().getSelfMember(), Permission.ADMINISTRATOR, Permission.SEND_MESSAGES)) {
            throw new PermissionException(Permission.ADMINISTRATOR, Permission.SEND_MESSAGES);
        }
        new Requester(identity, HttpPath.Channel.TRIGGER_TYPING_INDICATOR).request(id).performRequest();
    }

    @Override
    public String toString() {
        return "MessageChannel{" +
                "key='" + id + '\'' +
                ", isPrivate=" + isPrivate +
                '}';
    }

}
