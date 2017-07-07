package org.alienideology.jcord.internal.object.managers;

import org.alienideology.jcord.handle.Icon;
import org.alienideology.jcord.handle.managers.IWebhookManager;
import org.alienideology.jcord.handle.permission.Permission;
import org.alienideology.jcord.handle.user.IWebhook;
import org.alienideology.jcord.internal.exception.PermissionException;
import org.alienideology.jcord.internal.gateway.HttpPath;
import org.alienideology.jcord.internal.gateway.Requester;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.user.Webhook;
import org.json.JSONObject;

/**
 * @author AlienIdeology
 */
public class WebhookManager implements IWebhookManager {

    private Webhook webhook;

    public WebhookManager(Webhook webhook) {
        this.webhook = webhook;
    }

    @Override
    public IWebhook getWebhook() {
        return webhook;
    }

    @Override
    public void modifyDefaultName(String name) {
        if (name == null || name.isEmpty()) return;
        if (!IWebhook.isValidWebhookName(name)) {
            throw new IllegalArgumentException("The name is not valid!");
        }
        modify(new JSONObject().put("name", name));
    }

    @Override
    public void modifyDefaultAvatar(Icon icon) {
        modify(new JSONObject().put("avatar", icon.getData()));
    }

    private void modify(JSONObject json) {
        if (!getChannel().hasPermission(getGuild().getSelfMember(), Permission.ADMINISTRATOR, Permission.MANAGE_WEBHOOKS)) {
            throw new PermissionException(Permission.ADMINISTRATOR, Permission.MANAGE_WEBHOOKS);
        }

        new Requester((IdentityImpl) getIdentity(), HttpPath.Webhook.MODIFY_WEBHOOK)
                .request(webhook.getId())
                .updateRequestWithBody(request -> request.body(json))
                .performRequest();
    }

    @Override
    public void execute(WebhookMessageBuilder webhookMB) {
        new Requester((IdentityImpl) getIdentity(), HttpPath.Webhook.EXECUTE_WEBHOOK)
                .request(webhook.getId(), webhook.getToken())
                .updateRequestWithBody(http -> http.body(webhookMB.getJson())).performRequest();
    }

    @Override
    public void delete() {
        if (!getChannel().hasPermission(getGuild().getSelfMember(), Permission.ADMINISTRATOR, Permission.MANAGE_WEBHOOKS)) {
            throw new PermissionException(Permission.ADMINISTRATOR, Permission.MANAGE_WEBHOOKS);
        }

        new Requester((IdentityImpl) getIdentity(), HttpPath.Webhook.DELETE_WEBHOOK).request(webhook.getId())
                .performRequest();
    }
}
