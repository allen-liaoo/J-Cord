package org.alienideology.jcord.object.user;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.object.DiscordObject;

import java.util.regex.Pattern;

/**
 * @author AlienIdeology
 */
public class Game extends DiscordObject {

    private String name;
    private Type type;
    private String url;

    private final Pattern TWITCH_URL = Pattern.compile("(http|https)(://)(www\\.|)(twitch)(\\.tv/)");

    public Game(Identity identity, String name) {
        super(identity);
        this.name = name;
        this.type = Type.PLAYING;
        this.url = null;
    }

    public Game(Identity identity, String name, String url) {
        super(identity);
        this.name = name;
        this.type = Type.STREAMING;
        if (url != null && !url.matches(TWITCH_URL.pattern())) {
            throw new IllegalArgumentException("Streaming game type url only support valid twitch urls!");
        }
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Game) {
            Game game = (Game) obj;
            return name.equals(game.getName())
                    && type.equals(game.getType())
                    && url.equals(game.getUrl());
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return type == Type.PLAYING ? "Playing: " : "Streaming: " + name;
    }

    public enum Type {
        PLAYING (0),
        STREAMING (1);

        public final int id;

        Type(int id) {
            this.id = id;
        }
    }
}
