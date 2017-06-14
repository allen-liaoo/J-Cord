package org.alienideology.jcord.handle.message;

import org.alienideology.jcord.handle.EmojiTable;
import org.alienideology.jcord.handle.IDiscordObject;
import org.alienideology.jcord.handle.guild.IGuildEmoji;

/**
 * Reaction - A emoji that users reacted under a message.
 * @author AlienIdeology
 */
public interface IReaction extends IDiscordObject {

    int getReactedTimes();

    EmojiTable.Emoji getEmoji();

    IGuildEmoji getGuildEmoji();

    boolean isSelfReacted();

    default boolean isGuildEmoji() {
        return getGuildEmoji() != null;
    }

}
