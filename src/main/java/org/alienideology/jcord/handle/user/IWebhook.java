package org.alienideology.jcord.handle.user;

import org.alienideology.jcord.handle.IDiscordObject;
import org.alienideology.jcord.handle.ISnowFlake;
import org.alienideology.jcord.handle.channel.ITextChannel;
import org.alienideology.jcord.handle.guild.IGuild;

/**
 * IWebhook - A low effort way to send messages to channels.
 *
 * @author AlienIdeology
 */
public interface IWebhook extends IDiscordObject, ISnowFlake {

    IGuild getGuild();

    ITextChannel getChannel();

    IUser getAuthor();

    String getDefaultName();

    String getDefaultAvatar();

    String getToken();

}
