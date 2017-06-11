package org.alienideology.jcord;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import com.neovisionaries.ws.client.*;
import com.sun.istack.internal.Nullable;
import org.alienideology.jcord.command.CommandFramework;
import org.alienideology.jcord.event.DispatcherAdaptor;
import org.alienideology.jcord.exception.ErrorResponseException;
import org.alienideology.jcord.gateway.ErrorResponse;
import org.alienideology.jcord.gateway.GatewayAdaptor;
import org.alienideology.jcord.gateway.HttpPath;
import org.alienideology.jcord.object.channel.MessageChannel;
import org.alienideology.jcord.object.channel.PrivateChannel;
import org.alienideology.jcord.object.channel.TextChannel;
import org.alienideology.jcord.object.channel.VoiceChannel;
import org.alienideology.jcord.object.Guild;
import org.alienideology.jcord.object.guild.Role;
import org.alienideology.jcord.object.user.User;
import org.apache.commons.logging.impl.SimpleLog;
import org.json.JSONException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Identity - The identity of a bot (without shards), a shard, or a human (client)
 * @author AlienIdeology
 */
public class Identity {

    public SimpleLog LOG = new SimpleLog("Identity");

    private IdentityType type;
    private String token;

    private WebSocketFactory wsFactory;
    private WebSocket socket;
    public Connection CONNECTION = Connection.OFFLINE;

    private List<DispatcherAdaptor> listeners = new ArrayList<>();
    private List<CommandFramework> frameworks = new ArrayList<>();

    private User self;
    private List<User> users = new ArrayList<>();
    private List<Guild> guilds = new ArrayList<>();
    private List<TextChannel> textChannels = new ArrayList<>();
    private List<VoiceChannel> voiceChannels = new ArrayList<>();
    private List<PrivateChannel> privateChannels = new ArrayList<>();

    public Identity (IdentityType type,  WebSocketFactory wsFactory) {
        this.type = type;
        this.wsFactory = wsFactory;
    }

    public Identity revive() throws IOException {
        logout();
        login(token);
        return this;
    }

    public List<DispatcherAdaptor> getDispatchers () {
        return listeners;
    }

    Identity addCommandFrameworks(CommandFramework... frameworks) {
        this.frameworks.addAll(Arrays.asList(frameworks));
        return this;
    }

    public List<CommandFramework> getFrameworks() {
        return frameworks;
    }

    public String getToken () {
        return token;
    }

    public User getSelf() {
        return self;
    }

