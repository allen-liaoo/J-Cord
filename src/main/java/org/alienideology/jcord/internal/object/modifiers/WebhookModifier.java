package org.alienideology.jcord.internal.object.modifiers;

import org.alienideology.jcord.handle.Icon;
import org.alienideology.jcord.handle.audit.AuditAction;
import org.alienideology.jcord.handle.modifiers.Attribute;
import org.alienideology.jcord.handle.modifiers.IWebhookModifier;
import org.alienideology.jcord.handle.modifiers.attr.IconAttribute;
import org.alienideology.jcord.handle.permission.Permission;
import org.alienideology.jcord.handle.user.IWebhook;
import org.alienideology.jcord.internal.exception.PermissionException;
import org.alienideology.jcord.internal.object.user.Webhook;
import org.alienideology.jcord.internal.rest.HttpPath;
import org.alienideology.jcord.internal.rest.Requester;

/**
 * @author AlienIdeology
 */
public final class WebhookModifier extends Modifier<AuditAction<Void>> implements IWebhookModifier {

    private final Webhook webhook;

    private Attribute<IWebhookModifier, String> defaultNameAttr;
    private IconAttribute<IWebhookModifier> defaultAvatarAttr;

    public WebhookModifier(Webhook webhook) {
        super(webhook.getIdentity());
        this.webhook = webhook;
        setupAttributes();
    }

    @Override
    public Webhook getWebhook() {
        return webhook;
    }

    @Override
    public IWebhookModifier defaultName(String name) {
        return defaultNameAttr.setValue(name);
    }

    @Override
    public IWebhookModifier defaultAvatar(Icon avatar) {
        return defaultAvatarAttr.setValue(avatar);
    }

    @Override
    public Attribute<IWebhookModifier, String> getDefaultNameAttr() {
        return defaultNameAttr;
    }

    @Override
    public IconAttribute<IWebhookModifier> getDefaultAvatarAttr() {
        return defaultAvatarAttr;
    }

    @Override
    public AuditAction<Void> modify() {
        if (!webhook.getChannel().hasPermission(webhook.getGuild().getSelfMember(), Permission.ADMINISTRATOR, Permission.MANAGE_WEBHOOKS)) {
            throw new PermissionException(Permission.ADMINISTRATOR, Permission.MANAGE_WEBHOOKS);
        }

        return new AuditAction<Void>(getIdentity(), HttpPath.Webhook.MODIFY_WEBHOOK, webhook.getId()) {
            @Override
            protected Void request(Requester requester) {
                requester.updateRequestWithBody(request -> request.body(getUpdatableJson())).performRequest();
                reset();
                return null;
            }
        };
    }

    @Override
    protected void setupAttributes() {
        defaultNameAttr = new Attribute<IWebhookModifier, String>("name", this, webhook::getDefaultName) {
            @Override
            public void checkValue(String value) throws IllegalArgumentException {
                if (!IWebhook.isValidWebhookName(value)) {
                    throw new IllegalArgumentException("The name is not valid!");
                }
            }

            @Override
            public IWebhookModifier setValue(String newValue) {
                if (newValue == null || newValue.isEmpty()) return getModifier();
                return super.setValue(newValue);
            }
        };
        defaultAvatarAttr = new IconAttribute<>("avatar", this);
        addAttributes(defaultNameAttr, defaultAvatarAttr);
    }
}
