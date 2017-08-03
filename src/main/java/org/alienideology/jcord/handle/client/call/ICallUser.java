package org.alienideology.jcord.handle.client.call;

import org.alienideology.jcord.handle.client.IClientObject;
import org.alienideology.jcord.handle.user.IUser;

/**
 * ICallUser - A temporary instance representing an user in a call.
 *
 * @author AlienIdeology
 */
public interface ICallUser extends IClientObject {

    default IUser getUser() {
        return getVoiceState().getUser();
    }

    ICall getCall();

    ICallVoiceState getVoiceState();

}