    @Nullable
    public User getUser(String id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    public List<User> getUsers() {
        return users;
    }

    @Nullable
    public Guild getGuild(String id) {
        for (Guild guild : guilds) {
            if (guild.getId().equals(id)) {
                return guild;
            }
        }
        return null;
    }

    public List<Guild> getGuilds() {
        return Collections.unmodifiableList(guilds);
    }

    @Nullable
    public Role getRole(String id) {
        for (Guild guild : guilds) {
            Role role = guild.getRole(id);
            if (role != null) return role;
        }
        return null;
    }

    public List<Role> getAllRoles() {
        List<Role> roles = new ArrayList<>();
        for (Guild guild : guilds) {
            roles.addAll(guild.getRoles());
        }
        return Collections.unmodifiableList(roles);
    }

    @Nullable
    public MessageChannel getMessageChannel(String id) {
        List<MessageChannel> channels = getMessageChannels();
        for (MessageChannel channel : channels) {
            if (channel.getId().equals(id))
                return channel;
        }
        return null;
    }

    public List<MessageChannel> getMessageChannels() {
        List<MessageChannel> channels = new ArrayList<>(textChannels);
        channels.addAll(privateChannels);
        return channels;
    }

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
        return textChannels;
    }

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
        return voiceChannels;
    }

    @Nullable
    public PrivateChannel getPrivateChannel(String id) {
        for (PrivateChannel dm : privateChannels) {
            if (dm.getRecipient().getId().equals(id)) {
                return dm;
            }
        }
        return null;
    }

    public List<PrivateChannel> getPrivateChannels() {
        return privateChannels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Identity)) return false;

        Identity identity = (Identity) o;

        if (type != identity.type) return false;
        if (!token.equals(identity.token)) return false;
        return self.equals(identity.self);
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + token.hashCode();
        result = 31 * result + self.hashCode();
        return result;
    }

    /*
        ------------------------
            Internal Methods
        ------------------------
     */

    @Internal
    Identity login (String token) throws ErrorResponseException, IllegalArgumentException, IOException {
        if (type == IdentityType.BOT && !token.startsWith("Bot ")) {
            this.token = "Bot " + token;
        } else {
            this.token = token;
        }

        try {
            HttpRequest request = Unirest.get(HttpPath.Gateway.GET_BOT.getPath()).header("Authorization", this.token);
            String uri = request.asJson().getBody().getObject().getString("url") + "?encoding=json&v=" + GatewayAdaptor.GATEWAY_VERSION;

            URI url = new URI(uri);

            socket = wsFactory.createSocket(url);
            socket.addListener(new GatewayAdaptor(this, socket)).connect();
        } catch (UnirestException | JSONException ne) {
            throw new ErrorResponseException(ErrorResponse.INVALID_AUTHENTICATION_TOKEN);
        } catch (URISyntaxException urise) {
            throw new ConnectException("Discord fail to provide a valid URI!");
        } catch (IOException iow) {
            throw new IOException("Fail to create WebSocket!");
        } catch (WebSocketException wse) {
            throw new ConnectException("Fail to connect to the Discord server!");
        }

        return this;
    }

    @Internal
    Identity logout() {
        socket.disconnect();
        CONNECTION = Connection.OFFLINE;
        users.clear();
        guilds.clear();
        textChannels.clear();
        voiceChannels.clear();
        privateChannels.clear();
        socket.clearListeners();
        return this;
    }

    @Internal
    Identity addDispatchers(DispatcherAdaptor... dispatchers) {
        this.listeners.addAll(Arrays.asList(dispatchers));
        return this;
    }

    /**
     * [API Use Only]
     * Set the self (user) of this identity.
     * @param selfUser The self user
     */
    @Internal
    public void setSelf (User selfUser) {
        this.self = selfUser;
    }

    /**
     * [API Use Only]
     * Add an user to this identity
     * @param user The user to be added.
     */
    @Internal
    public void addUser (User user) {
        if(users.contains(user)) return;
        this.users.add(user);
    }

    /**
     * [API Use Only]
     * Add a guild to this identity
     * @param guild The guild to be added.
     */
    @Internal
    public void addGuild (Guild guild) {
        if(guilds.contains(guild)) return;
        this.guilds.add(guild);
    }

    /**
     * [API Use Only]
     * Add a TextChannel to this identity
     * @param textChannel The TextChannel to be added.
     */
    @Internal
    public void addTextChannel (TextChannel textChannel) {
        if(textChannels.contains(textChannel)) return;
        this.textChannels.add(textChannel);
    }

    /**
     * [API Use Only]
     * Add a VoiceChannel to this identity
     * @param voiceChannel The VoiceChannel to be added.
     */
    @Internal
    public void addVoiceChannel (VoiceChannel voiceChannel) {
        if(voiceChannels.contains(voiceChannel)) return;
        this.voiceChannels.add(voiceChannel);
    }

    /**
     * [API Use Only]
     * Add a PrivateChannel to this identity
     * @param privateChannel The PrivateChannel to be added.
     */
    @Internal
    public void addPrivateChannel (PrivateChannel privateChannel) {
        if(privateChannels.contains(privateChannel)) return;
        this.privateChannels.add(privateChannel);
    }

    public enum Connection {
        CONNECTING,
        RESUMING,
        CONNECTED,
        READY,
        OFFLINE;

        /**
         * @return Is the connection open.
         */
        public boolean isConnected() {
            return this == CONNECTED || this == READY;
        }

        /**
         * @return Is the connection ready to fire event.
         */
        public boolean isReady() {
            return this == READY;
        }

    }

}
