package org.alienideology.jcord.object.channel;

import org.alienideology.jcord.object.Guild;

/**
 * GuildChannel - A Channel that exist in a guild
 * @author AlienIdeology
 */
public interface GuildChannel {

    Guild getGuild();

    String getName();

    int getPosition();

}
