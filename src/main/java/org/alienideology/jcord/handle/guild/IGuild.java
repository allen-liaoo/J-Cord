package org.alienideology.jcord.handle.guild;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.alienideology.jcord.handle.IDiscordObject;
import org.alienideology.jcord.handle.ISnowFlake;
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
    VoiceChannel getAfkChannel();

    @Nullable
    TextChannel getEmbedChannel();

    Member getOwner();

    List<User> getUsers();

    /**
    * Get the member instance of the identity.
    * @return The member instance
    */
    @NotNull
    Member getSelfMember();

    /**
    * Get a member by id
    * @param id The specified id
    * @return a Member or null if no member was found.
    */
    @Nullable
    Member getMember(String id);

    List<Member> getMembers();

    /**
    * Get a role by id.
    * @param id The specified id
    * @return a Role or null if no role was found.
    */
    @Nullable
    Role getRole(String id);

    @NotNull
    Role getEveryoneRole();

    List<Role> getRoles();

    /**
    * Get a guild emoji by id.
    * @param id The specified id
    * @return a GuildEmoji or null if no emoji was found.
    */
    @Nullable
    GuildEmoji getGuildEmoji(String id);

    List<GuildEmoji> getGuildEmojis();

    /**
    * Get a text channel by id.
    * @param id The specified id
    * @return a TextChannel or null if no channel was found.
    */
    @Nullable
    TextChannel getTextChannel(String id);

    List<TextChannel> getTextChannels();

    /**
    * Get a voice channel by id.
    * @param id The specified id
    * @return a VoiceChannel or null if no channel is found.
    */
    @Nullable
    public VoiceChannel getVoiceChannel(String id);

    List<VoiceChannel> getVoiceChannels();

    @Override
    String getId();
    
}
