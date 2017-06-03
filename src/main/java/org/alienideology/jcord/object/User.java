package org.alienideology.jcord.object;

import org.alienideology.jcord.Identity;
import org.json.JSONObject;

/**
 * User - A base entity, can be a member of guild/private channel, and bot/normal.
 * @author AlienIdeology
 */
public class User extends DiscordObject implements SnowFlake {

    private final String id;

    private String name;
    private String discriminator;
    private String avatar;
    private String email;

    private final boolean isBot;
    private boolean isVerified;
    private boolean MFAEnabled;

    public User(Identity identity, JSONObject json) {
        super(identity);

        id = json.getString("id");

        name = json.getString("username");
        discriminator = json.getString("discriminator");
        avatar = json.getString("avatar");
        email = json.isNull("email") ? null : json.getString("email");

        isBot = json.getBoolean("bot");
        isVerified = json.getBoolean("verified");
        MFAEnabled = json.getBoolean("mfa_enabled");
    }

    @Override
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDiscriminator() {
        return discriminator;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getEmail() {
        return email;
    }

    public boolean isBot() {
        return isBot;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public boolean isMFAEnabled() {
        return MFAEnabled;
    }

}
