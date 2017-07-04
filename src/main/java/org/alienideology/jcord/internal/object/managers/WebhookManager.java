package org.alienideology.jcord.internal.object.managers;

import org.alienideology.jcord.handle.managers.IWebhookManager;
import org.alienideology.jcord.handle.message.IMessage;
import org.alienideology.jcord.handle.user.IWebhook;
import org.alienideology.jcord.internal.gateway.HttpPath;
import org.alienideology.jcord.internal.gateway.Requester;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.ObjectBuilder;
import org.alienideology.jcord.internal.object.channel.MessageChannel;
import org.alienideology.jcord.internal.object.message.Message;
import org.alienideology.jcord.internal.object.user.Webhook;
import org.alienideology.jcord.util.Icon;
import org.json.JSONObject;

import java.io.IOException;

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
        modify(new JSONObject().put("name", name));
    }

    @Override
    public void modifyDefaultAvatar(Icon icon) throws IOException {
        modify(new JSONObject().put("avatar", icon.getData()));
    }

    private void modify(JSONObject json) {
        new Requester((IdentityImpl) getIdentity(), HttpPath.Webhook.MODIFY_WEBHOOK)
                .request(webhook.getId())
                .updateRequestWithBody(request -> request.body(json))
                .performRequest();
    }

    @Override
    public IMessage execute(WebhookMessageBuilder webhookMB) {
        return exe(webhookMB.getJson());
    }

    private IMessage exe(JSONObject json) {
        JSONObject msg = new Requester((IdentityImpl) getIdentity(), HttpPath.Webhook.EXECUTE_WEBHOOK)
                .request(webhook.getId(), webhook.getToken())
                .updateRequestWithBody(http -> http.body(json)).getAsJSONObject();
        Message message = new ObjectBuilder((IdentityImpl) getIdentity()).buildMessage(msg);
        ((MessageChannel) message.getChannel()).setLatestMessage(message);
        return message;
    }

}
