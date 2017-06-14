package org.alienideology.jcord.object.user;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.mashape.unirest.request.body.RequestBodyEntity;
import org.alienideology.jcord.Identity;
import org.alienideology.jcord.Internal;
import org.alienideology.jcord.gateway.HttpPath;
import org.alienideology.jcord.gateway.Requester;
import org.alienideology.jcord.object.DiscordObject;
import org.alienideology.jcord.object.Mention;
import org.alienideology.jcord.object.ObjectBuilder;
import org.alienideology.jcord.object.SnowFlake;
import org.alienideology.jcord.object.channel.PrivateChannel;
import org.json.HTTP;
import org.json.JSONObject;

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
        setAvatar();
        this.email = email;
        this.isBot = isBot;
        this.isWebHook = isWebHook;
        this.isVerified = isVerified;
        this.MFAEnabled = MFAEnabled;
    }

    /**
     * Get or open a PrivateChannel with this user
     * @return The PrivateChannel with this user
     */
    public PrivateChannel getPrivateChannel() {
        PrivateChannel dm = identity.getPrivateChannel(id);

        // Private Channel has not exist
        if (dm == null) {
            try {
                JSONObject json = new Requester(identity, HttpPath.User.CREATE_DM).request()
                        .updateRequestWithBody(body -> body.header("Content-Type", "application/json")
                                .body(new JSONObject().put("recipient_id", id))).getAsJSONObject();
                dm = new ObjectBuilder(identity).buildPrivateChannel(json);
            } catch (RuntimeException ignored) { }
        }
        return dm;
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

    public boolean isSelf() {
        return identity.getSelf().getId().equals(id);
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
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ID: "+id+"\tName: "+name;
    }

    @Internal
    private void setAvatar() {
        this.avatar = avatar == null ?
                String.format(HttpPath.EndPoint.DEFAULT_AVATAR, String.valueOf(Integer.parseInt(discriminator) % 5)) :
                String.format(HttpPath.EndPoint.AVATAR, id, avatar, (avatar.startsWith("a_") ? ".gif" : ".png"));
    }

}
