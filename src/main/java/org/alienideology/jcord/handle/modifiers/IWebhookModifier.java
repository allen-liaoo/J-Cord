package org.alienideology.jcord.handle.modifiers;

import org.alienideology.jcord.handle.Icon;
import org.alienideology.jcord.handle.audit.AuditAction;
import org.alienideology.jcord.handle.modifiers.attr.IconAttribute;
import org.alienideology.jcord.handle.user.IWebhook;
import org.alienideology.jcord.internal.object.user.Webhook;

/**
 * IWebhookModifier - The modifier that modifies a webhook.
 *
 * @author AlienIdeology
 */
public interface IWebhookModifier extends IModifier<AuditAction<Void>> {

    /**
     * Get the webhook this modifier belongs to.
     *
     * @return The webhook.
     */
    Webhook getWebhook();

    /**
     * Modify the webhook's default name.
     *
     * The default name appears on every message, and it can be different than the webhook's username.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity itself does not have {@code Manager Webhooks} permission.
     * @exception  IllegalArgumentException
     *          If the name is not valid. See {@link IWebhook#isValidWebhookName(String)}
     *
     * @param name The name.
     * @return IWebhookModifier for chaining.
     */
    IWebhookModifier defaultName(String name);

    /**
     * Modify the webhook's
     * The default avatar appears on every message, and it can be different than the webhook's user avatar.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity itself does not have {@code Manager Webhooks} permission.
     *
     * @param avatar The image file.
     * @return IWebhookModifier for chaining.
     */
    IWebhookModifier defaultAvatar(Icon avatar);

    /**
     * Get the default name attribute, used to modify the webhook's default name.
     *
     * @return The default name attribute.
     */
    Attribute<IWebhookModifier, String> getDefaultNameAttr();

    /**
     * Get the default avatar attribute, used to modify the webhook's default avatar.
     *
     * @return The default avatar attribute.
     */
    IconAttribute<IWebhookModifier> getDefaultAvatarAttr();

}
