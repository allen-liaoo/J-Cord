package org.alienideology.jcord.handle.message;

import com.sun.istack.internal.Nullable;
import org.alienideology.jcord.handle.EmojiTable;
import org.alienideology.jcord.handle.IDiscordObject;
import org.alienideology.jcord.handle.guild.IGuildEmoji;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.exception.ErrorResponseException;
import org.alienideology.jcord.internal.gateway.ErrorResponse;

import java.util.List;

/**
 * Reaction - A emoji that users reacted under a message.
 * @author AlienIdeology
 */
public interface IReaction extends IDiscordObject {

    /**
     * Get the message this reaction belongs to.
     *
     * @return The message.
     */
    IMessage getMessage();

    /**
     * Get the total reacted times of this reaction
     *
     * @return The integer value of reacted times
     */
    int getReactedTimes();

    /**
     * Get a list of users that reacted to this emoji.
     *
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If the message is not found.
     *          @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_MESSAGE
     *
     * @return A list of users.
     */
    default List<IUser> getReactedUsers() {
        try {
            return isGuildEmoji() ? getMessage().getChannel().getReactedUsers(getMessage().getId(), getGuildEmoji())
                    : getMessage().getChannel().getReactedUsers(getMessage().getId(), getEmoji());
        } catch (ErrorResponseException ex) {
            throw new ErrorResponseException(ErrorResponse.UNKNOWN_MESSAGE);
        }
    }

    /**
     * Get the emoji object of this reaction.
     *
     * @return The emoji or null if this reaction is a GuildEmoji.
     */
    @Nullable
    EmojiTable.Emoji getEmoji();

    /**
     * Get the guild emoji object of this reaction.
     *
     * @return The guild emoji or null if this is a regular emoji.
     */
    @Nullable
    IGuildEmoji getGuildEmoji();

    /**
     * @return True if the identity itself has reacted to this reaction
     */
    boolean isSelfReacted();

    /**
     * @return True if this reaction is a GuildEmoji
     */
    default boolean isGuildEmoji() {
        return getGuildEmoji() != null;
    }

}
