package org.alienideology.jcord.object.message;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.object.DiscordObject;
import org.alienideology.jcord.object.EmojiList;
import org.alienideology.jcord.object.guild.GuildEmoji;

/**
 * Reaction - A emoji that users reacted under a message.
 * @author AlienIdeology
 */
public class Reaction extends DiscordObject {

    private int reactedTimes;
    private boolean selfReacted;

    private EmojiList.Emoji emoji;
    private GuildEmoji guildEmoji;

    /**
     * Constructor for Emoji
     */
    public Reaction(Identity identity, int reactedTimes, boolean selfReacted, EmojiList.Emoji emoji) {
        super(identity);
        this.reactedTimes = reactedTimes;
        this.selfReacted = selfReacted;
        this.emoji = emoji;
        this.guildEmoji = null;
    }

    /**
     * Constructor for GuildEmoji
     */
    public Reaction(Identity identity, int reactedTimes, boolean selfReacted, GuildEmoji guildEmoji) {
        super(identity);
        this.reactedTimes = reactedTimes;
        this.selfReacted = selfReacted;
        this.emoji = null;
        this.guildEmoji = guildEmoji;
    }

    public int getReactedTimes() {
        return reactedTimes;
    }

    public boolean isSelfReacted() {
        return selfReacted;
    }

    public boolean isGuildEmoji() {
        return guildEmoji != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reaction)) return false;
        if (!super.equals(o)) return false;

        Reaction reaction = (Reaction) o;

        if (reactedTimes != reaction.reactedTimes) return false;
        return selfReacted == reaction.selfReacted;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + reactedTimes;
        result = 31 * result + (selfReacted ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        if (guildEmoji != null) {
            return "Reaction: "+reactedTimes+" "+guildEmoji.getName()+" "+guildEmoji.getId()+"\t";
        } else {
            return "Reaction: "+reactedTimes+" "+emoji.getName()+" "+emoji.getUnicode();
        }
    }
}
