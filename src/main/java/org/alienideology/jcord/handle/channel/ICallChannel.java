package org.alienideology.jcord.handle.channel;

import org.alienideology.jcord.IdentityType;
import org.alienideology.jcord.handle.client.call.ICall;
import org.jetbrains.annotations.Nullable;

/**
 * ICallChannel - A channel that allows users to call other users.
 *
 * @author AlienIdeology
 */
public interface ICallChannel extends IAudioChannel {
    // Call, End Call

    /**
     * Get the current call for this channel.
     * This returns null if there is no call currently.
     *
     * @return The current call.
     */
    @Nullable
    ICall getCurrentCall();

    /**
     * Not supported yet.
     */
    default void startCall() {
        if (getIdentity().getType().equals(IdentityType.CLIENT)) {
            getIdentity().getAsClient().getManager().startCall(this);
        } else {
            throw new IllegalArgumentException("Cannot call an user with bot identity type!");
        }
    }

    /**
     * Not supported yet.
     */
    default void endCall() {
        if (getIdentity().getType().equals(IdentityType.CLIENT)) {
            getIdentity().getAsClient().getManager().endCall(this);
        } else {
            throw new IllegalArgumentException("Cannot call an user with bot identity type!");
        }
    }

}
