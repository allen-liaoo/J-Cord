package org.alienideology.jcord.event.guild;

import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.guild.Guild;

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

    public GuildCreateEvent(IdentityImpl identity, Guild guild, int sequence) {
        super(identity, sequence, guild);
    }

}
