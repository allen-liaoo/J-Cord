package org.alienideology.jcord.internal.object.client.app;

import org.alienideology.jcord.handle.Icon;
import org.alienideology.jcord.handle.client.app.IApplication;
import org.alienideology.jcord.handle.managers.IApplicationManager;
import org.alienideology.jcord.handle.modifiers.IApplicationModifier;
import org.alienideology.jcord.internal.object.Jsonable;
import org.alienideology.jcord.internal.object.client.Client;
import org.alienideology.jcord.internal.object.client.ClientObject;
import org.alienideology.jcord.internal.object.managers.ApplicationManager;
import org.alienideology.jcord.internal.object.modifiers.ApplicationModifier;
import org.json.JSONObject;

import java.util.List;

/**
 * @author AlienIdeology
 */
// TODO: Flags and RpcApplicationState
public final class Application extends ClientObject implements IApplication, Jsonable {

    private final String id;
    private String secret;

    private String name;
    private String icon;
    private String description;

    private List<String> redirectUris;
    private BotUser bot;

    private boolean isPublicBot;
    private boolean requireCodeGrant;

    private final ApplicationManager manager;
    private final ApplicationModifier modifier;

    public Application(Client client, String id) {
        super(client);
        this.id = id;
        this.manager = new ApplicationManager(this);
        this.modifier = new ApplicationModifier(this);
    }

    @Override
    public JSONObject toJson() {
        return new JSONObject()
                .put("name", name)
                // icon is either raw data from Icon.getData() casted to string
                // or Icon.DEFAULT_ICON
                .put("icon", icon == null ? Icon.DEFAULT_ICON.getData() : icon)
                .put("description", description);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public IApplicationManager getManager() {
        return manager;
    }

    @Override
    public IApplicationModifier getModifier() {
        return modifier;
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

    //-----------------Internal-----------------

    public Application setSecret(String secret) {
        this.secret = secret;
        return this;
    }

    public Application setName(String name) {
        this.name = name;
        return this;
    }

    public Application setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public Application setDescription(String description) {
        this.description = description;
        return this;
    }

    public Application setRedirectUris(List<String> redirectUris) {
        this.redirectUris = redirectUris;
        return this;
    }

    public Application setBot(BotUser bot) {
        this.bot = bot;
        return this;
    }

    public Application setPublicBot(boolean publicBot) {
        isPublicBot = publicBot;
        return this;
    }

    public Application setRequireCodeGrant(boolean requireCodeGrant) {
        this.requireCodeGrant = requireCodeGrant;
        return this;
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
