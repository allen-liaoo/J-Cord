package org.alienideology.jcord.internal.object.managers;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.Icon;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.managers.ISelfManager;
import org.alienideology.jcord.handle.user.IGame;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.handle.user.OnlineStatus;
import org.alienideology.jcord.internal.gateway.HttpPath;
import org.alienideology.jcord.internal.gateway.Requester;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.user.Game;
import org.json.JSONObject;

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
        if (!IUser.isValidUsername(name)) {
            throw new IllegalArgumentException("The username is not valid!");
        }

        modifySelf(new JSONObject().put("username", name));
    }

    @Override
    public void modifyAvatar(Icon icon)  {
        modifySelf(new JSONObject().put("avatar", icon.getData()));
    }

    private void modifySelf(JSONObject json) {
        new Requester(identity, HttpPath.User.MODIFY_CURRENT_USER)
                .request().updateRequestWithBody(request -> request.body(json)).performRequest();
    }

    @Override
    public void setPresence(OnlineStatus status, IGame game) {
        JSONObject content = new JSONObject()
                .put("status", status.getAppropriateKey())
                .put("afk", status.isIdle())
                .put("since", status.isIdle() ? System.currentTimeMillis() : JSONObject.NULL);

        if (game == null) {
            content.put("game", JSONObject.NULL);
        } else {
            content.put("game", ((Game) game).toJson());
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
