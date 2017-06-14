package org.alienideology.jcord.handle.guild;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.alienideology.jcord.handle.IDiscordObject;
import org.alienideology.jcord.handle.ISnowFlake;
import org.alienideology.jcord.handle.channel.ITextChannel;
import org.alienideology.jcord.handle.channel.IVoiceChannel;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.object.Guild;
import org.alienideology.jcord.internal.object.Region;
import org.alienideology.jcord.internal.object.channel.TextChannel;
import org.alienideology.jcord.internal.object.channel.VoiceChannel;
import org.alienideology.jcord.internal.object.guild.GuildEmoji;
import org.alienideology.jcord.internal.object.guild.Member;
import org.alienideology.jcord.internal.object.guild.Role;
import org.alienideology.jcord.internal.object.user.User;

import java.util.List;

/**
 * Guild - A collection of users and channels, often referred to in the UI as a server.
 * @author AlienIdeology
 */
public interface IGuild extends IDiscordObject, ISnowFlake {

    boolean isAvailable();

    String getName();

    String getIcon();

    String getSplash();

    Region getRegion();

    Guild.AFK_Timeout getAfkTimeout();

    boolean isEmbedEnabled();

    Guild.Verification getVerificationLevel();

    Guild.Notification getNotificationsLevel();

    Guild.MFA getMFALevel();

    @Nullable
    IVoiceChannel getAfkChannel();

    @Nullable
    ITextChannel getEmbedChannel();

    IMember getOwner();

    List<IUser> getUsers();

    /**
    * Get the member instance of the identity.
    * @return The member instance
    */
    @NotNull
    IMember getSelfMember();

    /**
    * Get a member by id
    * @param id The specified id
    * @return a Member or null if no member was found.
    */
    @Nullable
    IMember getMember(String id);

    List<IMember> getMembers();

    /**
    * Get a role by id.
    * @param id The specified id
    * @return a Role or null if no role was found.
    */
    @Nullable
    IRole getRole(String id);

    @NotNull
    IRole getEveryoneRole();

    List<IRole> getRoles();

    /**
    * Get a guild emoji by id.
    * @param id The specified id
    * @return a GuildEmoji or null if no emoji was found.
    */
    @Nullable
    IGuildEmoji getGuildEmoji(String id);

    List<IGuildEmoji> getGuildEmojis();

    /**
    * Get a text channel by id.
    * @param id The specified id
    * @return a TextChannel or null if no channel was found.
    */
    @Nullable
    ITextChannel getTextChannel(String id);

    List<ITextChannel> getTextChannels();

    /**
    * Get a voice channel by id.
    * @param id The specified id
    * @return a VoiceChannel or null if no channel is found.
    */
    @Nullable
    IVoiceChannel getVoiceChannel(String id);

    List<IVoiceChannel> getVoiceChannels();
    
}
