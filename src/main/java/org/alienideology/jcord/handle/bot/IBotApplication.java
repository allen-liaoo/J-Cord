package org.alienideology.jcord.handle.bot;

import org.alienideology.jcord.handle.IDiscordObject;
import org.alienideology.jcord.handle.ISnowFlake;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.gateway.HttpPath;

import java.util.List;

/**
 * BotApplication - Information about a bot's oauth app.
 * 
 * @author AlienIdeology
 */
public interface IBotApplication extends IDiscordObject, ISnowFlake {

    /**
     * Get the registered name of this app.
     *
     * @return The name.
     */
    String getName();

    /**
     * Get the icon hash.
     *
     * @return The icon.
     */
    String getIconHash();

    /**
     * Get the icon url of this app.
     *
     * @return The icon url.
     */
    default String getIconUrl() {
        return String.format(HttpPath.EndPoint.APPLICATION_ICON, getId(), getIconHash());
    }

    /**
     * Get the description of this app.
     *
     * @return The description.
     */
    String getDescription();

    /**
     * Get the owner of this app.
     *
     * @return The owner.
     */
    IUser getOwner();

    /**
     * Get the rpc origin urls. Return an empty list of rpc is disabled.
     *
     * @return The rpc urls.
     */
    List<String> getRpcOrigins();

    /**
     * Check if the bot can be invited(means public) to other guilds.
     *
     * @return True of the bot is a  bot.
     */
    boolean isPublicBot();

    /**
     * Check if the bot requires code grant.
     * If {@code requireCodeGrant} is true,
     * then the bot will only join upon completion of the full oauth2 code grant flow.
     *
     * @return True of the bot requires code grant.
     */
    boolean requireCodeGrant();
    
}
