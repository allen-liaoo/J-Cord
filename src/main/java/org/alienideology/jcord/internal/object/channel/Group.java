package org.alienideology.jcord.internal.object.channel;

import org.alienideology.jcord.handle.channel.IGroup;
import org.alienideology.jcord.handle.client.IClient;
import org.alienideology.jcord.handle.client.call.ICall;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.object.client.Client;
import org.alienideology.jcord.internal.object.client.call.Call;

import java.util.List;

/**
 * @author AlienIdeology
 */
public class Group extends MessageChannel implements IGroup {

    private Client client;

    private String name;
    private String icon;
    private String ownerId;

    private List<IUser> recipients;

    private Call currentCall;

    public Group(Client client, String id, String name, String icon, String ownerId, List<IUser> recipients) {
        super(client.getIdentity(), id, Type.GROUP_DM);
        this.client = client;
        this.name = name;
        this.icon = icon;
        this.ownerId = ownerId;
        this.recipients = recipients;
    }

    @Override
    public IClient getClient() {
        return client;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getIconHash() {
        return icon;
    }

    @Override
    public IUser getOwner() {
        return recipients.stream()
                .filter(r -> r.getId().equals(ownerId)).findFirst().orElse(null);
    }

    @Override
    public List<IUser> getRecipients() {
        return recipients;
    }

    @Override
    public ICall getCurrentCall() {
        return currentCall;
    }

    public void setCurrentCall(Call currentCall) {
        this.currentCall = currentCall;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Group)) return false;
        if (!super.equals(o)) return false;

        Group group = (Group) o;

        if (!client.equals(group.client)) return false;
        if (name != null ? !name.equals(group.name) : group.name != null) return false;
        return ownerId.equals(group.ownerId);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + client.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + ownerId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Group{" +
                "name='" + name + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", recipients=" + recipients +
                '}';
    }

}
