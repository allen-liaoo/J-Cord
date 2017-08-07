package org.alienideology.jcord.internal.object.managers;

import org.alienideology.jcord.handle.audit.AuditAction;
import org.alienideology.jcord.handle.managers.IWebhookManager;
import org.alienideology.jcord.handle.permission.Permission;
import org.alienideology.jcord.handle.user.IWebhook;
import org.alienideology.jcord.internal.exception.PermissionException;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.user.Webhook;
import org.alienideology.jcord.internal.rest.HttpPath;
import org.alienideology.jcord.internal.rest.Requester;

/**
 * @author AlienIdeology
 */
public final class WebhookManager implements IWebhookManager {

    private Webhook webhook;

    public WebhookManager(Webhook webhook) {
        this.webhook = webhook;
    }

    @Override
    public IWebhook getWebhook() {
        return webhook;
    }

    @Override
    public AuditAction<Void> execute(WebhookMessageBuilder webhookMB) {
        return new AuditAction<Void>((IdentityImpl) getIdentity(), HttpPath.Webhook.EXECUTE_WEBHOOK, webhook.getId(), webhook.getToken()) {
            @Override
            protected Void request(Requester requester) {
                requester.updateRequestWithBody(http -> http.body(webhookMB.getJson())).performRequest();
                return null;
            }
        };
    }

    @Override
    public AuditAction<Void> delete() {
        if (!getChannel().hasPermission(getGuild().getSelfMember(), Permission.ADMINISTRATOR, Permission.MANAGE_WEBHOOKS)) {
            throw new PermissionException(Permission.ADMINISTRATOR, Permission.MANAGE_WEBHOOKS);
        }

        return new AuditAction<Void>((IdentityImpl) getIdentity(), HttpPath.Webhook.DELETE_WEBHOOK, webhook.getId()) {
            @Override
            protected Void request(Requester requester) {
                requester.performRequest();
                return null;
            }
        };
    }
}
