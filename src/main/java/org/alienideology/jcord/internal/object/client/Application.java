package org.alienideology.jcord.internal.object.client;

import org.alienideology.jcord.handle.client.IApplication;

import java.util.List;

/**
 * @author AlienIdeology
 */
// TODO: Flags and RpcApplicationState
public class Application extends ClientObject implements IApplication {

    private final String id;
    private final String secret;

    private final String name;
    private final String icon;
    private final String description;

    private final List<String> redirectUris;
    private BotUser bot;

    private final boolean isPublicBot;
    private final boolean requireCodeGrant;

    public Application(Client client, String id, String secret, String name, String icon, String description, List<String> redirectUris) {
        this(client, id, secret, name, icon, description, redirectUris, null, false, false);
    }

    public Application(Client client, String id, String secret, String name, String icon, String description,
                       List<String> redirectUris, BotUser bot, boolean isPublicBot, boolean requireCodeGrant) {
        super(client);
        this.id = id;
        this.secret = secret;
        this.name = name;
        this.icon = icon;
        this.description = description;
        this.redirectUris = redirectUris;
        this.bot = bot;
        this.isPublicBot = isPublicBot;
        this.requireCodeGrant = requireCodeGrant;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getSecret() {
        return secret;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getIconHash() {
        return icon;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public List<String> getRedirectUris() {
        return redirectUris;
    }

    @Override
    public IBotUser getBot() {
        return bot;
    }

    @Override
    public boolean hasBot() {
        return bot != null;
    }

    @Override
    public boolean isPublicBot() {
        return isPublicBot;
    }

    @Override
    public boolean requireCodeGrant() {
        return requireCodeGrant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Application)) return false;
        if (!super.equals(o)) return false;

        Application that = (Application) o;

        if (!id.equals(that.id)) return false;
        return secret.equals(that.secret);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + secret.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Application{" +
                "id='" + id + '\'' +
                ", secret='" + secret + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", hasBot=" + hasBot() +
                '}';
    }

    public static class BotUser implements IBotUser {
        private final String id;
        private final String token;

        private final String name;
        private final String discriminator;
        private final String avatar;

        public BotUser(String id, String token, String name, String discriminator, String avatar) {
            this.id = id;
            this.token = token;
            this.name = name;
            this.discriminator = discriminator;
            this.avatar = avatar;
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public String getToken() {
            return token;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getDiscriminator() {
            return discriminator;
        }

        @Override
        public String getAvatarHash() {
            return avatar;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof BotUser)) return false;

            BotUser bot = (BotUser) o;

            if (!id.equals(bot.id)) return false;
            if (!token.equals(bot.token)) return false;
            return discriminator.equals(bot.discriminator);
        }

        @Override
        public int hashCode() {
            int result = id.hashCode();
            result = 31 * result + token.hashCode();
            result = 31 * result + discriminator.hashCode();
            return result;
        }


        @Override
        public String toString() {
            return "IBotUser{" +
                    "id='" + id + '\'' +
                    ", token='" + token + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }

    }

}
