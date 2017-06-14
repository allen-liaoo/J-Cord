package org.alienideology.jcord.internal.object;

import com.sun.istack.internal.Nullable;
import org.alienideology.jcord.internal.gateway.HttpPath;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * EmojiTable - A collection of Default Discord Emojis.
 * See a full this of emojis here:
 * https://raw.githubusercontent.com/emojione/emojione/master/emoji.json
 * @author AlienIdeology
 */
public class EmojiTable {

    private final List<Emoji> emojis;

    public EmojiTable() {
        this.emojis = new ArrayList<>();

        try {
            JSONTokener tokener = new JSONTokener(new InputStreamReader(new URL(HttpPath.EndPoint.EMOJI_URL).openStream()));
            JSONObject json = new JSONObject(tokener);

            for (String key : json.keySet()) {
                JSONObject obj = json.getJSONObject(key);
                Category category = Category.getByName(obj.getString("category"));
                String name = obj.getString("name");
                String rawUnicode[] = obj.getJSONObject("code_points").getString("base").split("-");
                String unicode = "";
                for (String uni : rawUnicode) {
                    unicode += String.valueOf(Character.toChars(Integer.valueOf(uni, 16)));
                }

                List<String> keywords = new ArrayList<>();
                for (int j = 0; j < obj.getJSONArray("keywords").length(); j++) {
                    keywords.add(obj.getJSONArray("keywords").getString(j));
                }

                List<String> aliases = new ArrayList<>();
                aliases.add(obj.getString("shortname"));
                for (int k = 0; k < obj.getJSONArray("shortname_alternates").length(); k++) {
                    keywords.add(obj.getJSONArray("shortname_alternates").getString(k));
                }
                emojis.add(new Emoji(category, name, unicode, keywords, aliases));
            }
        } catch (IOException ignore) {}
    }

    public List<Emoji> getEmojis() {
        return emojis;
    }

    public List<Emoji> getByCategory(EmojiTable.Category category) {
        List<Emoji> emojisList = new ArrayList<>();
        for (Emoji emoji : emojis) {
            if (emoji.getCategory().equals(category))
                emojisList.add(emoji);
        }
        return emojisList;
    }


    @Nullable
    public Emoji getByName(String name) {
        for (Emoji emoji : emojis) {
            if (emoji.getName().equals(name))
                return emoji;
        }
        return null;
    }

    @Nullable
    public Emoji getByKeyword(String keyword) {
        for (Emoji emoji : emojis) {
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

        for (Emoji emoji : emojis) {
            if (emoji.getAliases().stream().anyMatch(a -> a.equals(process)))
                return emoji;
        }
        return null;
    }

    @Nullable
    public Emoji getByUnicode(String unicode) {
        for (Emoji emoji : emojis) {
            if (emoji.getUnicode().equals(unicode))
                return emoji;
        }
        return null;
    }

    public static class Emoji {

        private final Category category;
        private String name;
        private final String unicode;

        private final List<String> keywords;
        private final List<String> aliases;

        Emoji(Category category, String name, String unicode, List<String> keywords, List<String> aliases) {
            this.category = category;
            this.name = name;
            this.unicode = unicode;
            this.keywords = keywords;
            this.aliases = aliases;
        }

        public Category getCategory() {
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

    public enum Category {
        PEOPLE ("people"),
        NATURE ("nature"),
        FOOD ("food"),
        ACTIVITIES ("activity"),
        TRAVEL ("travel"),
        OBJECTS ("objects"),
        SYMBOLS ("symbols"),
        FLAGS ("flags"),
        UNKNOWN ("unknown");

        public final String name;

        Category(String name) {
            this.name = name;
        }

        public static Category getByName(String name) {
            for (Category category : Category.values()) {
                if (category.name.equals(name))
                    return category;
            }
            return UNKNOWN;
        }
    }

}
