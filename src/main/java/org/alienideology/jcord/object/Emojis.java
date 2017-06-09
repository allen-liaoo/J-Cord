package org.alienideology.jcord.object;

import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Emojis - A list of emojis.
 * @author AlienIdeology
 */
public class Emojis extends ArrayList<Emoji> {

    public List<Emoji> getByCategory(Emoji.Category category) {
        List<Emoji> emojisList = new ArrayList<>();
        for (Emoji emoji : this) {
            if (emoji.getCategory().equals(category))
                emojisList.add(emoji);
        }
        return emojisList;
    }


    @Nullable
    public Emoji getByName(String name) {
        for (Emoji emoji : this) {
            if (emoji.getName().equals(name))
                return emoji;
        }
        return null;
    }

    @Nullable
    public Emoji getByKeyword(String keyword) {
        for (Emoji emoji : this) {
            if (emoji.getKeywords().stream().anyMatch(a -> a.equals(keyword)))
                return emoji;
        }
        return null;
    }

    @Nullable
    public Emoji getByAlias(String alias) {
        if (!alias.startsWith(":")) alias = ":" + alias;
        if (!alias.endsWith(":")) alias = alias + ":";
        String process = alias;

        for (Emoji emoji : this) {
            if (emoji.getAliases().stream().anyMatch(a -> a.equals(process)))
                return emoji;
        }
        return null;
    }

}
