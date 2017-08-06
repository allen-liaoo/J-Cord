package org.alienideology.jcord.internal.object.managers;

import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.managers.ISelfManager;
import org.alienideology.jcord.handle.user.IGame;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.handle.user.OnlineStatus;
import org.alienideology.jcord.internal.gateway.OPCode;
import org.alienideology.jcord.internal.object.DiscordObject;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.user.Game;
import org.alienideology.jcord.internal.rest.HttpPath;
import org.alienideology.jcord.internal.rest.Requester;
import org.json.JSONObject;

/**
 * @author AlienIdeology
 */
public final class SelfManager extends DiscordObject implements ISelfManager {

    public SelfManager(IdentityImpl identity) {
        super(identity);
    }

    @Override
    public IUser getSelf() {
        return identity.getSelf();
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

        identity.getGateway().send(OPCode.STATUS_UPDATE, content);
    }

    @Override
    public void leaveGuild(IGuild guild) {
        new Requester(identity, HttpPath.User.LEAVE_GUILD).request(guild.getId()).performRequest();
    }
}
