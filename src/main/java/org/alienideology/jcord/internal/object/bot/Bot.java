package org.alienideology.jcord.internal.object.bot;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.bot.BotInviteBuilder;
import org.alienideology.jcord.handle.bot.IBot;
import org.alienideology.jcord.handle.bot.IBotApplication;
import org.alienideology.jcord.internal.gateway.HttpPath;
import org.alienideology.jcord.internal.gateway.Requester;
import org.alienideology.jcord.internal.object.DiscordObject;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.ObjectBuilder;
import org.json.JSONObject;

/**
 * @author AlienIdeology
 */
public class Bot extends DiscordObject implements IBot {

    private final BotInviteBuilder inviteBuilder;

    public Bot(IdentityImpl identity) {
        super(identity);
        this.inviteBuilder = new BotInviteBuilder(identity.getSelf().getId());
    }

    @Override
    public Identity getIdentity() {
        return identity;
    }

    public IBotApplication getAsApplication() {
        JSONObject json = new Requester((IdentityImpl) identity, HttpPath.OAuth.GET_CURRENT_APPLICATION_INFORMATION)
                .getAsJSONObject();
        return new ObjectBuilder((IdentityImpl) identity).buildBotApplication(json);
    }

    public String getBotId() {
        return identity.getSelf().getId();
    }

    public BotInviteBuilder getInviteBuilder() {
        return inviteBuilder;
    }
}
