package org.alienideology.jcord.bot;

import org.alienideology.jcord.Identity;

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
    private final String id;

    private final List<PostAgent> postAgent;
    private final BotInviteBuilder inviteBuilder;

    public Bot(Identity identity) {
        this.identity = identity;
        this.id = identity.getSelf().getId();
        this.postAgent = new ArrayList<>();
        this.inviteBuilder = new BotInviteBuilder(identity.getSelf().getId());
    }

    public Identity getIdentity() {
        return identity;
    }

    public String getBotId() {
        return id;
    }

    public List<PostAgent> getPostAgents() {
        return postAgent;
    }

    public Bot addPostAgent(PostAgent... agent) {
        postAgent.addAll(Arrays.asList(agent));
        return this;
    }

    public BotInviteBuilder getInviteBuilder() {
        return inviteBuilder;
    }
}
