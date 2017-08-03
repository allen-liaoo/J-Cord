package org.alienideology.jcord.handle.client.call;

import org.alienideology.jcord.handle.IVoiceState;
import org.alienideology.jcord.handle.client.IClientObject;

/**
 * ICallVoiceState - A voice state for a call.
 *
 * @author AlienIdeology
 */
public interface ICallVoiceState extends IClientObject, IVoiceState {

    /**
     * Get the call of this voice state.
     *
     * @return The call.
     */
    ICall getCall();

    /**
     * Get the call user this voice state belongs to.
     *
     * @return The call user.
     */
    ICallUser getCallUser();

    /**
     * Check if the user is in a call or not.
     * The user can not be waiting and in call at the same time.
     *
     * @return True if the user is in call.
     */
    boolean isInCall();

    /**
     * Check if the user is being invited to the call, but hasn't joined.
     *
     * @return True if the user is being waited.
     */
    boolean isWaiting();

}
