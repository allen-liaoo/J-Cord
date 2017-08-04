package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.event.channel.group.user.GroupUserJoinEvent;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.ObjectBuilder;
import org.alienideology.jcord.internal.object.channel.Group;
import org.alienideology.jcord.internal.object.client.Client;
import org.alienideology.jcord.internal.object.user.User;
import org.alienideology.jcord.util.log.LogLevel;
import org.json.JSONObject;

/**
 * @author AlienIdeology
 */
public class ChannelRecipientAddEventHandler extends EventHandler {

    public ChannelRecipientAddEventHandler(IdentityImpl identity) {
        super(identity);
    }

    @Override
    public void dispatchEvent(JSONObject json, int sequence) {
        Client client = identity.getClient();

        Group group = (Group) client.getGroup(json.getString("channel_id"));
        if (group == null) {
            logger.log(LogLevel.FETAL, "[UNKNOWN GROUP] [CHANNEL_RECIPIENT_ADD_EVENT_HANDLER] ID: " + json.getString("channel_id"));
            return;
        }

        User user = new ObjectBuilder(identity.getClient()).buildUser(json.getJSONObject("user"));
        group.getRecipients().add(user);

        dispatchEvent(new GroupUserJoinEvent(identity, sequence, group, user));

    }

}
