package org.alienideology.jcord.handle.channel;

import org.alienideology.jcord.handle.user.IUser;

/**
 * PrivateChannel - A one-to-one channel between two users
 * @author AlienIdeology
 */
public interface IPrivateChannel extends IMessageChannel {

    IUser getRecipient();

}
