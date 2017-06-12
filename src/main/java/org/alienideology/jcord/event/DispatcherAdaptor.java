package org.alienideology.jcord.event;

import org.alienideology.jcord.event.gateway.*;
import org.alienideology.jcord.event.guild.*;
import org.alienideology.jcord.event.message.*;
import org.alienideology.jcord.event.message.dm.*;
import org.alienideology.jcord.event.message.guild.*;
import org.alienideology.jcord.exception.ErrorResponseException;

/**
 * Event Listener used to listen to events and perform actions
 * @author AlienIdeology
 */
@SuppressWarnings("WeakerAccess")
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

    /**
     * Gateway Events
     */
    private void onGatewayEvent (GatewayEvent event) {
        if (event instanceof ReadyEvent) {
            onReady((ReadyEvent) event);
        } else if (event instanceof ResumedEvent) {
            onResume((ResumedEvent) event);
        }
    }

    public void onReady (ReadyEvent event) {}

    public void onResume (ResumedEvent event) {}

    /**
     * Guild Events
     */
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

    /**
     * Message Events
     */
    private void onMessageEvent (MessageEvent event) {
        if (event instanceof MessageCreateEvent) {
            onMessageCreate((MessageCreateEvent) event);
            if (event instanceof GuildMessageCreateEvent) {
                onGuildMessageCreate((GuildMessageCreateEvent) event);
            } else if (event instanceof PrivateMessageCreateEvent) {
                onPrivateMessageCreate((PrivateMessageCreateEvent) event);
            }
        } else if (event instanceof MessageUpdateEvent) {
            onMessageUpdate((MessageUpdateEvent) event);
            if (event instanceof GuildMessageUpdateEvent) {
                onGuildMessageUpdate((GuildMessageUpdateEvent) event);
            } else if (event instanceof PrivateMessageUpdateEvent) {
                onPrivateMessageUpdate((PrivateMessageUpdateEvent) event);
            }
        } else if (event instanceof MessageDeleteEvent) {
            onMessageDelete((MessageDeleteEvent) event);
            if (event instanceof GuildMessageDeleteEvent) {
                onGuildMessageDelete((GuildMessageDeleteEvent) event);
            } else if (event instanceof PrivateMessageDeleteEvent) {
                onPrivateMessageDelete((PrivateMessageDeleteEvent) event);
            }
        }
    }

    public void onMessageCreate (MessageCreateEvent event) {}

    public void onGuildMessageCreate (GuildMessageCreateEvent event) {}

    public void onPrivateMessageCreate (PrivateMessageCreateEvent event) {}

    public void onMessageUpdate (MessageUpdateEvent event) {}

    public void onGuildMessageUpdate (GuildMessageUpdateEvent event) {}

    public void onPrivateMessageUpdate (PrivateMessageUpdateEvent event) {}

    public void onMessageDelete (MessageDeleteEvent event) {}

    public void onGuildMessageDelete (GuildMessageDeleteEvent event) {}

    public void onPrivateMessageDelete (PrivateMessageDeleteEvent event) {}

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
