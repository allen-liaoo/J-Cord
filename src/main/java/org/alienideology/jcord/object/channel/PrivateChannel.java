package org.alienideology.jcord.object.channel;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.object.Channel;
import org.alienideology.jcord.object.User;

/**
 * PrivateChannel - A one-to-one channel between two users
 * @author AlienIdeology
 */
public class PrivateChannel extends Channel {

    private User recipent;
//    private Message message;

    public PrivateChannel(Identity identity, String id, User recipient, String last_message_id) {
        super(identity, id, true);
        this.recipent = recipient;
//        this.message = new ObjectBuilder().buildMessage(HttpPath.Channel.GET_CHANNEL_MESSAGE.request(identity, id, last_message_id).asjson());
    }
}
