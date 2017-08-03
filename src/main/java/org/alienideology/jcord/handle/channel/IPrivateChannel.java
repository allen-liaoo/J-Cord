package org.alienideology.jcord.handle.channel;

import org.alienideology.jcord.handle.user.IUser;

/**
 * PrivateChannel - A one-to-one channel between two users
 * @author AlienIdeology
 */
public interface IPrivateChannel extends IMessageChannel, ICallChannel {

    /**
     * Get the recipient of this private channel.
     *
     * @return The recipient user object.
     */
    IUser getRecipient();

    /**
     * Close the private channel.
     * Note that this action can be undone by using {@link IUser#getPrivateChannel()} to reopen a new PrivateChannel.
     */
    default void close() {
        getRecipient().closePrivateChannel();
    }

}
