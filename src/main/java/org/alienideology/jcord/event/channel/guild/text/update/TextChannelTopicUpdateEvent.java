package org.alienideology.jcord.event.channel.guild.text.update;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.channel.guild.text.TextChannelUpdateEvent;
import org.alienideology.jcord.handle.channel.IChannel;

/**
 * @author AlienIdeology
 */
public class TextChannelTopicUpdateEvent extends TextChannelUpdateEvent {

    private final String oldTopic;

    public TextChannelTopicUpdateEvent(Identity identity, int sequence, IChannel channel, String oldTopic) {
        super(identity, sequence, channel);
        this.oldTopic = oldTopic;
    }

    public String getNewTopic() {
        return channel.getTopic();
    }

    public String getOldTopic() {
        return oldTopic;
    }

}
