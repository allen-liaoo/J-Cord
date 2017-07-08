package org.alienideology.jcord.bot;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.user.IUser;

import java.util.List;

/**
 * Application - Information about a bot's oauth application.
 *
 * @author AlienIdeology
 */
public class Application {

    private final Identity identity;

    private final String id;
    private final String name;
    private final String icon;
    private final String description;

    private final IUser owner;
    private List<String> rpcOrigins;

    private final boolean isPublicBot;
    private final boolean requireCodeGrant;

    public Application(Identity identity, String id, String name, String icon, String description, IUser owner, List<String> rpcOrigins, boolean isPublicBot, boolean requireCodeGrant) {
        this.identity = identity;
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.description = description;
        this.owner = owner;
        this.rpcOrigins = rpcOrigins;
        this.isPublicBot = isPublicBot;
        this.requireCodeGrant = requireCodeGrant;
    }

    /**
     * Get the identity this application belongs to.
     *
     * @return The identity.
     */
    public Identity getIdentity() {
        return identity;
    }

    /**
     * Get the ID of this application.
     *
     * @return The id.
     */
    public String getId() {
        return id;
    }

    /**
     * Get the registered name of this application.
     *
     * @return The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the icon of this application.
     *
     * @return The icon.
     */
    public String getIcon() {
        return icon;
    }

    /**
     * Get the description of this application.
     *
     * @return The description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the owner of this application.
     *
     * @return The owner.
     */
    public IUser getOwner() {
        return owner;
    }

    /**
     * Get the rpc origin urls. Return an empty list of rpc is disabled.
     *
     * @return The rpc urls.
     */
    public List<String> getRpcOrigins() {
        return rpcOrigins;
    }

    /**
     * Check if the bot can be invited (public) to other guilds.
     *
     * @return True of the bot is a public bot.
     */
    public boolean isPublicBot() {
        return isPublicBot;
    }

    /**
     * Check if the bot requires code grant.
     * If {@code requireCodeGrant} is true,
     * then the bot will only join upon completion of the full oauth2 code grant flow.
     *
     * @return True of the bot requires code grant.
     */
    public boolean requireCodeGrant() {
        return requireCodeGrant;
    }

}
