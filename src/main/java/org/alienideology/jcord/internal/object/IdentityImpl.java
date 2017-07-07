package org.alienideology.jcord.internal.object;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import org.alienideology.jcord.Identity;
import org.alienideology.jcord.IdentityType;
import org.alienideology.jcord.JCord;
import org.alienideology.jcord.bot.Bot;
import org.alienideology.jcord.bot.command.CommandFramework;
import org.alienideology.jcord.event.DispatcherAdaptor;
import org.alienideology.jcord.event.EventManager;
import org.alienideology.jcord.handle.channel.*;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IRole;
import org.alienideology.jcord.handle.managers.ISelfManager;
import org.alienideology.jcord.handle.permission.Permission;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.handle.user.IWebhook;
import org.alienideology.jcord.internal.exception.ErrorResponseException;
import org.alienideology.jcord.internal.gateway.ErrorResponse;
import org.alienideology.jcord.internal.gateway.GatewayAdaptor;
import org.alienideology.jcord.internal.gateway.HttpPath;
import org.alienideology.jcord.internal.object.channel.PrivateChannel;
import org.alienideology.jcord.internal.object.guild.Guild;
import org.alienideology.jcord.internal.object.managers.SelfManager;
import org.alienideology.jcord.internal.object.user.User;
import org.alienideology.jcord.util.log.Logger;
import org.jetbrains.annotations.Nullable;
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
public final class IdentityImpl implements Identity {

    public Logger LOG;

    private IdentityType type;
    private String token;

    private WebSocketFactory wsFactory;
    private WebSocket socket;
    public Connection CONNECTION = Connection.OFFLINE;

    public Bot bot;

    private EventManager manager;

    private IUser self;
    private SelfManager selfManager;
    private List<IUser> users = new ArrayList<>();
    private List<IGuild> guilds = new ArrayList<>();
    private List<IPrivateChannel> privateChannels = new ArrayList<>();

    public IdentityImpl(IdentityType type, WebSocketFactory wsFactory, Logger logger) {
        this.type = type;
        this.wsFactory = wsFactory;
        this.selfManager = new SelfManager(this);
        this.LOG = logger;
    }

    @Override
    public IdentityImpl revive() throws IOException {
        logout();
        login(token);
        return this;
    }

    @Override
    public EventManager getEventManager() {
        return manager;
    }

    @Override
    public List<DispatcherAdaptor> getDispatchers () {
        return manager.getDispatcherAdaptors();
    }

    @Override
    public List<Object> getSubscribers() {
        return manager.getEventSubscribers();
    }

    @Override
    public List<CommandFramework> getFrameworks() {
        return manager.getCommandFrameworks();
    }

    @Override
    public IdentityType getType() {
        return type;
    }

    @Override
    public String getToken () {
        return token;
    }

    @Override
    public Bot getAsBot() {
        return bot;
    }

    @Override
    public IUser getSelf() {
        return self;
    }

    @Override
    public ISelfManager getSelfManager() {
        return selfManager;
    }

    @Override
    @Nullable
    public IUser getUser(String id) {
        for (IUser user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<IUser> getUsers() {
        return users;
    }

    @Override
    @Nullable
    public IWebhook getWebhook(String id) {
        for (IGuild guild : guilds) {
            IWebhook webhook = guild.getWebhook(id);
            if (webhook != null) return webhook;
        }
        return null;
    }

    @Override
    public List<IWebhook> getWebhooks() {
        List<IWebhook> webhooks = new ArrayList<>();
        for (IGuild guild : guilds) {
            if (guild.getSelfMember().hasPermissions(true, Permission.MANAGE_WEBHOOKS)) {
                webhooks.addAll(guild.getWebhooks());
            }
        }
        return webhooks;
    }

    @Nullable
    @Override
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
            String uri = request.asJson().getBody().getObject().getString("url") + "?encoding=json&v=" + JCord.DISCORD_GATEWAY_VERSION;

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
        this.manager = manager.setIdentity(this);
        return this;
    }

    public WebSocket getSocket() {
        return socket;
    }

    public void setSelf (User selfUser) {
        this.self = selfUser;

        // Initialize this after self user is built
        this.bot = new Bot(this);
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

}
