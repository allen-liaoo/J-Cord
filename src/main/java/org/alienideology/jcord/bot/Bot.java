package org.alienideology.jcord.bot;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.internal.gateway.HttpPath;
import org.alienideology.jcord.internal.gateway.Requester;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.ObjectBuilder;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Bot - The core of a Discord Bot
 *
 * @author AlienIdeology
 */
public class Bot {

    private Identity identity;

    private final List<PostAgent> postAgent;
    private final BotInviteBuilder inviteBuilder;

    public Bot(Identity identity) {
        this.identity = identity;
        this.postAgent = new ArrayList<>();
        this.inviteBuilder = new BotInviteBuilder(identity.getSelf().getId());
    }

    /**
     * Get the identity of this bot instance.
     *
     * @return The identity.
     */
    public Identity getIdentity() {
        return identity;
    }

    /**
     * Get the application of this bot.
     *
     * @return The application.
     */
    public Application getAsApplication() {
        JSONObject json = new Requester((IdentityImpl) identity, HttpPath.OAuth.GET_CURRENT_APPLICATION_INFORMATION)
                .getAsJSONObject();
        return new ObjectBuilder((IdentityImpl) identity).buildApplication(json);
    }

    /**
     * Get the bot's ID.
     *
     * @return The id.
     */
    public String getBotId() {
        return identity.getSelf().getId();
    }

    /**
     * Get all post agents for this bot.
     *
     * @return Post agents.
     */
    public List<PostAgent> getPostAgents() {
        return postAgent;
    }

    /**
     * Add post agents to this bot.
     *
     * @param agent All post agents to add.
     * @return Bot for chaining.
     */
    public Bot addPostAgent(PostAgent... agent) {
        postAgent.addAll(Arrays.asList(agent));
        return this;
    }

    /**
     * Get the invite builder of this bot.
     * Used to built bot invite urls.
     *
     * @return The bot invite builder.
     */
    public BotInviteBuilder getInviteBuilder() {
        return inviteBuilder;
    }
}
