package org.alienideology.jcord.object;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Emoji - A emoji global default in Discord.
 * See a full this of emojis here:
 * https://raw.githubusercontent.com/emojione/emojione/master/emoji.json
 * @author AlienIdeology
 */
public class Emoji {

    private final Category category;
    private String name;
    private final String unicode;
    private final List<String> keywords;

    private final List<String> aliases;

    private final static String EMOJI_URL = "https://raw.githubusercontent.com/emojione/emojione/master/emoji.json";

    Emoji(Category category, String name, String unicode, List<String> keywords, List<String> aliases) {
        this.category = category;
        this.name = name;
        this.unicode = unicode;
        this.keywords = keywords;
        this.aliases = aliases;
    }

    public static Emojis loadEmoji() {
        Emojis emojis = new Emojis();
        try {
            JSONTokener tokener = new JSONTokener(new InputStreamReader(new URL(EMOJI_URL).openStream()));
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return emojis;
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
