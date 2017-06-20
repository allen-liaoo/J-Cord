package org.alienideology.jcord.internal.object;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.sun.istack.internal.Nullable;
import org.alienideology.jcord.IdentityType;
import org.alienideology.jcord.bot.command.CommandFramework;
import org.alienideology.jcord.event.DispatcherAdaptor;
import org.alienideology.jcord.event.EventManager;
import org.alienideology.jcord.handle.channel.*;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IRole;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.exception.ErrorResponseException;
import org.alienideology.jcord.internal.gateway.ErrorResponse;
import org.alienideology.jcord.internal.gateway.GatewayAdaptor;
import org.alienideology.jcord.internal.gateway.HttpPath;
import org.alienideology.jcord.internal.object.channel.PrivateChannel;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.user.User;
import org.apache.commons.logging.impl.SimpleLog;
import org.json.JSONException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author AlienIdeology
 */
public final class IdentityImpl implements org.alienideology.jcord.Identity {

    public SimpleLog LOG = new SimpleLog("IdentityImpl");

    private IdentityType type;
    private String token;

    private WebSocketFactory wsFactory;
    private WebSocket socket;
    public Connection CONNECTION = Connection.OFFLINE;

    private EventManager manager;

    private IUser self;
    private List<IUser> users = new ArrayList<>();
    private List<IGuild> guilds = new ArrayList<>();
    private List<IPrivateChannel> privateChannels = new ArrayList<>();

    public IdentityImpl(IdentityType type, WebSocketFactory wsFactory) {
        this.type = type;
        this.wsFactory = wsFactory;
    }

    public IdentityImpl revive() throws IOException {
        logout();
        login(token);
        return this;
    }

    public EventManager getEventManager() {
        return manager;
    }

    public List<DispatcherAdaptor> getDispatchers () {
        return manager.getDispatcherAdaptors();
    }

    public List<Object> getSubscribers() {
        return manager.getEventSubscribers();
    }

    public List<CommandFramework> getFrameworks() {
        return manager.getCommandFrameworks();
    }

    public String getToken () {
        return token;
    }

    public IUser getSelf() {
        return self;
    }

