package org.alienideology.jcord.event;

import org.alienideology.jcord.event.gateway.GatewayEvent;
import org.alienideology.jcord.event.gateway.ReadyEvent;
import org.alienideology.jcord.event.gateway.ResumedEvent;
import org.alienideology.jcord.event.guild.*;
import org.alienideology.jcord.event.guild.member.*;
import org.alienideology.jcord.event.guild.role.GuildRoleCreateEvent;
import org.alienideology.jcord.event.guild.update.*;
import org.alienideology.jcord.event.message.MessageCreateEvent;
import org.alienideology.jcord.event.message.MessageDeleteEvent;
import org.alienideology.jcord.event.message.MessageEvent;
import org.alienideology.jcord.event.message.MessageUpdateEvent;
import org.alienideology.jcord.event.message.dm.PrivateMessageCreateEvent;
import org.alienideology.jcord.event.message.dm.PrivateMessageDeleteEvent;
import org.alienideology.jcord.event.message.dm.PrivateMessageUpdateEvent;
import org.alienideology.jcord.event.message.guild.GuildMessageCreateEvent;
import org.alienideology.jcord.event.message.guild.GuildMessageDeleteEvent;
import org.alienideology.jcord.event.message.guild.GuildMessageUpdateEvent;
import org.alienideology.jcord.internal.exception.ErrorResponseException;

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
        onGatewayEvent(event);
        if (event instanceof ReadyEvent) {
            onReady((ReadyEvent) event);
        } else if (event instanceof ResumedEvent) {
            onResume((ResumedEvent) event);
        }
    }

    public void onGatewayEvent (GatewayEvent event) {}

    public void onReady (ReadyEvent event) {}

    public void onResume (ResumedEvent event) {}

    /**
     * Guild Events
     */
    private void dispatchGuildEvent(GuildEvent event) {
        onGuildEvent(event);
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
        } else if (event instanceof GuildMemberEvent) {
            onGuildMemberEvent((GuildMemberEvent) event);
            if (event instanceof GuildMemberJoinEvent) {
                onGuildMemberJoin((GuildMemberJoinEvent) event);
            } else if (event instanceof GuildMemberLeaveEvent) {
                onGuildMemberLeave((GuildMemberLeaveEvent) event);
            }else if (event instanceof GuildMemberBanEvent) {
                onGuildBan((GuildMemberBanEvent) event);
            }
        } else if (event instanceof GuildUnbanEvent) {
            onGuildUnban((GuildUnbanEvent) event);
        } else if (event instanceof GuildRoleCreateEvent) {
            onGuildRoleCreate((GuildRoleCreateEvent) event);
        }
    }
    /*
        General Guild Events
     */
    public void onGuildEvent (GuildEvent event) {}

    public void onGuildCreate (GuildCreateEvent event) {}

    public void onGuildUpdate (GuildUpdateEvent event) {}

    public void onGuildOwnerUpdate (GuildOwnerUpdateEvent event) {}

    public void onGuildRegionUpdate (GuildRegionUpdateEvent event) {}

    public void onGuildIconUpdate (GuildIconUpdateEvent event) {}

    public void onGuildSplashUpdate (GuildSplashUpdateEvent event) {}

    public void onGuildAFKTimeoutUpdate (GuildAFKTimeoutUpdateEvent event) {}

    public void onGuildVerificationUpdate (GuildVerificationUpdateEvent event) {}

    public void onGuildNotificationUpdate (GuildNotificationUpdateEvent event) {}

    public void onGuildMFAUpdate (GuildMFAUpdateEvent event) {}

    public void onGuildDelete (GuildDeleteEvent event) {}

    public void onGuildUnavailable (GuildUnavailableEvent event) {}

    /*
        General Guild Member Events
     */
    public void onGuildMemberEvent(GuildMemberEvent event) {}

    public void onGuildMemberJoin (GuildMemberJoinEvent event) {}

    public void onGuildMemberLeave (GuildMemberLeaveEvent event) {}

    public void onGuildBan (GuildMemberBanEvent event) {}

    public void onGuildUnban (GuildUnbanEvent event) {}

    public void onGuildRoleCreate (GuildRoleCreateEvent event) {}

    /**
     * Message Events
     */
    private void dispatchMessageEvent(MessageEvent event) {
        onMessageEvent(event);
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

    public void onMessageEvent (MessageEvent event) {}

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
