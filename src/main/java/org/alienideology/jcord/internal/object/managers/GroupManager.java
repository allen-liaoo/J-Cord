package org.alienideology.jcord.internal.object.managers;

import org.alienideology.jcord.handle.Icon;
import org.alienideology.jcord.handle.channel.IGroup;
import org.alienideology.jcord.handle.managers.IGroupManager;
import org.alienideology.jcord.internal.gateway.HttpPath;
import org.alienideology.jcord.internal.gateway.Requester;
import org.alienideology.jcord.internal.object.channel.Group;
import org.json.JSONObject;

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
    public void modifyName(String name) {
        modifyChannel(new JSONObject().put("name", name == null ? "" : name));
    }

    @Override
    public void modifyIcon(Icon icon) {
        modifyChannel(new JSONObject().put("icon", icon.getData()));
    }

    private void modifyChannel(JSONObject json) {
        new Requester(getIdentity(), HttpPath.Channel.MODIFY_CHANNEL)
                .request(group.getId())
                .updateRequestWithBody(request -> request.body(json))
                .performRequest();
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
