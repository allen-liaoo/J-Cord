package org.alienideology.jcord.internal.object.client.call;

import org.alienideology.jcord.handle.Region;
import org.alienideology.jcord.handle.channel.ICallChannel;
import org.alienideology.jcord.handle.channel.IChannel;
import org.alienideology.jcord.handle.channel.IGroup;
import org.alienideology.jcord.handle.channel.IPrivateChannel;
import org.alienideology.jcord.handle.client.call.ICall;
import org.alienideology.jcord.handle.client.call.ICallUser;
import org.alienideology.jcord.internal.object.client.Client;
import org.alienideology.jcord.internal.object.client.ClientObject;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author AlienIdeology
 */
public class Call extends ClientObject implements ICall {

    private final String id;

    private Region region;
    private ICallChannel channel;

    List<ICallUser> waitingUsers = new ArrayList<>();
    List<ICallUser> users = new ArrayList<>();

    public Call(Client client, String id, Region region, ICallChannel channel) {
        super(client);
        this.id = id;
        this.region = region;
        this.channel = channel;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Region getRegion() {
        return region;
    }

    @Override
    public ICallChannel getChannel() {
        return channel;
    }

    @Override
    @Nullable
    public IGroup getGroup() {
        return channel.getType().equals(IChannel.Type.GROUP_DM) ? (IGroup) channel : null;
    }

    @Override
    @Nullable
    public IPrivateChannel getPrivateChannel() {
        return channel.getType().equals(IChannel.Type.DM) ? (IPrivateChannel) channel : null;
    }

    @Override
    public List<ICallUser> getWaitingUsers() {
        return waitingUsers;
    }

    @Override
    public List<ICallUser> getConnectedUsers() {
        return users;
    }

    @Override
    public List<ICallUser> getAllUsers() {
        List<ICallUser> users = new ArrayList<>(waitingUsers);
        users.addAll(this.users);
        return users;
    }

    //----------------Internal----------------

    public void setRegion(Region region) {
        this.region = region;
    }

    public void addWaitingUsers(ICallUser waitingUser) {
        this.waitingUsers.add(waitingUser);
    }

    public void addUser(ICallUser user) {
        this.users.add(user);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Call)) return false;
        if (!super.equals(o)) return false;

        Call call = (Call) o;

        if (!id.equals(call.id)) return false;
        return channel != null ? channel.equals(call.channel) : call.channel == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + (channel != null ? channel.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Call{" +
                "id='" + id + '\'' +
                ", region=" + region +
                ", channel=" + channel +
                '}';
    }

}
