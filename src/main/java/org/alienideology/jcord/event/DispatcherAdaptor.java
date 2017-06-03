package org.alienideology.jcord.event;

import org.alienideology.jcord.event.GuildEvent.GuildEvent;
import org.alienideology.jcord.event.GuildEvent.GuildCreateEvent;
import org.alienideology.jcord.event.GuildEvent.GuildRoleCreateEvent;

/**
 * Event Listener used to listen to events and perform actions
 * @author AlienIdeology
 */
public class DispatcherAdaptor {

    public final void onEvent (Event event) {
        if (event instanceof GuildEvent) {
            onGuildEvent((GuildEvent) event);
        }
    }

    private void onGuildEvent (GuildEvent event) {
        if (event instanceof GuildCreateEvent) {
            onGuildCreate((GuildCreateEvent) event);
        } else if (event instanceof GuildRoleCreateEvent) {
            onGuildRoleCreate((GuildRoleCreateEvent) event);
        }
    }

    public void onGuildCreate (GuildCreateEvent event) {}

    public void onGuildRoleCreate (GuildRoleCreateEvent event) {}

}
