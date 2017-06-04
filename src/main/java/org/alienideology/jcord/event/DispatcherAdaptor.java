package org.alienideology.jcord.event;

import org.alienideology.jcord.event.gateway.GatewayEvent;
import org.alienideology.jcord.event.gateway.ReadyEvent;
import org.alienideology.jcord.event.gateway.ResumedEvent;
import org.alienideology.jcord.event.guild.GuildEvent;
import org.alienideology.jcord.event.guild.GuildCreateEvent;
import org.alienideology.jcord.event.guild.GuildRoleCreateEvent;
import org.alienideology.jcord.exception.ErrorResponseException;

/**
 * Event Listener used to listen to events and perform actions
 * @author AlienIdeology
 */
public class DispatcherAdaptor {

    /*
        Fire Events
     */
    public final void onEvent (Event event) {
        if (event instanceof GatewayEvent) {
            onGatewayEvent((GatewayEvent) event);
        } else if (event instanceof GuildEvent) {
            onGuildEvent((GuildEvent) event);
        }
    }

    private void onGatewayEvent (GatewayEvent event) {
        if (event instanceof ReadyEvent) {
            onReady((ReadyEvent) event);
        } else if (event instanceof ResumedEvent) {
            onResume((ResumedEvent) event);
        }
    }

    public void onReady (ReadyEvent event) {}

    public void onResume (ResumedEvent event) {}

    private void onGuildEvent (GuildEvent event) {
        if (event instanceof GuildCreateEvent) {
            onGuildCreate((GuildCreateEvent) event);
        } else if (event instanceof GuildRoleCreateEvent) {
            onGuildRoleCreate((GuildRoleCreateEvent) event);
        }
    }

    public void onGuildCreate (GuildCreateEvent event) {}

    public void onGuildRoleCreate (GuildRoleCreateEvent event) {}

    /*
        Fire Exceptions
     */
    public void onException (Exception exception) {
        exception.printStackTrace();

        if (exception instanceof ErrorResponseException) {
            onErrorResponseException((ErrorResponseException) exception);
        }
    }

    public void onErrorResponseException (ErrorResponseException exception) {}

}
