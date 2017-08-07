package org.alienideology.jcord.handle.managers;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.Icon;
import org.alienideology.jcord.handle.audit.AuditAction;
import org.alienideology.jcord.handle.builders.Buildable;
import org.alienideology.jcord.handle.builders.MessageBuilder;
import org.alienideology.jcord.handle.channel.ITextChannel;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.message.IEmbed;
import org.alienideology.jcord.handle.message.IMessage;
import org.alienideology.jcord.handle.user.IWebhook;
import org.alienideology.jcord.internal.object.message.Embed;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * IWebhookManager - A manager for modifying, executing and deleting webhooks.
 *
 * @author AlienIdeology
 */
public interface IWebhookManager {

    /**
     * Get the identity this webhook appears in.
     *
     * @return The identity.
     */
    default Identity getIdentity() {
        return getWebhook().getIdentity();
    }

    /**
     * Get the webhook this manager manages.
     *
     * @return The webhook.
     */
    IWebhook getWebhook();

    /**
     * Get the guild this webhook belongs to.
     *
     * @return The guild.
     */
    default IGuild getGuild() {
        return getWebhook().getGuild();
    }

    /**
     * Get the channel this webhook can send messages to.
     *
     * @return The channel.
     */
    default ITextChannel getChannel() {
        return getWebhook().getChannel();
    }

    /**
     * Modify the default name of this webhook.
     * The default name appears on every message, and it can be different than the webhook's username.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity itself does not have {@code Manager Webhooks} permission.
     * @exception  IllegalArgumentException
     *          If the name is not valid. See {@link IWebhook#isValidWebhookName(String)}
     *
     * @param name The name.
     * @return A {@link Void} {@link AuditAction}, used to attach audit log reason.
     */
    default AuditAction<Void> modifyDefaultName(String name) {
        return getWebhook().getModifier().defaultName(name).modify();
    }

    /**
     * Modify the default avatar of this webhook.
     * The default avatar appears on every message, and it can be different than the webhook's user avatar.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity itself does not have {@code Manager Webhooks} permission.
     *
     * @param icon The avatar.
     * @return A {@link Void} {@link AuditAction}, used to attach audit log reason.
     */
    default AuditAction<Void> modifyDefaultAvatar(Icon icon) {
        return getWebhook().getModifier().defaultAvatar(icon).modify();
    }

    /**
     * Execute the webhook by sending a message to the channel.
     * No exception will be thrown, since webhooks are granted with
     * {@link org.alienideology.jcord.handle.permission.Permission#ALL_TEXT_PERMS} in the channel.
     *
     * @param webhookMB The webhook message builder, used to send messages.
     * @return A {@link Void} {@link AuditAction}, used to attach audit log reason.
     */
    AuditAction<Void> execute(WebhookMessageBuilder webhookMB);

    /**
     * Delete this webhook.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity itself does not have {@code Manager Webhooks} permission.
     * @return A {@link Void} {@link AuditAction}, used to attach audit log reason.
     */
    AuditAction<Void> delete();

    /**
     * A simple message builder for webhooks.
     * Main difference to the {@link MessageBuilder} is that webhooks' messages may have temporary overridden names and avatars.
     */
    class WebhookMessageBuilder implements Buildable<WebhookMessageBuilder, WebhookMessageBuilder> {

        private JSONObject json = new JSONObject();

        public WebhookMessageBuilder() {
        }

        /**
         * Build a webhook message.
         * This is just a pace holder for the builder. Not using this method also works.
         *
         * @return The builder.
         */
        @Override
        public WebhookMessageBuilder build() {
            return this;
        }

        @Override
        public WebhookMessageBuilder clear() {
            json = new JSONObject();
            return this;
        }

        /**
         * Set the webhook message with string content.
         *
         * @param content The content.
         * @return WebhookMessageBuilder for chaining.
         */
        public WebhookMessageBuilder withContent(String content) {
            return withMessage(new MessageBuilder().setContent(content).build());
        }

        /**
         * Set the webhook's message.
         *
         * @param message The message, built by {@link MessageBuilder}.
         * @return WebhookMessageBuilder for chaining.
         */
        public WebhookMessageBuilder withMessage(IMessage message) {
            json.put("tts", message.isTTS());

            // Content and Embed may not be sent at the same time
            // Only one type if allowed
            if (message.getContent() != null) {
                json.put("content", message.getContent());
            } else if (!message.getEmbeds().isEmpty()) {
                JSONArray embeds = new JSONArray();
                for (IEmbed embed : message.getEmbeds()) {
                    embeds.put(((Embed) embed).toJson());
                }
                json.put("embeds", embeds);
            }

            return this;
        }

        /**
         * Set the webhook's message with one or multiple embeds.
         *
         * @param embeds The embeds.
         * @return WebhookMessageBuilder for chaining.
         */
        public WebhookMessageBuilder withEmbeds(IEmbed... embeds) {
            IMessage msg = new MessageBuilder().build();
            msg.getEmbeds().addAll(Arrays.asList(embeds));
            return withMessage(msg);
        }

        /**
         * Override the default name while sending this message.
         * This is temporary, it only overrides the message sent.
         *
         * @param name The temporary name.
         * @return WebhookMessageBuilder for chaining.
         */
        public WebhookMessageBuilder overrideName(String name) {
            json.put("username", name);
            return this;
        }

        /**
         * Override the default avatar while sending this message.
         * This is temporary, it only overrides the message sent.
         *
         * @param icon The temporary icon.
         * @return WebhookMessageBuilder for chaining.
         */
        public WebhookMessageBuilder overrideAvatar(Icon icon) {
            json.put("avatar_url", icon.getData());
            return this;
        }

        /**
         * The Json used to post http requests internally.
         *
         * @return The json.
         */
        public JSONObject getJson() {
            return json;
        }

    }
}
