package org.alienideology.jcord.event;

import org.alienideology.jcord.event.channel.ChannelCreateEvent;
import org.alienideology.jcord.event.channel.ChannelDeleteEvent;
import org.alienideology.jcord.event.channel.ChannelEvent;
import org.alienideology.jcord.event.channel.dm.IPrivateChannelEvent;
import org.alienideology.jcord.event.channel.dm.PrivateChannelCreateEvent;
import org.alienideology.jcord.event.channel.dm.PrivateChannelDeleteEvent;
import org.alienideology.jcord.event.channel.guild.GuildChannelCreateEvent;
import org.alienideology.jcord.event.channel.guild.GuildChannelDeleteEvent;
import org.alienideology.jcord.event.channel.guild.GuildChannelUpdateEvent;
import org.alienideology.jcord.event.channel.guild.IGuildChannelEvent;
import org.alienideology.jcord.event.channel.guild.text.ITextChannelEvent;
import org.alienideology.jcord.event.channel.guild.text.TextChannelCreateEvent;
import org.alienideology.jcord.event.channel.guild.text.TextChannelDeleteEvent;
import org.alienideology.jcord.event.channel.guild.text.TextChannelUpdateEvent;
import org.alienideology.jcord.event.channel.guild.text.update.TextChannelNameUpdateEvent;
import org.alienideology.jcord.event.channel.guild.text.update.TextChannelPermissionsUpdateEvent;
import org.alienideology.jcord.event.channel.guild.text.update.TextChannelPositionUpdateEvent;
import org.alienideology.jcord.event.channel.guild.text.update.TextChannelTopicUpdateEvent;
import org.alienideology.jcord.event.channel.guild.voice.IVoiceChannelEvent;
import org.alienideology.jcord.event.channel.guild.voice.VoiceChannelCreateEvent;
import org.alienideology.jcord.event.channel.guild.voice.VoiceChannelDeleteEvent;
import org.alienideology.jcord.event.channel.guild.voice.VoiceChannelUpdateEvent;
import org.alienideology.jcord.event.channel.guild.voice.update.*;
import org.alienideology.jcord.event.gateway.GatewayEvent;
import org.alienideology.jcord.event.gateway.ReadyEvent;
import org.alienideology.jcord.event.gateway.ResumedEvent;
import org.alienideology.jcord.event.guild.*;
import org.alienideology.jcord.event.guild.emoji.*;
import org.alienideology.jcord.event.guild.member.*;
import org.alienideology.jcord.event.guild.role.GuildRoleCreateEvent;
import org.alienideology.jcord.event.guild.role.GuildRoleDeleteEvent;
import org.alienideology.jcord.event.guild.role.GuildRoleEvent;
import org.alienideology.jcord.event.guild.role.GuildRoleUpdateEvent;
import org.alienideology.jcord.event.guild.role.update.*;
import org.alienideology.jcord.event.guild.update.*;
import org.alienideology.jcord.event.message.MessageCreateEvent;
import org.alienideology.jcord.event.message.MessageDeleteEvent;
import org.alienideology.jcord.event.message.MessageEvent;
import org.alienideology.jcord.event.message.MessageUpdateEvent;
import org.alienideology.jcord.event.message.dm.IPrivateMessageEvent;
import org.alienideology.jcord.event.message.dm.PrivateMessageCreateEvent;
import org.alienideology.jcord.event.message.dm.PrivateMessageDeleteEvent;
import org.alienideology.jcord.event.message.dm.PrivateMessageUpdateEvent;
import org.alienideology.jcord.event.message.guild.GuildMessageCreateEvent;
import org.alienideology.jcord.event.message.guild.GuildMessageDeleteEvent;
import org.alienideology.jcord.event.message.guild.GuildMessageUpdateEvent;
import org.alienideology.jcord.event.message.guild.IGuildMessageEvent;
import org.alienideology.jcord.internal.exception.ErrorResponseException;

/**
 * DispatcherAdaptor - Event listener used to listen to events and perform actions.
 *
 * @author AlienIdeology
 */
