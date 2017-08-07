package org.alienideology.jcord.internal.object.managers;

import org.alienideology.jcord.handle.client.app.IApplication;
import org.alienideology.jcord.handle.managers.IApplicationManager;
import org.alienideology.jcord.internal.exception.HttpErrorException;
import org.alienideology.jcord.internal.object.client.ClientObject;
import org.alienideology.jcord.internal.rest.HttpPath;
import org.alienideology.jcord.internal.rest.Requester;

/**
 * @author AlienIdeology
 */
public final class ApplicationManager extends ClientObject implements IApplicationManager {

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
