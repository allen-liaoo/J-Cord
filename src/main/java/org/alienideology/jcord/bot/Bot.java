package org.alienideology.jcord.bot;

import org.alienideology.jcord.Identity;

/**
 * Bot - The core of a Discord Bot
 * @author AlienIdeology
 */
public class Bot {

    private Identity identity;
    private final String id;

    private final PostAgent postAgent;
    private final BotInviteBuilder inviteBuilder;

    public Bot(Identity identity) {
        this.identity = identity;
        this.id = identity.getSelf().getId();
        this.postAgent = new PostAgent(identity);
        this.inviteBuilder = new BotInviteBuilder(identity.getSelf().getId());
    }

    public Identity getIdentity() {
        return identity;
    }

    public String getBotId() {
        return id;
    }

    public PostAgent getPostAgent() {
        return postAgent;
    }

    public BotInviteBuilder getInviteBuilder() {
        return inviteBuilder;
    }
}
