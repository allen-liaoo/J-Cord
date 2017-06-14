package org.alienideology.jcord.internal.event.guild;

import org.alienideology.jcord.internal.Identity;
import org.alienideology.jcord.internal.object.Guild;

/**
 * GuildCreateEvent - Fired whenever a guild is detected
 * This event is a general guild event.
 * Three ways this event is fired:
 *  - Initially connecting
 *  - Guild become available
 *  - User join new guild
 * Do not use this event for checking if the user join a new guild or not.
 * @author AlienIdeology
 */
public class GuildCreateEvent extends GuildEvent {

    public GuildCreateEvent(Identity identity, Guild guild, int sequence) {
        super(identity, guild, sequence);
    }

}
