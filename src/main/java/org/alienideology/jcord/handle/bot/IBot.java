package org.alienideology.jcord.handle.bot;

import org.alienideology.jcord.bot.BotInviteBuilder;
import org.alienideology.jcord.bot.PostAgent;
import org.alienideology.jcord.handle.IDiscordObject;
import org.alienideology.jcord.internal.object.bot.Bot;

import java.util.List;

/**
 * Bot - The core of a Discord Bot, integrated with bot utilities.
 * 
 * @author AlienIdeology
 */
public interface IBot extends IDiscordObject {

    /**
     * Get the application of this bot.
     *
     * @return The application.
     */
    IBotApplication getAsApplication();

    /**
     * Get the bot's ID.
     *
     * @return The id.
     */
    String getBotId();

    /**
     * Get all post agents for this bot.
     *
     * @return Post agents.
     */
    List<PostAgent> getPostAgents();

    /**
     * Add post agents to this bot.
     *
     * @param agent All post agents to add.
     * @return Bot for chaining.
     */
    Bot addPostAgent(PostAgent... agent);

    /**
     * Get the invite builder of this bot.
     * Used to built bot invite urls.
     *
     * @return The bot invite builder.
     */
    BotInviteBuilder getInviteBuilder();

}
