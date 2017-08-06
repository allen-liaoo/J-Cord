package org.alienideology.jcord.handle.emoji;

import org.alienideology.jcord.internal.rest.HttpPath;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Emojis - A collection of Default Discord Emojis.
 *
 * @author AlienIdeology
 */
public class Emojis {

    /**
     * A list of all emojis.
     */
    public final static List<Emoji> EMOJIS = new ArrayList<>();

    static {
        try {
            JSONTokener tokener = new JSONTokener(new InputStreamReader(new URL(HttpPath.EndPoint.EMOJI_URL).openStream()));
            JSONObject json = new JSONObject(tokener);

            for (String key : json.keySet()) {
                JSONObject obj = json.getJSONObject(key);

                EmojiCategory category = EmojiCategory.getByName(obj.getString("category"));
                String name = obj.getString("name");
                String rawUnicode[] = obj.getJSONObject("code_points").getString("base").split("-");
                String unicode = "";

                for (String uni : rawUnicode) {
                    // Encode unicode
                    // Use += for multiple modifiers on a emoji (i.e., skin tone)
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
                EMOJIS.add(new Emoji(category, name, unicode, keywords, aliases));
            }
        } catch (IOException ignore) {}
    }

    /**
     * Get a list of emojis by the category.
     *
     * @param category The emoji category.
     * @return The emojis found.
     */
    public static List<Emoji> getByCategory(EmojiCategory category) {
        List<Emoji> emojisList = new ArrayList<>();
        for (Emoji emoji : EMOJIS) {
            if (emoji.getCategory().equals(category))
                emojisList.add(emoji);
        }
        return emojisList;
    }


    /**
     * Get an emoji by name.
     *
     * @param name The name.
     * @return An emoji, or null if no name matches.
     */
    @Nullable
    public static Emoji getByName(String name) {
        for (Emoji emoji : EMOJIS) {
            if (emoji.getName().equals(name))
                return emoji;
        }
        return null;
    }

    /**
     * Get an emoji by keyword.
     *
     * @param keyword The keyword.
     * @return An emoji, or null if no keyword matches.
     */
    @Nullable
    public static Emoji getByKeyword(String keyword) {
        for (Emoji emoji : EMOJIS) {
            if (emoji.getKeywords().stream().anyMatch(a -> a.equals(keyword)))
                return emoji;
        }
        return null;
    }

    /**
     * Get an emoji by alias.
     *
     * @param alias The alias.
     * @return An emoji, or null if no alias matches.
     */
    @Nullable
    public static Emoji getByAlias(String alias) {
        if (!alias.startsWith(":")) alias = ":" + alias;
        if (!alias.endsWith(":")) alias = alias + ":";
        String process = alias;

        for (Emoji emoji : EMOJIS) {
            if (emoji.getAliases().stream().anyMatch(a -> a.equals(process)))
                return emoji;
        }
        return null;
    }

    /**
     * Get an emoji by unicode.
     *
     * @param unicode The unicode.
     * @return An emoji, or null if no unicode matches.
     */
    @Nullable
    public static Emoji getByUnicode(String unicode) {
        for (Emoji emoji : EMOJIS) {
            if (emoji.getUnicode().equals(unicode))
                return emoji;
        }
        return null;
    }

}
