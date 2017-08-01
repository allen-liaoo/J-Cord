package org.alienideology.jcord.handle.bot;

import org.alienideology.jcord.bot.BotInviteBuilder;
import org.alienideology.jcord.handle.IDiscordObject;

/**
 * IBot - The core of a Discord IBot, integrated with bot utilities.
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
     * @return The key.
     */
    String getBotId();

    /**
     * Get the invite builder of this bot.
     * Used to built bot invite urls.
     *
     * @return The bot invite builder.
     */
    BotInviteBuilder getInviteBuilder();

}
