package org.alienideology.jcord.internal.object.managers;

import org.alienideology.jcord.handle.channel.IGroup;
import org.alienideology.jcord.handle.managers.IGroupManager;
import org.alienideology.jcord.internal.object.channel.Group;
import org.alienideology.jcord.internal.rest.HttpPath;
import org.alienideology.jcord.internal.rest.Requester;

/**
 * @author AlienIdeology
 */
public final class GroupManager implements IGroupManager {

    private Group group;

    public GroupManager(Group group) {
        this.group = group;
    }

    @Override
    public IGroup getGroup() {
        return group;
    }

    @Override
    public void add(String friendId) {
        new Requester(getIdentity(), HttpPath.Channel.ADD_RECIPIENT)
                .request(group.getId(), friendId)
                .performRequest();
    }

    @Override
    public void remove(String userId) {
        new Requester(getIdentity(), HttpPath.Channel.REMOVE_RECIPIENT)
                .request(group.getId(), userId)
                .performRequest();
    }

}
