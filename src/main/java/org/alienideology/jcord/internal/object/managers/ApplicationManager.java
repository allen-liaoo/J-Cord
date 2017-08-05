package org.alienideology.jcord.internal.object.managers;

import org.alienideology.jcord.handle.Icon;
import org.alienideology.jcord.handle.client.app.IApplication;
import org.alienideology.jcord.handle.managers.IApplicationManager;
import org.alienideology.jcord.internal.exception.HttpErrorException;
import org.alienideology.jcord.internal.gateway.HttpPath;
import org.alienideology.jcord.internal.gateway.Requester;
import org.alienideology.jcord.internal.object.client.ClientObject;
import org.json.JSONObject;

import java.util.List;

/**
 * @author AlienIdeology
 */
public class ApplicationManager extends ClientObject implements IApplicationManager {

    private IApplication application;

    public ApplicationManager(IApplication application) {
        super(application.getClient());
        this.application = application;
    }

    @Override
    public IApplication getApplication() {
        return application;
    }

    @Override
    public void modifyName(String name) {
        modifyApplication(new JSONObject().put("name", name));
    }

    @Override
    public void modifyIcon(Icon icon) {
        modifyApplication(new JSONObject()
                .put("icon", icon.getData())
                // For some reason, discord responds with 400 BAD REQUEST when the request without name
                .put("name", application.getName())
        );
    }

    @Override
    public void modifyDescription(String description) {
        modifyApplication(new JSONObject()
                .put("description", description)
                // For some reason, discord responds with 400 BAD REQUEST when the request without name
                .put("name", application.getName())
        );
    }

    @Override
    public void modifyRedirectUris(List<String> redirectUris) {
        modifyApplication(new JSONObject()
                .put("redirect_uris", redirectUris)
                // For some reason, discord responds with 400 BAD REQUEST when the request without name
                .put("name", application.getName())
        );
    }

    @Override
    public void modifyIsBotPublic(boolean isBotPublic) {
        modifyApplication(new JSONObject()
                .put("bot_public", isBotPublic)
                // For some reason, discord responds with 400 BAD REQUEST when the request without name
                .put("name", application.getName())
        );
    }

    @Override
    public void modifyRequireCodeGrant(boolean requireCodeGrant) {
        modifyApplication(new JSONObject()
                .put("bot_require_code_grant", requireCodeGrant)
                // For some reason, discord responds with 400 BAD REQUEST when the request without name
                .put("name", application.getName()));
    }

    private void modifyApplication(JSONObject json) {
        new Requester(getIdentity(), HttpPath.Application.MODIFY_APPLICATION)
                .request(application.getId())
                .updateRequestWithBody(request -> request.body(json))
                .performRequest();
    }

    @Override
    public void createBotUser() {
        new Requester(getIdentity(), HttpPath.Application.CREATE_BOT_USER)
                .request(application.getId())
                .performRequest();
    }

    @Override
    public void resetSecret() {
        new Requester(getIdentity(), HttpPath.Application.RESET_APPLICATION_SECRET)
                .request(application.getId())
                .performRequest();
    }

    @Override
    public void resetToken() {
        try {
            new Requester(getIdentity(), HttpPath.Application.RESET_BOT_TOKEN)
                    .request(application.getId())
                    .performRequest();
        } catch (HttpErrorException ex) {
            if (ex.isPermissionException()) {
                throw new IllegalArgumentException("Cannot reset the bot token when the application hasn't have a bot user!");
            } else {
                throw ex;
            }
        }
    }
}
