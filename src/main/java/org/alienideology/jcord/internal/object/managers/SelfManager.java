package org.alienideology.jcord.internal.object.managers;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.managers.ISelfManager;
import org.alienideology.jcord.handle.user.Game;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.handle.user.OnlineStatus;
import org.alienideology.jcord.internal.gateway.HttpPath;
import org.alienideology.jcord.internal.gateway.Requester;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.util.FileUtils;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author AlienIdeology
 */
public final class SelfManager implements ISelfManager {

    private IdentityImpl identity;

    public SelfManager(IdentityImpl identity) {
        this.identity = identity;
    }

    @Override
    public Identity getIdentity() {
        return identity;
    }

    @Override
    public IUser getSelf() {
        return identity.getSelf();
    }

    @Override
    public void modifyUserName(String name) {
        if (name == null) name = "";
        modifySelf(new JSONObject().put("username", name));
    }

    @Override
    public void modifyAvatar(BufferedImage image) throws IOException {
        String encoding = FileUtils.encodeIcon(image);
        modifySelf(new JSONObject().put("avatar", encoding));
    }

    @Override
    public void modifyAvatar(String path) throws IOException {
        modifyAvatar(ImageIO.read(new File(path)));
    }

    private void modifySelf(JSONObject json) {
        new Requester(identity, HttpPath.User.MODIFY_CURRENT_USER)
                .request().updateRequestWithBody(request -> request.body(json)).performRequest();
    }

    @Override
    public ISelfManager setStatus(OnlineStatus status) {
        setPresence(status, identity.getSelf().getPresence().getGame());
        return this;
    }

    @Override
    public ISelfManager setPlaying(String name) {
        setPresence(identity.getSelf().getPresence().getStatus(), new Game(identity, name));
        return this;
    }

    @Override
    public ISelfManager setStreaming(String name, String url) {
        if (url != null && !url.matches(TWITCH_URL.pattern())) {
            throw new IllegalArgumentException("Streaming game type only support valid twitch urls!");
        }

        setPresence(identity.getSelf().getPresence().getStatus(), new Game(identity, name, url));
        return this;
    }

    private void setPresence(OnlineStatus status, Game game) {
        JSONObject content = new JSONObject()
                .put("status", status.getAppropriateKey())
                .put("afk", status.isIdle())
                .put("since", System.currentTimeMillis());

        if (game != null && game.isStreaming()) {
            content.put("game",
                new JSONObject()
                    .put("name", game.getName())
                    .put("type", game.getType().id)
                    .put("url", game.getUrl())
            );
        } else {
            content.put("game", game == null ? JSONObject.NULL :
                new JSONObject()
                    .put("name", game.getName())
            );
        }

        identity.getSocket().sendText(new JSONObject()
                .put("op", 3)
                .put("d", content)
                .toString());
    }

    @Override
    public void leaveGuild(IGuild guild) {
        new Requester(identity, HttpPath.User.LEAVE_GUILD).request(guild.getId()).performRequest();
    }
}
