package org.alienideology.jcord.handle.user;

import org.alienideology.jcord.handle.IDiscordObject;
import org.alienideology.jcord.handle.ISnowFlake;
import org.alienideology.jcord.handle.channel.ITextChannel;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.managers.IWebhookManager;
import org.alienideology.jcord.handle.modifiers.IWebhookModifier;
import org.alienideology.jcord.internal.rest.HttpPath;

/**
 * IWebhook - A low effort way to send messages to channels.
 *
 * @author AlienIdeology
 */
public interface IWebhook extends IDiscordObject, ISnowFlake {

    /**
     * The minimum length of a webhook's name.
     */
    int NAME_LENGTH_MIN = 2;

    /**
     * The maximum length of a webhook's name.
     */
    int NAME_LENGTH_MAX = 32;

    /**
     * Checks if an webhook's name is valid or not.
     *
     * Validations:
     * The length of the name must be between {@link IWebhook#NAME_LENGTH_MIN}
     * and {@link IWebhook#NAME_LENGTH_MAX}.
     *
     * @param name The name to be check with.
     * @return True if the name is valid.
     */
    static boolean isValidWebhookName(String name) {
        return name == null || name.isEmpty() ||
                name.length() >= NAME_LENGTH_MIN &&
                name.length() <= NAME_LENGTH_MAX;
    }

    /**
     * Get the webhook manager that manages this webhook.
     *
     * @return The manager.
     */
    IWebhookManager getManager();

    /**
     * Get the webhook modifier that modifies this webhook.
     *
     * @return The modifier.
     */
    IWebhookModifier getModifier();

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

    /**
     * Get the route that the webhook used to post requests in order to send message to a channel.
     * You may also use {@link IWebhookManager#execute(IWebhookManager.WebhookMessageBuilder)} to send message.
     *
     * @return The webhook {@code POST} route.
     */
    default String getPostUrl() {
        return HttpPath.DISCORD_API_URL + "/webhooks/" + getId() + "/" + getToken();
    }

    /**
     * Get the endpoint for executing this as a GitHub webhook.
     * Referring to <a href="https://support.discordapp.com/hc/en-us/articles/228383668-Intro-to-Webhooks">this introduction</a>
     * on how do execute a GitHub webhook.
     *
     * @return The GitHub webhook {@code POST} route.
     */
    default String getGitHubWebhookUrl() {
        return getPostUrl() + "/github";
    }

    /**
     * Get the endpoint for executing this as a Slack webhook.
     * Referring to <a href="https://api.slack.com/incoming-webhooks">this slack documentation</a>
     * on how do execute a Slack webhook.
     * Note that Discord does not support Slack's properties.
     *
     * @return The Slack webhook {@code POST} route.
     */
    default String getSlackWebhookUrl() {
        return getPostUrl() + "/slack";
    }

}
