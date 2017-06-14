package org.alienideology.jcord.internal.object.message;

import org.alienideology.jcord.handle.guild.IGuildEmoji;
import org.alienideology.jcord.handle.message.IReaction;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.DiscordObject;
import org.alienideology.jcord.handle.EmojiTable;

/**
 * @author AlienIdeology
 */
public final class Reaction extends DiscordObject implements IReaction {

    private int reactedTimes;
    private boolean selfReacted;

    private EmojiTable.Emoji emoji;
    private IGuildEmoji guildEmoji;

    /**
     * Constructor for Emoji
     */
    public Reaction(IdentityImpl identity, int reactedTimes, boolean selfReacted, EmojiTable.Emoji emoji) {
        super(identity);
        this.reactedTimes = reactedTimes;
        this.selfReacted = selfReacted;
        this.emoji = emoji;
        this.guildEmoji = null;
    }

    /**
     * Constructor for GuildEmoji
     */
    public Reaction(IdentityImpl identity, int reactedTimes, boolean selfReacted, IGuildEmoji guildEmoji) {
        super(identity);
        this.reactedTimes = reactedTimes;
        this.selfReacted = selfReacted;
        this.emoji = null;
        this.guildEmoji = guildEmoji;
    }

    @Override
    public int getReactedTimes() {
        return reactedTimes;
    }

    @Override
    public EmojiTable.Emoji getEmoji() {
        return emoji;
    }

    @Override
    public IGuildEmoji getGuildEmoji() {
        return guildEmoji;
    }

    @Override
    public boolean isSelfReacted() {
        return selfReacted;
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
