package org.alienideology.jcord.internal.object.client;

import org.alienideology.jcord.handle.client.IChannelSetting;
import org.alienideology.jcord.handle.client.IGuildSetting;
import org.alienideology.jcord.handle.client.MessageNotification;
import org.alienideology.jcord.handle.guild.IGuild;

import java.util.ArrayList;
import java.util.List;

/**
 * @author AlienIdeology
 */
public class GuildSetting extends ClientObject implements IGuildSetting {

    private IGuild guild;
    private List<IChannelSetting> channelSettings = new ArrayList<>();
    private MessageNotification notificationSetting;

    private boolean isMuted;
    private boolean isMobilePushEnabled;
    private boolean isEveryoneMentionSuppressed;

    public GuildSetting(Client client, IGuild guild, MessageNotification notificationSetting, boolean isMuted, boolean isMobilePushEnabled, boolean isEveryoneMentionSuppressed) {
        super(client);
        this.guild = guild;
        this.notificationSetting = notificationSetting;
        this.isMuted = isMuted;
        this.isMobilePushEnabled = isMobilePushEnabled;
        this.isEveryoneMentionSuppressed = isEveryoneMentionSuppressed;
    }

    @Override
    public IGuild getGuild() {
        return guild;
    }

    @Override
    public IChannelSetting getChannelSetting(String channelId) {
        for (IChannelSetting setting : channelSettings) {
            if (setting.getChannel().getId().equals(channelId)) {
                return setting;
            }
        }
        return null;
    }

    @Override
    public List<IChannelSetting> getChannelSettings() {
        return channelSettings;
    }

    @Override
    public MessageNotification getNotificationSetting() {
        return notificationSetting;
    }

    @Override
    public boolean isMuted() {
        return isMuted;
    }

    @Override
    public boolean isMobilePushEnabled() {
        return isMobilePushEnabled;
    }

    @Override
    public boolean isEveryoneMentionSuppressed() {
        return isEveryoneMentionSuppressed;
    }

    //---------------------Internal---------------------
    public void addChannelSetting(IChannelSetting channelSetting) {
        channelSettings.add(channelSetting);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GuildSetting)) return false;
        if (!super.equals(o)) return false;

        GuildSetting setting = (GuildSetting) o;

        return guild != null ? guild.equals(setting.guild) : setting.guild == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (guild != null ? guild.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GuildSetting{" +
                "guild=" + guild +
                ", notificationSetting=" + notificationSetting +
                '}';
    }
}
