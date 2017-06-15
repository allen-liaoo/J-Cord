package org.alienideology.jcord.handle.message;

import com.sun.istack.internal.Nullable;
import org.alienideology.jcord.handle.EmojiTable;
import org.alienideology.jcord.handle.IDiscordObject;
import org.alienideology.jcord.handle.guild.IGuildEmoji;

/**
 * Reaction - A emoji that users reacted under a message.
 * @author AlienIdeology
 */
public interface IReaction extends IDiscordObject {

    /**
     * Get the total reacted times of this reaction
     *
     * @return The integer value of reacted times
     */
    int getReactedTimes();

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
