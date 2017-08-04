package org.alienideology.jcord.handle.managers;

import org.alienideology.jcord.handle.channel.IGroup;
import org.alienideology.jcord.handle.client.IClient;
import org.alienideology.jcord.handle.client.IClientObject;

/**
 * @author AlienIdeology
 */
public interface IGroupManager extends IClientObject {

    @Override
    default IClient getClient() {
        return getGroup().getClient();
    }

    IGroup getGroup();

//    void modifyName(String name);

//    void modifyIcon(Icon icon);

    void addFriend();

}
