package org.alienideology.jcord.object;

import org.alienideology.jcord.Identity;

import java.util.Objects;

/**
 * User - A base entity, can be a member of guild/private channel, and bot/human.
 * @author AlienIdeology
 */
public class User extends DiscordObject implements SnowFlake, Mention {

    private final String id;

    private String name;
    private String discriminator;
    private String avatar;
    private String email;

    private final boolean isBot;
    private final boolean isWebHook;
    private boolean isVerified;
    private boolean MFAEnabled;

    public User (Identity identity, String id, String name, String discriminator, String avatar, String email,
                 boolean isBot, boolean isWebHook, boolean isVerified, boolean MFAEnabled) {
        super(identity);
        this.id = id;
        this.name = name;
        this.discriminator = discriminator;
        this.avatar = avatar;
        this.email = email;
        this.isBot = isBot;
        this.isWebHook = isWebHook;
        this.isVerified = isVerified;
        this.MFAEnabled = MFAEnabled;
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

    public boolean isWebHook() {
        return isWebHook;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public boolean isMFAEnabled() {
        return MFAEnabled;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String mention() {
        return "<@"+id+">";
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof User) && Objects.equals(this.id, ((User) obj).getId());
    }

    @Override
    public String toString() {
        return "ID: "+id+"\tName: "+name;
    }

}
