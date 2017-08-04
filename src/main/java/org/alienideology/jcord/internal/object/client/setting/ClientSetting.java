package org.alienideology.jcord.internal.object.client.setting;

import org.alienideology.jcord.handle.client.setting.IClientSetting;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.user.OnlineStatus;
import org.alienideology.jcord.internal.object.client.Client;
import org.alienideology.jcord.internal.object.client.ClientObject;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;

/**
 * @author AlienIdeology
 */
public final class ClientSetting extends ClientObject implements IClientSetting {

    private OnlineStatus status;
    private ZoneId timeZone;

    private IClientSetting.Theme theme;
    private IClientSetting.Locale locale;
    private IClientSetting.ContentFilterLevel contentFilterLevel;
    private IClientSetting.FriendSource[] friendSources;
    private IClientSetting.PushNotificationAFKTimeout pushNotificationAFKTimeout;

    private List<IGuild> guildsPositions;
    private List<IGuild> restrictedGuilds;

    private boolean showCurrentGame;
    private boolean developerMode;
    private boolean guildRestrictedByDefault;
    private boolean messageDisplayCompact;
    private boolean detectPlatformAccounts;

    // Message
    private boolean enableTTS;
    private boolean convertEmoticons;
    private boolean renderReaction;
    private boolean renderEmbeds;
    private boolean inlineEmbedMedia;
    private boolean inlineAttachmentMedia;

    public ClientSetting(Client client) {
        super(client);
    }

    @Override
    public OnlineStatus getStatus() {
        return status;
    }

    @Override
    public Theme getTheme() {
        return theme;
    }

    @Override
    public ZoneId getTimeZone() {
        return timeZone;
    }

    @Override
    public IClientSetting.Locale getLocale() {
        return locale;
    }

    @Override
    public ContentFilterLevel getContentFilterLevel() {
        return contentFilterLevel;
    }

    @Override
    public PushNotificationAFKTimeout getPushNotificationAFKTimeout() {
        return pushNotificationAFKTimeout;
    }

    @Override
    public FriendSource[] getFriendSources() {
        return friendSources;
    }

    @Override
    public List<IGuild> getGuildsPositions() {
        return guildsPositions;
    }

    @Override
    public List<IGuild> getRestrictedGuilds() {
        return restrictedGuilds;
    }

    @Override
    public boolean isShowCurrentGame() {
        return showCurrentGame;
    }

    @Override
    public boolean isDeveloperMode() {
        return developerMode;
    }

    @Override
    public boolean isGuildRestrictedByDefault() {
        return guildRestrictedByDefault;
    }

    @Override
    public boolean isMessageDisplayCompact() {
        return messageDisplayCompact;
    }

    @Override
    public boolean isDetectPlatformAccounts() {
        return detectPlatformAccounts;
    }
    @Override
    public boolean isEnableTTS() {
        return enableTTS;
    }

    @Override
    public boolean isConvertEmoticons() {
        return convertEmoticons;
    }

    @Override
    public boolean isRenderReaction() {
        return renderReaction;
    }

    @Override
    public boolean isRenderEmbeds() {
        return renderEmbeds;
    }

    @Override
    public boolean isInlineEmbedMedia() {
        return inlineEmbedMedia;
    }

    @Override
    public boolean isInlineAttachmentMedia() {
        return inlineAttachmentMedia;
    }


    //--------------------Setters------------------------

    public void setStatus(OnlineStatus status) {
        this.status = status;
    }

    public void setTimeZone(int minutes) {
        timeZone = ZoneId.ofOffset("UTC", ZoneOffset.ofHours(minutes / 60));
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public void setContentFilterLevel(ContentFilterLevel contentFilterLevel) {
        this.contentFilterLevel = contentFilterLevel;
    }

    public void setPushNotificationAFKTimeout(PushNotificationAFKTimeout pushNotificationAFKTimeout) {
        this.pushNotificationAFKTimeout = pushNotificationAFKTimeout;
    }

    public void setFriendSources(FriendSource[] friendSources) {
        this.friendSources = friendSources;
    }

    public void setGuildsPositions(List<IGuild> guildsPositions) {
        this.guildsPositions = guildsPositions;
    }

    public void setRestrictedGuilds(List<IGuild> restrictedGuilds) {
        this.restrictedGuilds = restrictedGuilds;
    }

    public void setShowCurrentGame(boolean showCurrentGame) {
        this.showCurrentGame = showCurrentGame;
    }

    public void setDeveloperMode(boolean developerMode) {
        this.developerMode = developerMode;
    }

    public void setGuildRestrictedByDefault(boolean guildRestrictedByDefault) {
        this.guildRestrictedByDefault = guildRestrictedByDefault;
    }

    public void setMessageDisplayCompact(boolean messageDisplayCompact) {
        this.messageDisplayCompact = messageDisplayCompact;
    }

    public void setDetectPlatformAccounts(boolean detectPlatformAccounts) {
        this.detectPlatformAccounts = detectPlatformAccounts;
    }

    public void setEnableTTS(boolean enableTTS) {
        this.enableTTS = enableTTS;
    }

    public void setConvertEmoticons(boolean convertEmoticons) {
        this.convertEmoticons = convertEmoticons;
    }

    public void setRenderReaction(boolean renderReaction) {
        this.renderReaction = renderReaction;
    }

    public void setRenderEmbeds(boolean renderEmbeds) {
        this.renderEmbeds = renderEmbeds;
    }

    public void setInlineEmbedMedia(boolean inlineEmbedMedia) {
        this.inlineEmbedMedia = inlineEmbedMedia;
    }

    public void setInlineAttachmentMedia(boolean inlineAttachmentMedia) {
        this.inlineAttachmentMedia = inlineAttachmentMedia;
    }

    @Override
    public String toString() {
        return "ClientSetting{" +
                "status=" + status +
                ", locale=" + locale +
                ", identity=" + identity +
                '}';
    }
}
