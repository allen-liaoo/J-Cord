package org.alienideology.jcord.internal.object.user;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.channel.IPrivateChannel;
import org.alienideology.jcord.handle.user.IPresence;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.handle.user.OnlineStatus;
import org.alienideology.jcord.internal.object.DiscordObject;
import org.alienideology.jcord.internal.object.ObjectBuilder;
import org.alienideology.jcord.internal.object.channel.PrivateChannel;
import org.alienideology.jcord.internal.rest.HttpPath;
import org.alienideology.jcord.internal.rest.Requester;
import org.json.JSONObject;

import java.util.Objects;

/**
 * @author AlienIdeology
 */
// TODO: SelfUser for email and isVerified?
public class User extends DiscordObject implements IUser {

    protected final String id;

    private Presence presence;

    protected String name;
    protected String discriminator;
    private String avatar;
    private String email;

    private boolean isBot;
    private boolean isWebHook;
    private boolean isVerified;
    private boolean isMFAEnabled;

    public User (Identity identity, String id) {
        super(identity);
        this.id = id;
        this.presence = new Presence(identity, this)
                .setStatus(OnlineStatus.ONLINE);
    }

    @Override
    public PrivateChannel getPrivateChannel() {
        PrivateChannel dm = (PrivateChannel) identity.getPrivateChannelByUserId(id);

        // Private Channel has not exist
        if (dm == null) {
            JSONObject json = new Requester(identity, HttpPath.User.CREATE_DM).request()
                    .updateRequestWithBody(body -> body.header("Content-Type", "application/json")
                            .body(new JSONObject().put("recipient_id", id))).getAsJSONObject();

            dm = new ObjectBuilder(identity).buildPrivateChannel(json);
            identity.addPrivateChannel(dm);
        }
        return dm;
    }

    @Override
    public void closePrivateChannel() {
        IPrivateChannel channel = identity.getPrivateChannelByUserId(id);
        if (channel == null) return;
        new Requester(identity, HttpPath.Channel.DELETE_CHANNEL).request(channel.getId())
                .performRequest();
        identity.removePrivateChannel(id);
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
    public String getEmail() {
        return email;
    }

    @Override
    public IPresence getPresence() {
        return presence;
    }

    @Override
    public boolean isBot() {
        return isBot;
    }

    @Override
    public boolean isWebHook() {
        return isWebHook;
    }

    @Override
    public boolean isVerified() {
        return isVerified;
    }

    @Override
    public boolean isMFAEnabled() {
        return isMFAEnabled;
    }

    @Override
    public String getId() {
        return id;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public User setDiscriminator(String discriminator) {
        this.discriminator = discriminator;
        return this;
    }

    public User setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public User setBot(boolean bot) {
        isBot = bot;
        return this;
    }

    public User setWebHook(boolean webHook) {
        isWebHook = webHook;
        return this;
    }

    public User setVerified(boolean verified) {
        isVerified = verified;
        return this;
    }

    public User setMFAEnabled(boolean MFAEnabled) {
        this.isMFAEnabled = MFAEnabled;
        return this;
    }

    public User setPresence(Presence presence) {
        this.presence = presence;
        return this;
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
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", discriminator='" + discriminator + '\'' +
                '}';
    }

}
