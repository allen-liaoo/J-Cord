package org.alienideology.jcord.event.client.call.update;

import org.alienideology.jcord.event.client.call.CallUpdateEvent;
import org.alienideology.jcord.handle.client.call.ICallUser;
import org.alienideology.jcord.internal.object.client.Client;
import org.alienideology.jcord.internal.object.client.call.Call;

import java.util.List;

/**
 * @author AlienIdeology
 */
public class CallWaitingUsersUpdateEvent extends CallUpdateEvent {

    private final List<ICallUser> stopWaitingUsers;
    private final List<ICallUser> startWaitingUsers;

    public CallWaitingUsersUpdateEvent(Client client, int sequence, Call call, List<ICallUser> stopWaitingUsers, List<ICallUser> startWaitingUsers) {
        super(client, sequence, call);
        this.stopWaitingUsers = stopWaitingUsers;
        this.startWaitingUsers = startWaitingUsers;
    }

    public List<ICallUser> getStopWaitingUsers() {
        return stopWaitingUsers;
    }

    public List<ICallUser> getStartWaitingUsers() {
        return startWaitingUsers;
    }

}
