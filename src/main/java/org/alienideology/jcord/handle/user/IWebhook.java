package org.alienideology.jcord.handle.user;

import org.alienideology.jcord.handle.IDiscordObject;
import org.alienideology.jcord.handle.ISnowFlake;
import org.alienideology.jcord.handle.channel.ITextChannel;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.managers.IWebhookManager;

/**
 * IWebhook - A low effort way to send messages to channels.
 *
 * @author AlienIdeology
 */
// TODO: IGuild#getWebhooks, ITextChannel#getWebhooks, Webhook#delete
public interface IWebhook extends IDiscordObject, ISnowFlake {

    /**
     * Get the webhook manager that manages this webhook.
     *
     * @return The manager.
     */
    IWebhookManager getWebhookManager();

    /**
     * Get the guild this webhook belongs to.
     *
     * @return The guild.
     */
    IGuild getGuild();

    /**
     * Get the text channel this webhook belongs to and can send message in.
     *
     * @return The channel.
     */
    ITextChannel getChannel();

    /**
     * Get the owner of this webhook.
     *
     * @return The owner.
     */
    IUser getOwner();

    /**
     * Get the webhook's default user.
     * Note that the user's discriminator is always {@code 0000}.
     *
     * @return The default user.
     */
    IUser getUser();

    /**
     * Get the default name of this webhook, which appears when sending message.
     *
     * @return The default name.
     */
    String getDefaultName();

    /**
     * Get the default avatar url of this webhook, which appears when sending message.
     *
     * @return The default avatar url.
     */
    String getDefaultAvatar();

    /**
     * Get the token of this webhook, used to execute or modify webhooks.
     * @see org.alienideology.jcord.handle.managers.IWebhookManager
     *
     * @return The token.
     */
    String getToken();

}
