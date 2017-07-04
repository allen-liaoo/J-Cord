package org.alienideology.jcord.handle.managers;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.builders.MessageBuilder;
import org.alienideology.jcord.handle.channel.ITextChannel;
import org.alienideology.jcord.handle.message.IEmbed;
import org.alienideology.jcord.handle.message.IMessage;
import org.alienideology.jcord.handle.user.IWebhook;
import org.alienideology.jcord.internal.object.message.Embed;
import org.alienideology.jcord.util.DataUtils;
import org.alienideology.jcord.util.Icon;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
     * @param name The name.
     */
    void modifyDefaultName(String name);

    /**
     * Modify the default avatar of this webhook.
     * The default avatar appears on every message, and it can be different than the webhook's user avatar.
     *
     * @param icon The avatar.
     * @throws IOException When decoding image.
     */
    void modifyDefaultAvatar(Icon icon) throws IOException;

    /**
     * Execute the webhook by sending a message to the channel.
     *
     * @param webhookMB The webhook message builder, used to send messages.
     * @return The message send.
     */
    IMessage execute(WebhookMessageBuilder webhookMB);

    /**
     * A simple message builder for webhooks.
     * Main difference to the {@link MessageBuilder} is that webhooks' messages may have temporary overridden names and avatars.
     */
    class WebhookMessageBuilder {

        private JSONObject json = new JSONObject();

        public WebhookMessageBuilder() {
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
         * @param image The temporary avatar.
         * @return WebhookMessageBuilder for chaining.
         */
        public WebhookMessageBuilder overrideAvatar(BufferedImage image) throws IOException {
            String encoding = DataUtils.encodeIcon(image);
            json.put("avatar_url", encoding);
            return this;
        }

        /**
         * Override the default avatar while sending this message.
         * This is temporary, it only overrides the message sent.
         *
         * @param path The temporary avatar's file path.
         * @return WebhookMessageBuilder for chaining.
         */
        public WebhookMessageBuilder overrideAvatar(String path) throws IOException {
            overrideAvatar(ImageIO.read(new File(path)));
            return this;
        }

        public JSONObject getJson() {
            return json;
        }
    }
}