@SuppressWarnings({"WeakerAccess", "unused"})
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
        } else if (event instanceof ChannelEvent) {
            dispatchChannelEvent((ChannelEvent) event);
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
            } else if (event instanceof GuildMemberBanEvent) {
                onGuildMemberBan((GuildMemberBanEvent) event);
            } else if (event instanceof GuildMemberNicknameUpdateEvent) {
                onGuildMemberNicknameUpdate((GuildMemberNicknameUpdateEvent) event);
            } else if (event instanceof GuildMemberAddRoleEvent) {
                onGuildMemberAddRole((GuildMemberAddRoleEvent) event);
            } else if (event instanceof GuildMemberRemoveRoleEvent) {
                onGuildMemberRemoveRole((GuildMemberRemoveRoleEvent) event);
            }
        } else if (event instanceof GuildUnbanEvent) {
            onGuildUnban((GuildUnbanEvent) event);
        } else if (event instanceof GuildRoleEvent) {
            onGuildRoleEvent((GuildRoleEvent) event);
            if (event instanceof GuildRoleCreateEvent) {
                onGuildRoleCreate((GuildRoleCreateEvent) event);
            } else if (event instanceof GuildRoleUpdateEvent) {
                onGuildRoleUpdate((GuildRoleUpdateEvent) event);
                if (event instanceof GuildRoleNameUpdateEvent) {
                    onGuildRoleNameUpdate((GuildRoleNameUpdateEvent) event);
                } else if (event instanceof GuildRolePositionUpdateEvent) {
                    onGuildRolePositionUpdate((GuildRolePositionUpdateEvent) event);
                } else if (event instanceof GuildRoleColorUpdateEvent) {
                    onGuildRoleColorUpdate((GuildRoleColorUpdateEvent) event);
                } else if (event instanceof GuildRolePermissionsUpdateEvent) {
                    onGuildRolePermissionsUpdate((GuildRolePermissionsUpdateEvent) event);
                } else if (event instanceof GuildRoleSeparateListedUpdateEvent) {
                    onGuildRoleSeparateListedUpdate((GuildRoleSeparateListedUpdateEvent) event);
                } else if (event instanceof GuildRoleMentionUpdateEvent) {
                    onGuildRoleMentionUpdate((GuildRoleMentionUpdateEvent) event);
                }
            } else if (event instanceof GuildRoleDeleteEvent) {
                onGuildRoleDelete((GuildRoleDeleteEvent) event);
            }
        } else if (event instanceof GuildEmojiEvent) {
            onGuildEmojiEvent((GuildEmojiEvent) event);
            if (event instanceof GuildEmojiUploadEvent) {
                onGuildEmojiUpload((GuildEmojiUploadEvent) event);
            } else if (event instanceof GuildEmojiUpdateEvent) {
                onGuildEmojiUpdate((GuildEmojiUpdateEvent) event);
                if (event instanceof GuildEmojiNameUpdateEvent) {
                    onGuildEmojiNameUpdate((GuildEmojiNameUpdateEvent) event);
                } else if (event instanceof GuildEmojiRolesUpdateEvent) {
                    onGuildEmojiRolesUpdate((GuildEmojiRolesUpdateEvent) event);
                }
            } else if (event instanceof GuildEmojiDeleteEvent) {
                onGuildEmojiDelete((GuildEmojiDeleteEvent) event);
            }
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

    public void onGuildMemberBan(GuildMemberBanEvent event) {}

    public void onGuildUnban (GuildUnbanEvent event) {}

    public void onGuildMemberNicknameUpdate (GuildMemberNicknameUpdateEvent event) {}

    public void onGuildMemberAddRole (GuildMemberAddRoleEvent event) {}

    public void onGuildMemberRemoveRole (GuildMemberRemoveRoleEvent event) {}

    /*
        General Guild Role Events
     */
    public void onGuildRoleEvent (GuildRoleEvent event) {}

    public void onGuildRoleCreate (GuildRoleCreateEvent event) {}

    public void onGuildRoleUpdate (GuildRoleUpdateEvent event) {}

    public void onGuildRoleNameUpdate (GuildRoleNameUpdateEvent event) {}

    public void onGuildRolePositionUpdate (GuildRolePositionUpdateEvent event) {}

    public void onGuildRoleColorUpdate (GuildRoleColorUpdateEvent event) {}

    public void onGuildRolePermissionsUpdate (GuildRolePermissionsUpdateEvent event) {}

    public void onGuildRoleSeparateListedUpdate (GuildRoleSeparateListedUpdateEvent event) {}

    public void onGuildRoleMentionUpdate (GuildRoleMentionUpdateEvent event) {}

    public void onGuildRoleDelete (GuildRoleDeleteEvent event) {}

    /*
        General Guild Emoji Events
     */
    public void onGuildEmojiEvent (GuildEmojiEvent event) {}

    public void onGuildEmojiUpload (GuildEmojiUploadEvent event) {}

    public void onGuildEmojiUpdate (GuildEmojiUpdateEvent event) {}

    public void onGuildEmojiNameUpdate (GuildEmojiNameUpdateEvent event) {}

    public void onGuildEmojiRolesUpdate (GuildEmojiRolesUpdateEvent event) {}

    public void onGuildEmojiDelete (GuildEmojiDeleteEvent event) {}

    /**
     * Channel Events
     */
    private void dispatchChannelEvent(ChannelEvent event) {
        onChannelEvent(event);
        if (event instanceof IGuildChannelEvent) {
            onGuildChannelEvent((IGuildChannelEvent) event);
            if (event instanceof ITextChannelEvent) {
                onTextChannelEvent((ITextChannelEvent) event);
            } else if (event instanceof IVoiceChannelEvent) {
                onVoiceChannelEvent((IVoiceChannelEvent) event);
            }
        } else if (event instanceof IPrivateChannelEvent) {
            onPrivateChannelEvent((IPrivateChannelEvent) event);
        }

        if (event instanceof ChannelCreateEvent) {
            onChannelCreate((ChannelCreateEvent) event);
            if (event instanceof GuildChannelCreateEvent) {
                onGuildChannelCreate((GuildChannelCreateEvent) event);
                if (event instanceof TextChannelCreateEvent) {
                    onTextChannelCreate((TextChannelCreateEvent) event);
                } else if (event instanceof VoiceChannelCreateEvent) {
                    onVoiceChannelCreate((VoiceChannelCreateEvent) event);
                }
            } else if (event instanceof PrivateChannelCreateEvent) {
                onPrivateChannelCreate((PrivateChannelCreateEvent) event);
            }
        } else if (event instanceof GuildChannelUpdateEvent) {
            if (event instanceof TextChannelUpdateEvent) {
                onTextChannelUpdate((TextChannelUpdateEvent) event);
                if (event instanceof TextChannelNameUpdateEvent) {
                    onTextChannelNameUpdate((TextChannelNameUpdateEvent) event);
                } else if (event instanceof TextChannelPositionUpdateEvent) {
                    onTextChannelPositionUpdate((TextChannelPositionUpdateEvent) event);
                } else if (event instanceof TextChannelPermissionsUpdateEvent) {
                    onTextChannelPermissionsUpdate((TextChannelPermissionsUpdateEvent) event);
                } else if (event instanceof TextChannelTopicUpdateEvent) {
                    onTextChannelTopicUpdate((TextChannelTopicUpdateEvent) event);
                }
            } else if (event instanceof VoiceChannelUpdateEvent) {
                onVoiceChannelUpdate((VoiceChannelUpdateEvent) event);
                if (event instanceof VoiceChannelNameUpdateEvent) {
                    onVoiceChannelNameUpdate((VoiceChannelNameUpdateEvent) event);
                } else if (event instanceof VoiceChannelPositionUpdateEvent) {
                    onVoiceChannelPositionUpdate((VoiceChannelPositionUpdateEvent) event);
                } else if (event instanceof VoiceChannelPermissionsUpdateEvent) {
                    onVoiceChannelPermissionsUpdate((VoiceChannelPermissionsUpdateEvent) event);
                } else if (event instanceof VoiceChannelBitrateUpdateEvent) {
                    onVoiceChannelBitrateUpdate((VoiceChannelBitrateUpdateEvent) event);
                } else if (event instanceof VoiceChannelUserLimitUpdateEvent) {
                    onVoiceChannelUserLimitUpdate((VoiceChannelUserLimitUpdateEvent) event);
                }
            }
        } else if (event instanceof ChannelDeleteEvent) {
            onChannelDelete((ChannelDeleteEvent) event);
            if (event instanceof GuildChannelDeleteEvent) {
                onGuildChannelDelete((GuildChannelDeleteEvent) event);
                if (event instanceof TextChannelDeleteEvent) {
                    onTextChannelDelete((TextChannelDeleteEvent) event);
                }else if (event instanceof VoiceChannelDeleteEvent) {
                    onVoiceChannelDelete((VoiceChannelDeleteEvent) event);
                }
            } else if (event instanceof PrivateChannelDeleteEvent) {
                onPrivateChannelDelete((PrivateChannelDeleteEvent) event);
            }
        }
    }

    /*
        General Channel Events
     */
    public void onChannelEvent (ChannelEvent event) {}

    public void onGuildChannelEvent (IGuildChannelEvent event) {}

    public void onTextChannelEvent (ITextChannelEvent event) {}

    public void onVoiceChannelEvent (IVoiceChannelEvent event) {}

    public void onPrivateChannelEvent (IPrivateChannelEvent event) {}

    public void onChannelCreate (ChannelCreateEvent event) {}

    public void onGuildChannelCreate (GuildChannelCreateEvent event) {}

    public void onTextChannelCreate (TextChannelCreateEvent event) {}

    public void onVoiceChannelCreate (VoiceChannelCreateEvent event) {}

    public void onPrivateChannelCreate (PrivateChannelCreateEvent event) {}

    public void onGuildChannelUpdate (GuildChannelUpdateEvent event) {}

    public void onTextChannelUpdate (TextChannelUpdateEvent event) {}

    public void onTextChannelNameUpdate (TextChannelNameUpdateEvent event) {}

    public void onTextChannelPositionUpdate (TextChannelPositionUpdateEvent event) {}

    public void onTextChannelPermissionsUpdate (TextChannelPermissionsUpdateEvent event) {}

    public void onTextChannelTopicUpdate (TextChannelTopicUpdateEvent event) {}

    public void onVoiceChannelUpdate (VoiceChannelUpdateEvent event) {}

    public void onVoiceChannelNameUpdate (VoiceChannelNameUpdateEvent event) {}

    public void onVoiceChannelPositionUpdate (VoiceChannelPositionUpdateEvent event) {}

    public void onVoiceChannelPermissionsUpdate (VoiceChannelPermissionsUpdateEvent event) {}

    public void onVoiceChannelBitrateUpdate (VoiceChannelBitrateUpdateEvent event) {}

    public void onVoiceChannelUserLimitUpdate (VoiceChannelUserLimitUpdateEvent event) {}

    public void onChannelDelete (ChannelDeleteEvent event) {}

    public void onGuildChannelDelete (GuildChannelDeleteEvent event) {}

    public void onTextChannelDelete (TextChannelDeleteEvent event) {}

    public void onVoiceChannelDelete (VoiceChannelDeleteEvent event) {}

    public void onPrivateChannelDelete (PrivateChannelDeleteEvent event) {}

    /**
     * Message Events
     */
    private void dispatchMessageEvent(MessageEvent event) {
        onMessageEvent(event);
        if (event instanceof IGuildMessageEvent) {
            onGuildMessageEvent((IGuildMessageEvent) event);
        } else if (event instanceof IPrivateMessageEvent) {
            onPrivateMessageEvent((IPrivateMessageEvent) event);
        }

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

    public void onGuildMessageEvent (IGuildMessageEvent event) {}

    public void onPrivateMessageEvent (IPrivateMessageEvent event) {}

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
