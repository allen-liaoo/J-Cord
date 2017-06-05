package org.alienideology.jcord.object.guild;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.object.DiscordObject;
import org.alienideology.jcord.object.Region;
import org.alienideology.jcord.object.SnowFlake;
import org.alienideology.jcord.object.User;
import org.alienideology.jcord.object.channel.GuildChannel;
import org.alienideology.jcord.object.channel.TextChannel;
import org.alienideology.jcord.object.channel.VoiceChannel;

import javax.xml.soap.Text;
import java.util.*;

/**
 * Guild - A collection of users and channels, often referred to in the UI as a server.
 * @author AlienIdeology
 */
public class Guild extends DiscordObject implements SnowFlake {

    private final String id;
    private boolean isAvailable = false;

    private final String name;

    private final String icon;
    private final String splash;

//    private final Member owner
    private final Region region;

    private final AFK_Timeout afk_timeout;
//    private final VoiceChannel afk_channel;

    private final boolean embed_enabled;
//    //private final Channel embed_channel;
//
    private final Verification verification_level;
    private final Notification notifications_level;
    private final MFA mfa_level;

//    private List<Role> roles;
//    private List<Emote> emojis;

    private final List<TextChannel> textChannels;
    private final List<VoiceChannel> voiceChannels;

    /**
     * Unavailable Guild
     * @param identity The Identity this guild belongs to.
     * @param id The ID of this guild
     */
    public Guild (Identity identity, String id) {
        this(identity, id, null, null, null, null,
                -1, false, -1, -1, -1);
    }


    /**
     * Available Guild
     * @param identity The Identity this guild belongs to.
     * @param id The ID of this guild
     * @param icon The icon of this guild
     * @param splash The splash hash of this guild
     * @param region The string value of the region
     * @param afk_timeout The afk timeout
     * @param embed_enabled Is embed enabled (i.e. widget)
     * @param verification_level Level of verification
     * @param notification_level Level of notification
     * @param mfa_level Required MFA level
     */
    // TODO: Add Owner Field
    public Guild (Identity identity, String id, String name, String icon, String splash, String region,
                  int afk_timeout, boolean embed_enabled, int verification_level, int notification_level, int mfa_level) {
        super(identity);
        this.id = id;
        isAvailable = true;
        this.name = name;
        this.icon = icon;
        this.splash = splash;
//         this.owner = owner;
        this.region = Region.getByKey(region);
        this.afk_timeout = AFK_Timeout.getByTimeout(afk_timeout);
//        this.afk_channel = null;
        this.embed_enabled = embed_enabled;
//        embed_channel = null;
        this.verification_level = Verification.getByKey(verification_level);
        this.notifications_level = Notification.getByKey(notification_level);
        this.mfa_level = MFA.getByKey(mfa_level);
//        roles = new ArrayList<Role>();
//        emojis = new ArrayList<Emote>();
        textChannels = new ArrayList<>();
        voiceChannels = new ArrayList<>();
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public String getSplash() {
        return splash;
    }

    public Region getRegion() {
        return region;
    }

    public AFK_Timeout getAfkTimeout() {
        return afk_timeout;
    }

    public boolean isEmbedEnabled() {
        return embed_enabled;
    }

    public Verification getVerificationLevel() {
        return verification_level;
    }

    public Notification getNotificationsLevel() {
        return notifications_level;
    }

    public MFA getMFALevel() {
        return mfa_level;
    }

    public List<TextChannel> getTextChannels() {
        return Collections.unmodifiableList(textChannels);
    }

    public List<VoiceChannel> getVoiceChannels() {
        return Collections.unmodifiableList(voiceChannels);
    }

    /**
     * Get a text channel by id.
     * @param id The specified id
     * @return a TextChannel or null if no channel is found.
     */
    public TextChannel getTextChannel(String id) {
        for (TextChannel tc : textChannels) {
            if (tc.getId().equals(id)) {
                return tc;
            }
        }
        return null;
    }

    /**
     * Get a voice channel by id.
     * @param id The specified id
     * @return a VoiceChannel or null if no channel is found.
     */
    public VoiceChannel getVoiceChannel(String id) {
        for (VoiceChannel vc : voiceChannels) {
            if (vc.getId().equals(id)) {
                return vc;
            }
        }
        return null;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Guild) && Objects.equals(this.id, ((Guild) obj).getId());
    }

    @Override
    public String toString() {
        return "ID: "+id+"\tName: "+name;
    }

    /**
     * [API Use Only]
     * Add Text or Voice channels.
     * @param channels the varargs of channels to be added.
     * @return this guild for chaining.
     */
    public Guild addGuildChannel (GuildChannel... channels) {
        for (GuildChannel channel : channels) {
            if (channel instanceof TextChannel) {
                textChannels.add((TextChannel) channel);
            } else if (channel instanceof VoiceChannel) {
                voiceChannels.add((VoiceChannel) channel);
            }
        }
        return this;
    }

    /**
     * AFK Timeouts (second)
     */
    public enum AFK_Timeout {
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
    public enum Verification {
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
    public enum Notification {
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
    public enum MFA {
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
