package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.event.user.PresenceUpdateEvent;
import org.alienideology.jcord.handle.user.Presence;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.user.User;
import org.json.JSONObject;

/**
 * @author AlienIdeology
 */
public class PresenceUpdateEventHandler extends EventHandler {

    public PresenceUpdateEventHandler(IdentityImpl identity) {
        super(identity);
    }

    @Override
    public void dispatchEvent(JSONObject json, int sequence) {
        User user = (User) identity.getUser(json.getJSONObject("user").getString("id"));
        Presence oldPresence = user.getPresence();
        builder.buildPresence(json, user); // Presence are automatically set to the user

        fireEvent(new PresenceUpdateEvent(identity, sequence, user, oldPresence));
    }

}
