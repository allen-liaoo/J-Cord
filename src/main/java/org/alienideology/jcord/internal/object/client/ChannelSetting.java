package org.alienideology.jcord.internal.object.client;

import org.alienideology.jcord.handle.channel.ITextChannel;
import org.alienideology.jcord.handle.client.IChannelSetting;
import org.alienideology.jcord.handle.client.MessageNotification;

/**
 * @author AlienIdeology
 */
public class ChannelSetting extends ClientObject implements IChannelSetting {

    private ITextChannel channel;
    private MessageNotification notificationSetting;
    private boolean isMuted;

    public ChannelSetting(Client client, ITextChannel channel, MessageNotification notificationSetting, boolean isMuted) {
        super(client);
        this.channel = channel;
        this.notificationSetting = notificationSetting;
        this.isMuted = isMuted;
    }

    @Override
    public ITextChannel getChannel() {
        return channel;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChannelSetting)) return false;
        if (!super.equals(o)) return false;

        ChannelSetting that = (ChannelSetting) o;

        if (isMuted != that.isMuted) return false;
        if (!channel.equals(that.channel)) return false;
        return notificationSetting == that.notificationSetting;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + channel.hashCode();
        result = 31 * result + (isMuted ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ChannelSetting{" +
                "channel=" + channel +
                ", notificationSetting=" + notificationSetting +
                '}';
    }

}
