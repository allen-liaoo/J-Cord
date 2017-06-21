package org.alienideology.jcord.event.channel;

import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.Channel;
import org.alienideology.jcord.internal.object.user.User;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * @author AlienIdeology
 */
public class TypingStartEvent extends ChannelEvent {

    private User typingUser;
    private OffsetDateTime timeStamp;

    public TypingStartEvent(IdentityImpl identity, int sequence, Channel channel, User typingUser, int unixTimeStamp) {
        super(identity, sequence, channel);
        this.typingUser = typingUser;
        this.timeStamp = OffsetDateTime.ofInstant(Instant.ofEpochSecond(unixTimeStamp), ZoneOffset.UTC);
    }

    public User getTypingUser() {
        return typingUser;
    }

    public OffsetDateTime getTimeStamp() {
        return timeStamp;
    }
}
