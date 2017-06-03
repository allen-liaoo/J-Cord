package org.alienideology.jcord.object;

import org.alienideology.jcord.Identity;
import org.json.JSONObject;
import java.util.Arrays;

/**
 * Guild - A collection of users and channels, often referred to in the UI as a server.
 * @author AlienIdeology
 */
public class Guild extends DiscordObject implements SnowFlake {

    private final String id;
    private boolean isAvailable;

    private final String name;

    private final String icon;
    private final String splash;

//    private final Member owner;
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

    public Guild (Identity identity, JSONObject json) {
        super(identity);

        id = json.getString("id");

        if (json.has("unavailable") && json.getBoolean("unavailable")) {

            isAvailable = false;

            name = null;

            icon = null;
            splash = null;

//            owner = null;
            region = null;
//
            afk_timeout = AFK_Timeout.UNKNOWN;
//            afk_channel = null;;
//
            embed_enabled = false;
//            embed_channel = null;
//
            verification_level = null;
            notifications_level = null;
            mfa_level = null;

//            roles = new ArrayList<Role>();
//            emojis = new ArrayList<Emote>();

        } else {
            isAvailable = true;

            name = json.getString("name");

            icon = json.getString("icon");
            splash = json.isNull("splash") ? null : json.getString("splash");

//            owner = identity.getMember(json.getString("owner_id"));
            region = Region.getByKey(json.getString("region"));

            afk_timeout = AFK_Timeout.getByTimeout(json.getInt("afk_timeout"));
//            afk_channel = json.isNull("afk_channel_id") ? null : identity.getVoiceChannel(json.getString("afk_channel_id"));

            embed_enabled = json.has("embed_enabled") && json.getBoolean("embed_enabled");
//            embed_channel = json.has("embed_channel_id") ? : identity.getChannel(json.getString("embed_channel_id")) : null;

            verification_level = Verification.getByKey(json.getInt("verification_level"));
            notifications_level = Notification.getByKey(json.getInt("default_message_notifications"));
            mfa_level = MFA.getByKey(json.getInt("mfa_level"));

//            roles = new ArrayList<Role>();
//            json.getJSONArray("roles").forEach(role -> roles.add(new Role(identity, role.toString())));
//            emojis = new ArrayList<Emote>();
//            json.getJSONArray("emojis").forEach(emoji -> emoji.add(new Role(identity, emoji.toString())));
        }
    }

    @Override
    public String getId() {
        return id;
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

    @Override
    public String toString() {
        return "Name: "+name+"\tID: "+id;
    }

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
            if (Arrays.stream(values()).anyMatch(vf -> vf.key == key)) {
                return Arrays.stream(values()).filter(vf -> vf.key == key).findFirst().get();
            } else {
                return UNKNOWN;
            }
        }

    }

    enum Notification {
        ALL_MESSAGE (0),
        ONLY_MENTIONS (1),
        UNKNOWN (-1);

        public final int key;

        Notification (int key) {
            this.key = key;
        }

        public static Notification getByKey(int key) {
            if (Arrays.stream(values()).anyMatch(nf -> nf.key == key)) {
                return Arrays.stream(values()).filter(nf -> nf.key == key).findFirst().get();
            } else {
                return UNKNOWN;
            }
        }

    }

    enum MFA {
        NONE (0),
        TWO_FACTOR (1),
        UNKNOWN (-1);

        public final int key;

        MFA (int key) {
            this.key = key;
        }

        public static MFA getByKey(int key) {
            if (Arrays.stream(values()).anyMatch(mfa -> mfa.key == key)) {
                return Arrays.stream(values()).filter(mfa -> mfa.key == key).findFirst().get();
            } else {
                return UNKNOWN;
            }
        }

    }
}