    @Nullable
    public IUser getUser(String id) {
        for (IUser user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    public List<IUser> getUsers() {
        return users;
    }

    @Nullable
    public IGuild getGuild(String id) {
        for (IGuild guild : guilds) {
            if (guild.getId().equals(id)) {
                return guild;
            }
        }
        return null;
    }

    public List<IGuild> getGuilds() {
        return Collections.unmodifiableList(guilds);
    }

    @Nullable
    public IRole getRole(String id) {
        for (IGuild guild : guilds) {
            IRole role = guild.getRole(id);
            if (role != null) return role;
        }
        return null;
    }

    public List<IRole> getAllRoles() {
        List<IRole> roles = new ArrayList<>();
        for (IGuild guild : guilds) {
            roles.addAll(guild.getRoles());
        }
        return Collections.unmodifiableList(roles);
    }

    @Override
    public IChannel getChannel(String id) {
        IGuildChannel channel = getGuildChannel(id);
        return channel == null ? getPrivateChannel(id) : channel;
    }

    @Override
    public List<IChannel> getAllChannels() {
        List<IChannel> channels = new ArrayList<>(getAllGuildChannels());
        channels.addAll(getPrivateChannels());
        return channels;
    }

    @Override
    public IGuildChannel getGuildChannel(String id) {
        for (IGuild guild : guilds) {
            IGuildChannel channel = guild.getGuildChannel(id);
            if (channel != null) return channel;
        }
        return null;
    }

    @Override
    public List<IGuildChannel> getAllGuildChannels() {
        List<IGuildChannel> channels = new ArrayList<>(getAllTextChannels());
        channels.addAll(getAllVoiceChannels());
        return channels;
    }

    @Nullable
    public IMessageChannel getMessageChannel(String id) {
        List<IMessageChannel> channels = getAllMessageChannels();
        for (IMessageChannel channel : channels) {
            if (channel.getId().equals(id))
                return channel;
        }
        return null;
    }

    public List<IMessageChannel> getAllMessageChannels() {
        List<IMessageChannel> channels = new ArrayList<>(getAllTextChannels());
        channels.addAll(privateChannels);
        return Collections.unmodifiableList(channels);
    }

    @Nullable
    public ITextChannel getTextChannel(String id) {
        for (ITextChannel channel : getAllTextChannels()) {
            if (channel.getId().equals(id)) {
                return channel;
            }
        }
        return null;
    }

    public List<ITextChannel> getAllTextChannels() {
        List<ITextChannel> channels = new ArrayList<>();
        for (IGuild guild : guilds) {
            channels.addAll(guild.getTextChannels());
        }
        return Collections.unmodifiableList(channels);
    }

    @Nullable
    public IVoiceChannel getVoiceChannel(String id) {
        for (IVoiceChannel vc : getAllVoiceChannels()) {
            if (vc.getId().equals(id)) {
                return vc;
            }
        }
        return null;
    }

    public List<IVoiceChannel> getAllVoiceChannels() {
        List<IVoiceChannel> channels = new ArrayList<>();
        for (IGuild guild : guilds) {
            channels.addAll(guild.getVoiceChannels());
        }
        return Collections.unmodifiableList(channels);
    }

    @Nullable
    public IPrivateChannel getPrivateChannel(String id) {
        for (IPrivateChannel dm : privateChannels) {
            if (dm.getId().equals(id)) {
                return dm;
            }
        }
        return null;
    }

    @Override
    public IPrivateChannel getPrivateChannelByUserId(String userId) {
        for (IPrivateChannel dm : privateChannels) {
            if (dm.getRecipient().getId().equals(userId)) {
                return dm;
            }
        }
        return null;
    }

    public List<IPrivateChannel> getPrivateChannels() {
        return Collections.unmodifiableList(privateChannels);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IdentityImpl)) return false;

        IdentityImpl identity = (IdentityImpl) o;

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

    public IdentityImpl login (String token) throws ErrorResponseException, IllegalArgumentException, IOException {
        if (type == IdentityType.BOT && !token.startsWith("Bot ")) {
            this.token = "Bot " + token;
        } else {
            this.token = token;
        }

        try {
            HttpRequest request = Unirest.get(HttpPath.Gateway.GET_GATEWAY_BOT.getPath()).header("Authorization", this.token);
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

    public IdentityImpl logout() {
        socket.disconnect();
        CONNECTION = Connection.OFFLINE;
        users.clear();
        guilds.clear();
        privateChannels.clear();
        socket.clearListeners();
        return this;
    }

    public IdentityImpl setEventManager(EventManager manager) {
        this.manager = manager;
        return this;
    }

    public void setSelf (User selfUser) {
        this.self = selfUser;
    }

    public void addUser (User user) {
        if(users.contains(user)) return;
        this.users.add(user);
    }

    public void updateUser (User user) {
        this.users.set(users.indexOf(user), user);
    }

    // Removes and return the removed user
    public User removeUser (String userId) {
        User user = (User) getUser(userId);
        this.users.remove(user);
        return user;
    }

    public void addGuild(Guild guild) {
        if(guilds.contains(guild)) return;
        this.guilds.add(guild);
    }

    public void updateGuild(Guild guild) {
        this.guilds.set(guilds.indexOf(guild), guild);
    }

    public Guild removeGuild(String guildId) {
        Guild guild = (Guild) getGuild(guildId);
        this.guilds.remove(guild);
        return guild;
    }

    public void addPrivateChannel(PrivateChannel privateChannel) {
        if(privateChannels.contains(privateChannel)) return;
        this.privateChannels.add(privateChannel);
    }

    public void updatePrivateChannel(PrivateChannel channel) {
        this.privateChannels.set(privateChannels.indexOf(channel), channel);
    }

    public PrivateChannel removePrivateChannel(String channelId) {
        PrivateChannel channel= (PrivateChannel) getPrivateChannel(channelId);
        this.privateChannels.remove(channel);
        return channel;
    }

    @Override
    public String toString() {
        return "IdentityImpl{" +
                "type=" + type +
                ", socket=" + socket +
                ", CONNECTION=" + CONNECTION +
                ", self=" + self +
                '}';
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
