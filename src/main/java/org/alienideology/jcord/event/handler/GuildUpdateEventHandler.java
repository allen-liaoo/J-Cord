package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.event.guild.update.*;
import org.alienideology.jcord.handle.Region;
import org.alienideology.jcord.handle.channel.ITextChannel;
import org.alienideology.jcord.handle.channel.IVoiceChannel;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IMember;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.util.log.LogLevel;
import org.json.JSONObject;

import java.util.Objects;

/**
 * @author AlienIdeology
 */
public class GuildUpdateEventHandler extends EventHandler {

    public GuildUpdateEventHandler(IdentityImpl identity) {
        super(identity);
    }

    @Override
    public void dispatchEvent(JSONObject json, int sequence) {
        String id = json.getString("id");
        Guild guild = (Guild) identity.getGuild(id);

        if (guild == null) {
            logger.log(LogLevel.FETAL, "[UNKNOWN GUILD] [GUILD_UPDATE_EVENT]");
            return;
        }

        String name = json.getString("name");
        String icon = json.isNull("icon") ? null : json.getString("icon");
        String splash = json.isNull("splash") ? null : json.getString("splash");
        String ownerId = json.getString("owner_id");
        IMember owner = guild.getMember(ownerId);
        String region = json.getString("region");
        int afk_timeout = json.getInt("afk_timeout");
        String afkChannelId = json.has("afk_channel_id") && !json.isNull("afk_channel_id") ? json.getString("afk_channel_id") : null;
        IVoiceChannel afkChannel = guild.getVoiceChannel(afkChannelId);
        boolean embed_enabled = json.has("embed_enabled") && json.getBoolean("embed_enabled");
        String embedChannelId = json.has("embed_channel_id") && !json.isNull("embed_channel_id") ? json.getString("embed_channel_id") : null;
        ITextChannel embedChannel = guild.getTextChannel(embedChannelId);
        int verification_level = json.getInt("verification_level");
        int notifications_level = json.getInt("default_message_notifications");
        int ecf_level = json.getInt("explicit_content_filter");
        int mfa_level = json.getInt("mfa_level");

        if (!Objects.equals(guild.getName(), name)) {
            String oldName = guild.getName();
            guild.setName(name);
            dispatchEvent(new GuildNameUpdateEvent(identity, sequence, guild, oldName));
        }
        if (ownerId != null && owner == null) {
            logger.log(LogLevel.FETAL, "[UNKNOWN GUILD OWNER] [GUILD_UPDATE_EVENT] ID: " + ownerId);
        } else if (!Objects.equals(guild.getOwner(), owner)) {
            IMember oldOwner = guild.getOwner();
            guild.setOwner(ownerId);
            dispatchEvent(new GuildOwnerUpdateEvent(identity, sequence, guild, oldOwner));
        }
        if (!Objects.equals(guild.getRegion().key, region)) {
            if (Region.getByKey(region).equals(Region.UNKNOWN)) {
                logger.log(LogLevel.FETAL, "[UNKNOWN REGION] [GUILD_UPDATE_EVENT] Please inform the developer for a new region found: " + region);
            }
            Region oldRegion = guild.getRegion();
            guild.setRegion(region);
            dispatchEvent(new GuildRegionUpdateEvent(identity, sequence, guild, oldRegion));
        }
        if (!Objects.equals(guild.getIconHash(), icon)) {
            String oldIcon = guild.getIconHash();
            guild.setIcon(icon);
            dispatchEvent(new GuildIconUpdateEvent(identity, sequence, guild, oldIcon));
        }
        if (!Objects.equals(guild.getSplash(), splash)) {
            String oldSplash = guild.getSplash();
            guild.setSplash(splash);
            dispatchEvent(new GuildSplashUpdateEvent(identity, sequence, guild, oldSplash));
        }
        if (!Objects.equals(guild.getAfkTimeout().key, afk_timeout)) {
            if (IGuild.AFKTimeout.getByTimeout(afk_timeout).equals(IGuild.AFKTimeout.UNKNOWN)) {
                logger.log(LogLevel.FETAL, "[UNKNOWN AFK TIMEOUT] [GUILD_UPDATE_EVENT] Please inform the developer for a new afk timeout found: " + afk_timeout);
            }
            IGuild.AFKTimeout oldTimeout = guild.getAfkTimeout();
            guild.setAfkTimeout(afk_timeout);
            dispatchEvent(new GuildAFKTimeoutUpdateEvent(identity, sequence, guild, oldTimeout));
        }
        if (afkChannelId != null && afkChannel == null) {
            logger.log(LogLevel.FETAL, "[UNKNOWN AFK CHANNEL] [GUILD_UPDATE_EVENT] ID: " + afkChannelId);
        } else if (!Objects.equals(guild.getAfkChannel(), afkChannel)) {
            IVoiceChannel oldAFKChannel = guild.getAfkChannel();
            guild.setAfkChannel(afkChannelId);
            dispatchEvent(new GuildAFKChannelUpdateEvent(identity, sequence, guild, oldAFKChannel));
        }
        if (embed_enabled != guild.isEmbedEnabled()) {
            guild.setEmbedEnabled(embed_enabled);
            dispatchEvent(new GuildEmbedEnabledUpdateEvent(identity, sequence, guild));
        }
        if (embedChannelId != null && embedChannel == null) {
            logger.log(LogLevel.FETAL, "[UNKNOWN EMBED CHANNEL] [GUILD_UPDATE_EVENT] ID: " + embedChannelId);
        } else if (!Objects.equals(guild.getAfkChannel(), afkChannel)) {
            ITextChannel oldEmbedChannel = guild.getEmbedChannel();
            guild.setEmbedChannel(embedChannelId);
            dispatchEvent(new GuildEmbedChannelUpdateEvent(identity, sequence, guild, oldEmbedChannel));
        }
        if (!Objects.equals(guild.getVerificationLevel().key, verification_level)) {
            if (IGuild.Verification.getByKey(verification_level).equals(IGuild.Verification.UNKNOWN)) {
                logger.log(LogLevel.FETAL, "[UNKNOWN VERIFICATION LEVEL] [GUILD_UPDATE_EVENT] Please inform the developer for a new verification level found: " + verification_level);
            }
            IGuild.Verification oldVerification = guild.getVerificationLevel();
            guild.setVerificationLevel(verification_level);
            dispatchEvent(new GuildVerificationUpdateEvent(identity, sequence, guild, oldVerification));
        }
        if (!Objects.equals(guild.getNotificationsLevel().key, notifications_level)) {
            if (IGuild.Notification.getByKey(notifications_level).equals(IGuild.Notification.UNKNOWN)) {
                logger.log(LogLevel.FETAL, "[UNKNOWN NOTIFICATION LEVEL] [GUILD_UPDATE_EVENT] Please inform the developer for a new notification level found: " + notifications_level);
            }
            IGuild.Notification oldNotification = guild.getNotificationsLevel();
            guild.setNotificationLevel(notifications_level);
            dispatchEvent(new GuildNotificationUpdateEvent(identity, sequence, guild, oldNotification));
        }
        if (!Objects.equals(guild.getMFALevel().key, mfa_level)) {
            if (IGuild.MFA.getByKey(mfa_level).equals(IGuild.MFA.UNKNOWN)) {
                logger.log(LogLevel.FETAL, "[UNKNOWN MFA LEVEL] [GUILD_UPDATE_EVENT] Please inform the developer for a new mfa level found: " + mfa_level);
            }
            IGuild.MFA oldMFA = guild.getMFALevel();
            guild.setMfaLevel(mfa_level);
            dispatchEvent(new GuildMFAUpdateEvent(identity, sequence, guild, oldMFA));
        }
        if (!Objects.equals(guild.getContentFilterLevel().key, ecf_level)) {
            if (IGuild.ContentFilterLevel.getByKey(ecf_level).equals(IGuild.ContentFilterLevel.UNKNOWN)) {
                logger.log(LogLevel.FETAL, "[UNKNOWN CONTENT FILTER LEVEL] [GUILD_UPDATE_EVENT] Please inform the developer for a new content filter level found: " + ecf_level);
            }
            IGuild.ContentFilterLevel oldContentFilter = guild.getContentFilterLevel();
            guild.setEcfLevel(ecf_level);
            dispatchEvent(new GuildContentFilterUpdateEvent(identity, sequence, guild, oldContentFilter));
        }
    }
}
