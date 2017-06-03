package org.alienideology.jcord.object;

import org.alienideology.jcord.Identity;
import org.json.JSONObject;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Guild, A collection of users and channels, often referred to in the UI as a server.
 * @author AlienIdeology
 */
public class Guild {

    private final Identity identity;

    private final String id;
    private final String name;

    private final String icon;
    private final String splash;

    //private final Member owner;
    private final Region region;

    //private final VoiceChannel afk_channel;
    private final int afk_timeout;

    private final boolean embed_enabled;
    //private final Channel embed_channel;

    private final Verification verification_level;
    private final Notification notifications_level;
    private final MFA mfa_level;

    //private List<Role> roles;
    //private List<Emote> emojis;

    public Guild (Identity identity, JSONObject json) {
        this.identity = identity;

        id = json.getString("id");
        name = json.getString("name");

        icon = json.getString("icon");
        splash = json.getString("splash");

        //owner = identity.getMember(json.getString("owner_id"));
        region = Region.getByKey(json.getString("region"));

        afk_timeout = json.getInt("afk_timeout");
        //afk_channel = identity.getVoiceChannel(json.getString("afk_channel_id"));

        embed_enabled = json.getBoolean("embed_enabled");
        //embed_channel = identity.getChannel(json.getString("embed_channel_id"));

        verification_level = Verification.getByKey(json.getInt("verification_level"));
        notifications_level = Notification.getByKey(json.getInt("default_message_notifications"));
        mfa_level = MFA.getByKey(json.getInt("mfa_level"));

        //roles = new ArrayList<Role>();
        //json.getJSONArray("roles").forEach(role -> roles.add(new Role(identity, role.toString())));
        //emojis = new ArrayList<Emote>();
        //json.getJSONArray("emojis").forEach(emoji -> emoji.add(new Role(identity, emoji.toString())));
    }

    public Identity getIdentity() {
        return identity;
    }

    public String getId() {
        return id;
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

    public int getAfkTimeout() {
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
            return Arrays.stream(values()).filter(vf -> vf.key == key).findFirst().get();
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
            return Arrays.stream(values()).filter(nf -> nf.key == key).findFirst().get();
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
            return Arrays.stream(values()).filter(mfa -> mfa.key == key).findFirst().get();
        }
    }
}
