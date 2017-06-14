package org.alienideology.jcord.internal.object;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.internal.Identity;
import org.alienideology.jcord.internal.Internal;
import org.alienideology.jcord.internal.gateway.HttpPath;
import org.alienideology.jcord.handle.channel.IGuildChannel;
import org.alienideology.jcord.internal.object.guild.GuildEmoji;
import org.alienideology.jcord.internal.object.guild.Member;
import org.alienideology.jcord.internal.object.guild.Role;
import org.alienideology.jcord.internal.object.user.User;
import org.alienideology.jcord.internal.object.channel.TextChannel;
import org.alienideology.jcord.internal.object.channel.VoiceChannel;

import java.util.*;

/**
 * @author AlienIdeology
 */
public class Guild extends DiscordObject implements IGuild {

    private final String id;
    private boolean isAvailable = false;

    private String name;

    private String icon;
    private String splash;

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
    private List<GuildEmoji> emojis;

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
        setIcon();
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
        emojis = new ArrayList<>();
        members = new ArrayList<>();
        textChannels = new ArrayList<>();
        voiceChannels = new ArrayList<>();
    }

    @Override
    public boolean isAvailable() {
        return isAvailable;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getIcon() {
        return icon;
    }

    @Override
    public String getSplash() {
        return splash;
    }

    @Override
    public Region getRegion() {
        return region;
    }

    @Override
    public AFK_Timeout getAfkTimeout() {
        return afk_timeout;
    }

    @Override
    public boolean isEmbedEnabled() {
        return embed_enabled;
    }

    @Override
    public Verification getVerificationLevel() {
        return verification_level;
    }

    @Override
    public Notification getNotificationsLevel() {
        return notifications_level;
    }

    @Override
    public MFA getMFALevel() {
        return mfa_level;
    }

    @Override
    @Nullable
    public VoiceChannel getAfkChannel() {
        return afk_channel;
    }

    @Override
    @Nullable
    public TextChannel getEmbedChannel() {
        return embed_channel;
    }

    @Override
    public Member getOwner() {
        return owner;
    }

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        for (Member mem : members) {
            users.add(mem.getUser());
        }
        return Collections.unmodifiableList(users);
    }

    @Override
    @NotNull
    public Member getSelfMember() {
        for (Member member : members) {
            if (member.getUser().isSelf())
                return member;
        }
        return null;
    }

    @Override
    @Nullable
    public Member getMember(String id) {
        for (Member member : members) {
            if (member.getId().equals(id))
                return member;
        }
        return null;
    }

    @Override
    public List<Member> getMembers() {
        return Collections.unmodifiableList(members);
    }

    @Override
    @Nullable
    public Role getRole(String id) {
        for (Role role : roles) {
            if (role.getId().equals(id))
                return role;
        }
        return null;
    }

    @Override
    @NotNull
    public Role getEveryoneRole() {
        for (Role role : roles) {
            if (role.isEveryone())
                return role;
        }
        return null;
    }

    @Override
    public List<Role> getRoles() {
        return Collections.unmodifiableList(roles);
    }

    @Override
    @Nullable
    public GuildEmoji getGuildEmoji(String id) {
        for (GuildEmoji emoji : emojis) {
            if (emoji.getId().equals(id)) {
                return emoji;
            }
        }
        return null;
    }

    @Override
    public List<GuildEmoji> getGuildEmojis() {
        return emojis;
    }

    @Override
    @Nullable
    public TextChannel getTextChannel(String id) {
        for (TextChannel tc : textChannels) {
            if (tc.getId().equals(id)) {
                return tc;
            }
        }
        return null;
    }

    @Override
    public List<TextChannel> getTextChannels() {
        return Collections.unmodifiableList(textChannels);
    }

    @Override
    @Nullable
    public VoiceChannel getVoiceChannel(String id) {
        for (VoiceChannel vc : voiceChannels) {
            if (vc.getId().equals(id)) {
                return vc;
            }
        }
        return null;
    }

    @Override
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
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "ID: "+id+"\tName: "+name;
    }

    /*
        --------------------------
            Guild Enumerations
        --------------------------
     */

    // TODO: Move enums to IGuild

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

    /*
        ------------------------
            Internal Methods
        ------------------------
     */

    /**
     * [API Use Only]
     * Add Text or Voice channels.
     * @param channels the varargs of channels to be added.
     * @return this guild for chaining.
     */
    @Internal
    Guild addGuildChannel (IGuildChannel... channels) {
        for (IGuildChannel channel : channels) {
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
    @Internal
    Guild addMember (Member... members) {
        this.members.addAll(Arrays.asList(members));
        return this;
    }

    /**
     * [API Use Only]
     * Add roles.
     * @param roles the varargs of roles to be added.
     * @return this guild for chaining.
     */
    @Internal
    Guild addRole (Role... roles) {
        this.roles.addAll(Arrays.asList(roles));
        return this;
    }

    /**
     * [API Use Only]
     * Add server emojis.
     * @param emojis the varargs of emojis to be added.
     * @return this guild for chaining.
     */
    @Internal
    Guild addGuildEmoji (GuildEmoji... emojis) {
        this.emojis.addAll(Arrays.asList(emojis));
        return this;
    }

    /**
     * [API Use Only]
     * Set the owner of this guild.
     * @param id the id of the owner.
     * @return this guild for chaining.
     */
    @Internal
    Guild setOwner (String id) {
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
    @Internal
    Guild setChannels (String afk, String embed) {
        this.afk_channel = getVoiceChannel(afk);
        this.embed_channel = getTextChannel(embed);
        return this;
    }

    private void setIcon() {
        this.icon = icon == null ?
                null : String.format(HttpPath.EndPoint.GUILD_ICON, id, icon);
    }

}
