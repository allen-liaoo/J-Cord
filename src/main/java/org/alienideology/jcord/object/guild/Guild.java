package org.alienideology.jcord.object.guild;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.alienideology.jcord.Identity;
import org.alienideology.jcord.object.DiscordObject;
import org.alienideology.jcord.object.Region;
import org.alienideology.jcord.object.SnowFlake;
import org.alienideology.jcord.object.User;
import org.alienideology.jcord.object.channel.GuildChannel;
import org.alienideology.jcord.object.channel.TextChannel;
import org.alienideology.jcord.object.channel.VoiceChannel;

import java.util.*;

/**
 * Guild - A collection of users and channels, often referred to in the UI as a server.
 * @author AlienIdeology
 */
public class Guild extends DiscordObject implements SnowFlake {

    private final String id;
    private boolean isAvailable = false;

    private String name;

    private final String icon;
    private final String splash;

    private Member owner;
    private Region region;

    private AFK_Timeout afk_timeout;
    private VoiceChannel afk_channel;

    private boolean embed_enabled;
    private TextChannel embed_channel;

    private Verification verification_level;
    private Notification notifications_level;
    private MFA mfa_level;

    private List<Role> roles;
//    private List<Emote> emojis;

    private final List<Member> members;
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
        roles = new ArrayList<>();
//        emojis = new ArrayList<Emote>();
        members = new ArrayList<>();
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

    @Nullable
    public VoiceChannel getAfkChannel() {
        return afk_channel;
    }

    @Nullable
    public TextChannel getEmbedChannel() {
        return embed_channel;
    }

    public Member getOwner() {
        return owner;
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        for (Member mem : members) {
            users.add(mem.getUser());
        }
        return Collections.unmodifiableList(users);
    }

    /**
     * Get a member by id
     * @param id The specified id
     * @return a Member or null if no member is found.
     */
    @Nullable
    public Member getMember(String id) {
        for (Member member : members) {
            if (member.getId().equals(id))
                return member;
        }
        return null;
    }

    public List<Member> getMembers() {
        return Collections.unmodifiableList(members);
    }

    /**
     * Get a role by id.
     * @param id The specified id
     * @return a Role or null if no role is found.
     */
    @Nullable
    public Role getRole(String id) {
        for (Role role : roles) {
            if (role.getId().equals(id))
                return role;
        }
        return null;
    }

    @NotNull
    public Role getEveryoneRole() {
        for (Role role : roles) {
            if (role.isEveryone())
                return role;
        }
        return null;
    }

    public List<Role> getRoles() {
        return Collections.unmodifiableList(roles);
    }

    /**
     * Get a text channel by id.
     * @param id The specified id
     * @return a TextChannel or null if no channel is found.
     */
    @Nullable
    public TextChannel getTextChannel(String id) {
        for (TextChannel tc : textChannels) {
            if (tc.getId().equals(id)) {
                return tc;
            }
        }
        return null;
    }

    public List<TextChannel> getTextChannels() {
        return Collections.unmodifiableList(textChannels);
    }

    /**
     * Get a voice channel by id.
     * @param id The specified id
     * @return a VoiceChannel or null if no channel is found.
     */
    @Nullable
    public VoiceChannel getVoiceChannel(String id) {
        for (VoiceChannel vc : voiceChannels) {
            if (vc.getId().equals(id)) {
                return vc;
            }
        }
        return null;
    }

    public List<VoiceChannel> getVoiceChannels() {
        return Collections.unmodifiableList(voiceChannels);
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
     * [API Use Only]
     * Add Members.
     * @param members the varargs of members to be added.
     * @return this guild for chaining.
     */
    public Guild addMember (Member... members) {
        this.members.addAll(Arrays.asList(members));
        return this;
    }

    /**
     * [API Use Only]
     * Add roles.
     * @param roles the varargs of roles to be added.
     * @return this guild for chaining.
     */
    public Guild addRole (Role... roles) {
        this.roles.addAll(Arrays.asList(roles));
        return this;
    }

    /**
     * [API Use Only]
     * Set the owner of this guild.
     * @param id the id of the owner.
     * @return this guild for chaining.
     */
    public Guild setOwner (String id) {
        for (Member mem : members) {
            if (mem.getId().equals(id)) {
                this.owner = mem;
                break;
            }
        }
        return this;
    }

    /**
     * [API Use Only]
     * Set the channels settings for this guild.
     * @param afk the afk voice channel
     * @param embed the embed text channel
     * @return this guild for chaining.
     */
    public Guild setChannels (String afk, String embed) {
        this.afk_channel = getVoiceChannel(afk);
        this.embed_channel = getTextChannel(embed);
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
