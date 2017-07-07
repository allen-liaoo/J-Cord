package org.alienideology.jcord.handle.emoji;

import java.util.List;

/**
 * Emoji - A Discord emoji.
 *
 * @author AlienIdeology
 */
public class Emoji {

    private final EmojiCategory category;
    private String name;
    private final String unicode;

    private final List<String> keywords;
    private final List<String> aliases;

    Emoji(EmojiCategory category, String name, String unicode, List<String> keywords, List<String> aliases) {
        this.category = category;
        this.name = name;
        this.unicode = unicode;
        this.keywords = keywords;
        this.aliases = aliases;
    }

    public EmojiCategory getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getUnicode() {
        return unicode;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public List<String> getAliases() {
        return aliases;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Emoji) && unicode.equals(((Emoji) obj).getUnicode());
    }

    @Override
    public String toString() {
        return unicode;
    }

}
