package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.event.channel.group.user.GroupUserLeaveEvent;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.channel.Group;
import org.alienideology.jcord.internal.object.client.Client;
import org.alienideology.jcord.util.log.LogLevel;
import org.json.JSONObject;

/**
 * @author AlienIdeology
 */
public class ChannelRecipientRemoveEventHandler extends EventHandler {

    public ChannelRecipientRemoveEventHandler(IdentityImpl identity) {
        super(identity);
    }

    @Override
    public void dispatchEvent(JSONObject json, int sequence) {
        Client client = identity.getClient();

        Group group = (Group) client.getGroup(json.getString("channel_id"));
        if (group == null) {
            logger.log(LogLevel.FETAL, "[UNKNOWN GROUP] [CHANNEL_RECIPIENT_REMOVE_EVENT_HANDLER] ID: " + json.getString("channel_id"));
            return;
        }

        IUser user = identity.getUser(json.getJSONObject("user").getString("id"));
        if (user == null) {
            logger.log(LogLevel.FETAL, "[UNKNOWN USER] [CHANNEL_RECIPIENT_REMOVE_EVENT_HANDLER] ID: " + json.getJSONObject("user").getString("id"));
            return;
        }
        group.getRecipients().remove(user);
        identity.removeUser(user.getId());

        dispatchEvent(new GroupUserLeaveEvent(identity, sequence, group, user));
    }

}
