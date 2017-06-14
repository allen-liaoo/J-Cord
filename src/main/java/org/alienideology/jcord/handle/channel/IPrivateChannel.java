package org.alienideology.jcord.handle.channel;

import org.alienideology.jcord.internal.object.user.User;

/**
 * PrivateChannel - A one-to-one channel between two users
 * @author AlienIdeology
 */
public interface IPrivateChannel extends IMessageChannel {

    User getRecipient();

}
