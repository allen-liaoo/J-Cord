package org.alienideology.jcord.event.guild;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.guild.IGuild;

/**
 * GuildCreateEvent - Fired whenever a guild is detected
 * This event is a general guild event.
 * Three ways this event is fired:
 * <ul>
 *     <li>Initially connecting</li>
 *     <li>Guild become available</li>
 *     <li>User join new guild</li>
 * </ul>
 *
 * Do not use this event for checking if an user join a new guild.
 * @author AlienIdeology
 */
public class GuildCreateEvent extends GuildEvent {

    public GuildCreateEvent(Identity identity, int sequence, IGuild guild) {
        super(identity, sequence, guild);
    }

}
