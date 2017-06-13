package org.alienideology.jcord.event;

import org.alienideology.jcord.event.gateway.*;
import org.alienideology.jcord.event.guild.*;
import org.alienideology.jcord.event.guild.role.GuildRoleCreateEvent;
import org.alienideology.jcord.event.guild.update.*;
import org.alienideology.jcord.event.message.*;
import org.alienideology.jcord.event.message.dm.*;
import org.alienideology.jcord.event.message.guild.*;
import org.alienideology.jcord.exception.ErrorResponseException;

/**
 * DispatcherAdaptor - Event listener used to listen to events and perform actions
 * @author AlienIdeology
 */
@SuppressWarnings("WeakerAccess")
public class DispatcherAdaptor {

    /*
        -------------------
            Fire Events
        -------------------
     */
    final void dispatchEvent(Event event) {
        onEvent(event);
        if (event instanceof ExceptionEvent) {
            dispatchException((ExceptionEvent) event);
        } else if (event instanceof GatewayEvent) {
            dispatchGatewayEvent((GatewayEvent) event);
        } else if (event instanceof GuildEvent) {
            dispatchGuildEvent((GuildEvent) event);
        } else if (event instanceof MessageEvent) {
            dispatchMessageEvent((MessageEvent) event);
        }
    }

    public void onEvent (Event event) {}

    /**
     * Gateway Events
     */
    private void dispatchGatewayEvent(GatewayEvent event) {
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
    private void dispatchGuildEvent(GuildEvent event) {
        if (event instanceof GuildCreateEvent) {
            onGuildCreate((GuildCreateEvent) event);
        } else if (event instanceof GuildUpdateEvent) {
            onGuildUpdate((GuildUpdateEvent) event);
            if (event instanceof GuildOwnerUpdateEvent) {
                onGuildOwnerUpdate((GuildOwnerUpdateEvent) event);
            } else if (event instanceof GuildRegionUpdateEvent) {
                onGuildRegionUpdate((GuildRegionUpdateEvent) event);
            } else if (event instanceof GuildIconUpdateEvent) {
                onGuildIconUpdate((GuildIconUpdateEvent) event);
            } else if (event instanceof GuildSplashUpdateEvent) {
                onGuildSplashUpdate((GuildSplashUpdateEvent) event);
            } else if (event instanceof GuildAFKTimeoutUpdateEvent) {
                onGuildAFKTimeoutUpdate((GuildAFKTimeoutUpdateEvent) event);
            } else if (event instanceof GuildVerificationUpdateEvent) {
                onGuildVerificationUpdate((GuildVerificationUpdateEvent) event);
            } else if (event instanceof GuildNotificationUpdateEvent) {
                onGuildNotificationUpdate((GuildNotificationUpdateEvent) event);
            } else if (event instanceof GuildMFAUpdateEvent) {
                onGuildMFAUpdate((GuildMFAUpdateEvent) event);
            }
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

    public void onGuildUpdate (GuildUpdateEvent event) {}

    public void onGuildOwnerUpdate (GuildOwnerUpdateEvent event) {}

    public void onGuildRegionUpdate (GuildRegionUpdateEvent event) {}

    public void onGuildIconUpdate (GuildIconUpdateEvent event) {}

    public void onGuildSplashUpdate (GuildSplashUpdateEvent event) {}

    public void onGuildAFKTimeoutUpdate (GuildAFKTimeoutUpdateEvent event) {}

    public void onGuildVerificationUpdate (GuildVerificationUpdateEvent event) {}

    public void onGuildNotificationUpdate (GuildNotificationUpdateEvent event) {}

    public void onGuildMFAUpdate (GuildMFAUpdateEvent event) {}

    public void onGuildUnavailable (GuildUnavailableEvent event) {}

    public void onGuildRoleCreate (GuildRoleCreateEvent event) {}

    /**
     * Message Events
     */
    private void dispatchMessageEvent(MessageEvent event) {
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
    private void dispatchException (ExceptionEvent event) {
        onException(event);
        if (event.getException() instanceof ErrorResponseException) {
            onErrorResponseException((ErrorResponseException) event.getException());
        }
    }

    public void onException (ExceptionEvent event) {}

    public void onErrorResponseException (ErrorResponseException exception) {}

}
