package org.alienideology.jcord.internal.object.user;

import org.alienideology.jcord.handle.user.IGame;
import org.alienideology.jcord.internal.object.DiscordObject;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.Jsonable;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

/**
 * @author AlienIdeology
 */
public final class Game extends DiscordObject implements IGame, Jsonable {

    private String name;
    private IGame.Type type;
    private String url;

    public Game(IdentityImpl identity, String name) {
        super(identity);
        this.name = name;
        this.type = IGame.Type.PLAYING;
        this.url = null;
    }

    public Game(IdentityImpl identity, String name, String url) {
        super(identity);
        this.name = name;
        this.type = url != null ? IGame.Type.STREAMING : IGame.Type.PLAYING;
        this.url = url;
    }

    @Override
    public JSONObject toJson() {
        return new JSONObject()
                    .put("name", name)
                    .put("type", type.key)
                    .put("url", url);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public IGame.Type getType() {
        return type;
    }

    @Override
    public boolean isStreaming() {
        return type.equals(IGame.Type.STREAMING);
    }

    @Override
    @Nullable
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
        return "Game{" +
                "name='" + name + '\'' +
                ", type=" + type +
                '}';
    }

}
