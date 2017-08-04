package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.event.message.MessageReactionAddEvent;
import org.alienideology.jcord.event.message.MessageReactionRemoveEvent;
import org.alienideology.jcord.handle.message.IReaction;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.MessageChannel;
import org.alienideology.jcord.internal.object.message.Message;
import org.alienideology.jcord.internal.object.message.Reaction;
import org.alienideology.jcord.internal.object.user.User;
import org.alienideology.jcord.util.log.LogLevel;
import org.json.JSONObject;

/**
 * @author AlienIdeology
 */
public class MessageReactionEventHandler extends EventHandler {

    private boolean isAdd;

    public MessageReactionEventHandler(IdentityImpl identity, boolean isAdd) {
        super(identity);
        this.isAdd = isAdd;
    }

    @Override
    public void dispatchEvent(JSONObject json, int sequence) {
        MessageChannel channel = (MessageChannel) identity.getMessageChannel(json.getString("channel_id"));

        if (channel == null) {
            logger.log(LogLevel.FETAL, "[UNKNOWN MESSAGE] [MESSAGE_REACTION_ADD/REMOVE]");
            return;
        }

        User user = (User) identity.getUser(json.getString("user_id"));
        Message message = (Message) channel.getMessage(json.getString("message_id"));

        JSONObject emojiJson = json.getJSONObject("emoji");
        boolean isGuildEmoji = emojiJson.has("id") && !emojiJson.isNull("id");

        if (isAdd) {
            Reaction reaction = builder.buildReaction(
                    new JSONObject()
                            .put("count", 1)
                            .put("emoji", emojiJson), message);
            message.addReaction(reaction);

            dispatchEvent(new MessageReactionAddEvent(identity, sequence, channel, message, user, reaction));
        } else {

            Reaction reaction = null;
            try {
                if (isGuildEmoji) {
                    reaction = (Reaction) message.getReactions()
                            .stream()
                            .filter(IReaction::isGuildEmoji)
                            .filter(r -> r.getGuildEmoji().getId().equals(emojiJson.getString("id"))).findFirst().get();
                } else {
                    reaction = (Reaction) message.getReactions()
                            .stream()
                            .filter(r -> !r.isGuildEmoji())
                            .filter(r -> r.getEmoji().getUnicode().equals(emojiJson.getString("name"))).findFirst().get();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            message.removeReaction(reaction);

            dispatchEvent(new MessageReactionRemoveEvent(identity, sequence, channel, message, user, reaction));
        }
    }

}
