package org.alienideology.jcord.internal.object.bot;

import org.alienideology.jcord.bot.BotInviteBuilder;
import org.alienideology.jcord.handle.bot.IBot;
import org.alienideology.jcord.handle.bot.IBotApplication;
import org.alienideology.jcord.internal.object.DiscordObject;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.ObjectBuilder;
import org.alienideology.jcord.internal.rest.HttpPath;
import org.alienideology.jcord.internal.rest.Requester;
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

    public IBotApplication getAsApplication() {
        JSONObject json = new Requester(identity, HttpPath.Application.GET_BOT_APPLICATION)
                .request()
                .getAsJSONObject();
        return new ObjectBuilder(identity).buildBotApplication(json);
    }

    public String getBotId() {
        return identity.getSelf().getId();
    }

    public BotInviteBuilder getInviteBuilder() {
        return inviteBuilder;
    }
}
