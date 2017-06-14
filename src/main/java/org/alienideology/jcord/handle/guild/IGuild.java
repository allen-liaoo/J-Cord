package org.alienideology.jcord.handle.guild;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.alienideology.jcord.handle.IDiscordObject;
import org.alienideology.jcord.handle.ISnowFlake;
import org.alienideology.jcord.handle.channel.ITextChannel;
import org.alienideology.jcord.handle.channel.IVoiceChannel;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.object.Guild;
import org.alienideology.jcord.handle.Region;

import java.util.Arrays;
import java.util.List;

/**
 * Guild - A collection of users and channels, often referred to in the UI as a server.
 * @author AlienIdeology
 */
public interface IGuild extends IDiscordObject, ISnowFlake {

    /**
     * @return True if the guild is available (no temporary shortage happens to discord server)
     */
    boolean isAvailable();

    /**
     * Get the name of this guild.
     *
     * @return The string name.
     */
    String getName();

    /**
     * Get the icon url of this guild.
     *
     * @return The string url, or null if the guild does not have an icon.
     */
    String getIcon();

    /**
     * Get the splash icon url of this guild. A guild can get splash by partnering with Discord.
     *
     * @return The string url, or null if the guild does not have splash.
     */
    String getSplash();

    /**
     * Get the region of this guild's voice channels.
     *
     * @return The region enumeration representing this guild's region.
     */
    Region getRegion();

    /**
     * Get the AFK timeout of this guild.
     * @see #getAfkChannel()
     *
     * @return The integer value of the timeout.
     */
    Guild.AFK_Timeout getAfkTimeout();

    /**
     * Get the AFK voice channel of this guild.
     * @see #getAfkTimeout()
     *
     * @return The channel, or null if no channel is set.
     */
    @Nullable
    IVoiceChannel getAfkChannel();

    /**
     * @see #getEmbedChannel()
     * @return True if an widget(embed) is enabled for this guild.
     */
    boolean isEmbedEnabled();

    /**
     * Get the channel of the embed widget.
     * @see #isEmbedEnabled()
     *
     * @return The channel, or null if no embed is set.
     */
    @Nullable
    ITextChannel getEmbedChannel();

    /**
     * Get the verification level of this guild.
     *
     * @return The verification level.
     */
    Guild.Verification getVerificationLevel();

    /**
     * Get the notification level of this guild.
     *
     * @return The notification level.
     */
    Guild.Notification getNotificationsLevel();

    /**
     * Get the MFA (Server Two-Factor Authentication) level of this guild.
     *
     * @return The MFA level.
     */
    Guild.MFA getMFALevel();

    /**
     * @return The owner of this guild.
     */
    IMember getOwner();

    /**
     * @return A list of users of this guild.
     */
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

    /**
     * @return A list of members of this guild.
     */
    List<IMember> getMembers();

    /**
    * Get a role by id.
    * @param id The specified id
    * @return a Role or null if no role was found.
    */
    @Nullable
    IRole getRole(String id);

    /**
     * @return The @everyone role of this guild.
     */
    @NotNull
    IRole getEveryoneRole();

    /**
     * @return A list of roles for this guild.
     */
    List<IRole> getRoles();

    /**
    * Get a guild emoji by id.
    * @param id The specified id
    * @return a GuildEmoji or null if no emoji was found.
    */
    @Nullable
    IGuildEmoji getGuildEmoji(String id);

    /**
     * @return A list of server custom emojis this guild has.
     */
    List<IGuildEmoji> getGuildEmojis();

    /**
    * Get a text channel by id.
    * @param id The specified id
    * @return a TextChannel or null if no channel was found.
    */
    @Nullable
    ITextChannel getTextChannel(String id);

    /**
     * @return A list of text channels this guild has.
     */
    List<ITextChannel> getTextChannels();

    /**
    * Get a voice channel by id.
    * @param id The specified id
    * @return a VoiceChannel or null if no channel is found.
    */
    @Nullable
    IVoiceChannel getVoiceChannel(String id);

    /**
     * @return A list of voice channels this guild has.
     */
    List<IVoiceChannel> getVoiceChannels();

    /*
        --------------------------
            Guild Enumerations
        --------------------------
     */

    /**
     * AFK Timeouts (second)
     */
    enum AFK_Timeout {
        MINUTE_1 (60),
        MINUTES_5 (300),
        MINUTES_10 (600),
        MINUTES_30 (1800),
        HOUR_1 (3600),
        UNKNOWN (-1);

        public int timeout;

        AFK_Timeout (int timeout) {
            this.timeout = timeout;
        }

        public static AFK_Timeout getByTimeout (int timeout) {
            if (Arrays.stream(values()).anyMatch(afk -> afk.timeout == timeout)) {
                return Arrays.stream(values()).filter(afk -> afk.timeout == timeout).findFirst().get();
            } else {
                return UNKNOWN;
            }
        }
    }

    /**
     * Guild Verification Level
     */
    enum Verification {
        NONE (0),
        LOW (1),
        MEDIUM (2),
        HIGH (3),
        SUPER (4),
        UNKNOWN (-1);

        public final int key;

        Verification (int key) {
            this.key = key;
        }

        public static Verification getByKey(int key) {
            for (Verification verify : values()) {
                if (verify.key == key) return verify;
            }
            return UNKNOWN;
        }

    }

    /**
     * Guild Notification Level
     */
    enum Notification {
        ALL_MESSAGE (0),
        ONLY_MENTIONS (1),
        UNKNOWN (-1);

        public final int key;

        Notification (int key) {
            this.key = key;
        }

        public static Notification getByKey(int key) {
            for (Notification notif : values()) {
                if (notif.key == key) return notif;
            }
            return UNKNOWN;
        }

    }

    /**
     * Guild MFA Level
     */
    enum MFA {
        NONE (0),
        TWO_FACTOR (1),
        UNKNOWN (-1);

        public final int key;

        MFA (int key) {
            this.key = key;
        }

        public static MFA getByKey(int key) {
            for (MFA mfa : values()) {
                if (mfa.key == key) return mfa;
            }
            return UNKNOWN;
        }

    }
}
