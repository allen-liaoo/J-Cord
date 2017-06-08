package org.alienideology.jcord.event;

import org.alienideology.jcord.event.gateway.GatewayEvent;
import org.alienideology.jcord.event.gateway.ReadyEvent;
import org.alienideology.jcord.event.gateway.ResumedEvent;
import org.alienideology.jcord.event.guild.*;
import org.alienideology.jcord.event.message.GuildMessageCreateEvent;
import org.alienideology.jcord.event.message.MessageCreateEvent;
import org.alienideology.jcord.event.message.MessageEvent;
import org.alienideology.jcord.event.message.PrivateMessageCreateEvent;
import org.alienideology.jcord.exception.ErrorResponseException;

/**
 * Event Listener used to listen to events and perform actions
 * @author AlienIdeology
 */
public class DispatcherAdaptor {

    /*
        -------------------
            Fire Events
        -------------------
     */
    public final void onEvent (Event event) {
        if (event instanceof GatewayEvent) {
            onGatewayEvent((GatewayEvent) event);
        } else if (event instanceof GuildEvent) {
            onGuildEvent((GuildEvent) event);
        } else if (event instanceof MessageEvent) {
            onMessageEvent((MessageEvent) event);
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
        } else if (event instanceof GuildDeleteEvent) {
            onGuildDelete((GuildDeleteEvent) event);
        } else if (event instanceof GuildUnavailableEvent) {
            onGuildUnavailable((GuildUnavailableEvent) event);
        } else if (event instanceof GuildRoleCreateEvent) {
            onGuildRoleCreate((GuildRoleCreateEvent) event);
        }
    }

    public void onGuildCreate (GuildCreateEvent event) {}

    public void onGuildDelete (GuildDeleteEvent event) {}

    public void onGuildUnavailable (GuildUnavailableEvent event) {}

    public void onGuildRoleCreate (GuildRoleCreateEvent event) {}

    private void onMessageEvent (MessageEvent event) {
        if (event instanceof MessageCreateEvent) {
            onMessageCreate((MessageCreateEvent) event);
            if (event instanceof GuildMessageCreateEvent) {
                onGuildMessageCreate((GuildMessageCreateEvent) event);
            } else if (event instanceof PrivateMessageCreateEvent) {
                onPrivateMessageCreate((PrivateMessageCreateEvent) event);
            }
        }
    }

    public void onMessageCreate (MessageCreateEvent event) {}

    public void onGuildMessageCreate (GuildMessageCreateEvent event) {}

    public void onPrivateMessageCreate (PrivateMessageCreateEvent event) {}

    /*
        -----------------------
            Fire Exceptions
        -----------------------
     */
    public void onException (Exception exception) {
        exception.printStackTrace();

        if (exception instanceof ErrorResponseException) {
            onErrorResponseException((ErrorResponseException) exception);
        }
    }

    public void onErrorResponseException (ErrorResponseException exception) {}

}
