package org.alienideology.jcord.internal.object.client.setting;

import org.alienideology.jcord.handle.client.setting.IClientSetting;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.user.OnlineStatus;
import org.alienideology.jcord.internal.object.client.Client;
import org.alienideology.jcord.internal.object.client.ClientObject;

import java.util.List;
import java.util.Locale;

/**
 * @author AlienIdeology
 */
public final class ClientSetting extends ClientObject implements IClientSetting {

    private OnlineStatus status;
    private Locale locale;

    private List<IGuild> guildsPositions;
    private List<IGuild> restrictedGuilds;

    private boolean showCurrentGame;
    private boolean developerMode;
    private boolean messageDisplayCompact;

    // Message
    private boolean enableTTS;
    private boolean convertEmoticons;
    private boolean renderReaction;
    private boolean renderEmbeds;
    private boolean inlineEmbedMedia;
    private boolean inlineAttachmentMedia;

    private boolean detectPlatformAccounts;
    private boolean defaultGuildsRestricted;

    public ClientSetting(Client client, OnlineStatus status, Locale locale, List<IGuild> guildsPositions, List<IGuild> restrictedGuilds) {
        super(client);
        this.status = status;
        this.locale = locale;
        this.guildsPositions = guildsPositions;
        this.restrictedGuilds = restrictedGuilds;
    }

    @Override
    public OnlineStatus getStatus() {
        return status;
    }

    @Override
    public Locale getLocale() {
        return locale;
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
    public boolean isMessageDisplayCompact() {
        return messageDisplayCompact;
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

    @Override
    public boolean isDetectPlatformAccounts() {
        return detectPlatformAccounts;
    }

    //--------------------Setters------------------------

    public void setStatus(OnlineStatus status) {
        this.status = status;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
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

    public void setMessageDisplayCompact(boolean messageDisplayCompact) {
        this.messageDisplayCompact = messageDisplayCompact;
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

    public void setDetectPlatformAccounts(boolean detectPlatformAccounts) {
        this.detectPlatformAccounts = detectPlatformAccounts;
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
