package org.alienideology.jcord.internal.object.client;

import org.alienideology.jcord.handle.channel.ITextChannel;
import org.alienideology.jcord.handle.client.IChannelSetting;
import org.alienideology.jcord.handle.client.MessageNotification;
import org.alienideology.jcord.internal.object.DiscordObject;
import org.alienideology.jcord.internal.object.IdentityImpl;

/**
 * @author AlienIdeology
 */
public class ChannelSetting extends DiscordObject implements IChannelSetting {

    private ITextChannel channel;
    private MessageNotification notificationSetting;
    private boolean isMuted;

    public ChannelSetting(IdentityImpl identity, ITextChannel channel, MessageNotification notificationSetting, boolean isMuted) {
        super(identity);
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

}
