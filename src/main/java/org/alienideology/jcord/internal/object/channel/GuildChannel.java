package org.alienideology.jcord.internal.object.channel;

import org.alienideology.jcord.internal.object.Guild;

/**
 * GuildChannel - A Channel that exist in a guild
 * @author AlienIdeology
 */
public interface GuildChannel {

    Guild getGuild();

    String getName();

    int getPosition();

}
