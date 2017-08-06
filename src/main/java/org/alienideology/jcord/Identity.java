package org.alienideology.jcord;

import org.alienideology.jcord.bot.command.CommandFramework;
import org.alienideology.jcord.event.DispatcherAdaptor;
import org.alienideology.jcord.event.EventManager;
import org.alienideology.jcord.handle.IInvite;
import org.alienideology.jcord.handle.bot.IBot;
import org.alienideology.jcord.handle.channel.*;
import org.alienideology.jcord.handle.client.IClient;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IRole;
import org.alienideology.jcord.handle.managers.ISelfManager;
import org.alienideology.jcord.handle.modifiers.ISelfModifier;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.handle.user.IWebhook;
import org.alienideology.jcord.internal.exception.ErrorResponseException;
import org.alienideology.jcord.internal.rest.ErrorResponse;
import org.alienideology.jcord.util.log.Logger;
import org.jetbrains.annotations.Nullable;

import java.net.ConnectException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * IdentityImpl - The identity of a bot (without shards), a shard, or a human (java.client)
 * @author AlienIdeology
 */
public interface Identity {

    /**
     * Revive the identity, reconnect to WebSocket.
     * This logout of the account, then log back in.
     *
     * @return The identity revived.
     *
     * @throws ErrorResponseException
     *          If the identity token is not valid.
     *          If you get this exception, please check if the bot's token has been revoked or not.
     *          For clients, please copy the token again.
     * @see ErrorResponse#INVALID_AUTHENTICATION_TOKEN
     *
     * @throws URISyntaxException
     *          If Discord failed to provide a valid URI. Please contact a developer and provide the failed URI.
     *
     * @throws ConnectException
     *          Can be caused by:
     *          <ul>
     *              <li>Fail to create web socket (Establishing connection on the library side).</li>
     *              <li>Connecting the server failed.</li>
     *              <li>The opening handshake failed.</li>
     *          </ul>
     */
    Identity revive() throws ErrorResponseException, URISyntaxException, ConnectException;

    /**
     * Get the event managers of this identity.
     * @see EventManager
     *
     * @return The event managers.
     */
    EventManager getEventManager();

    /**
     * Get the {@link DispatcherAdaptor}s registered in the event managers.
     * @see EventManager#getDispatcherAdaptors()
     *
     * @return A list of Dispatcher Adaptors.
     */
    List<DispatcherAdaptor> getDispatchers();

    /**
     * Get the objects that was registered as {@link org.alienideology.jcord.event.EventSubscriber}.
     * @see EventManager#getEventSubscribers()
     *
     * @return A list of objects that has subscribed to events.
     */
    List<Object> getSubscribers();

    /**
     * Get the {@link CommandFramework}s registered in the event managers.
     * @see EventManager#getCommandFrameworks()
     *
     * @return A list of Command Frameworks
     */
    List<CommandFramework> getFrameworks();

    /**
     * Get the identity type.
     *
     * @return The identity type.
     */
    IdentityType getType();

    /**
     * Get the token of this identity.
     * Remember to keep the token secret.
     * Bot tokens will looks like {@code Bot Your.Token.Here}.
     * User tokens will looks like {@code Your.Token.Here}.
     *
     * @return The string token.
     */
    String getToken();

    /**
     * Get the logger of this identity.
     * This logger is used to log important system messages.
     *
     * @return The logger
     */
    Logger getLogger();

    /**
     * Get this identity as a {@link IBot}.
     *
     * @exception IllegalArgumentException
     *          If the identity type is {@link IdentityType#CLIENT}.
     * @return The bot.
     */
    IBot getAsBot() throws IllegalArgumentException;

    /**
     * Get this identity as a {@link IClient}.
     *
     * @exception IllegalArgumentException
     *          If the identity type is {@link IdentityType#BOT}.
     * @return The client.
     */
    IClient getAsClient() throws IllegalArgumentException;

    /**
     * Get the self user of this identity.
     *
     * @return The self user.
     */
    IUser getSelf();

    /**
     * Get the self manager of this identity.
     *
     * @return The self manager.
     */
    ISelfManager getSelfManager();

    /**
     * Get the self modifier of this identity.
     *
     * @return The self modifier.
     */
    ISelfModifier getSelfModifier();

    /**
     * Get the ping, or heart beat time in milliseconds for the gateway connection with Discord server.
     *
     * @return The heartbeat interval in milliseconds.
     */
    long getHeartbeatInterval();

    /**
     * Get a user by ID.
     *
     * @param id The user ID.
     * @return The user, or null if the ID does not match any user.
     */
    @Nullable
    IUser getUser(String id);

    /**
     * Get a list of users this identity had a opened PrivateChannel or mutual guild.
     *
     * @return A list of users.
     */
    List<IUser> getUsers();

    /**
     * Get a webhook by ID.
     *
     * @param id The webhook ID.
     * @return The webhook, or null if no webhook found.
     */
    @Nullable
    IWebhook getWebhook(String id);

    /**
     * Get a list of webhooks this identity has a mutual guild with.
     * This only includes webhooks that the identity has permission to manage.
     *
     * @return A list of webhooks.
     */
    List<IWebhook> getWebhooks();

    /**
     * Get a guild by ID.
     *
     * @param id The guild ID.
     * @return The guild, or null if the ID does not match any guild.
     */
    @Nullable
    IGuild getGuild(String id);

    /**
     * Get a list of guilds this identity is in.
     *
     * @return A list of guilds.
     */
    List<IGuild> getGuilds();

    /**
     * Get a role by ID.
     *
     * @param id The role ID.
     * @return The role, or null if the ID does not match any role.
     */
    @Nullable
    IRole getRole(String id);

    /**
     * Get all roles from all the guilds this identity is in.
     *
     * @return A list of roles.
     */
    List<IRole> getAllRoles();

    /**
     * Get a channel by ID.
     *
     * @param id The channel ID.
     * @return A channel (Can be private, text, or voice channel), or null if no channel matches the ID.
     */
    @Nullable
    IChannel getChannel(String id);

    /**
     * Get a list of all channels under this identity.
     *
     * @return A list of channels.
     */
    List<IChannel> getAllChannels();

    /**
     * Get a GuildChannel by ID.
     *
     * @param id The channel ID.
     * @return The channel, or null if the ID does not match any guild channel.
     */
    @Nullable
    IGuildChannel getGuildChannel(String id);

    /**
     * Get all GuildChannels, including all TextChannels and VoiceChannels.
     *
     * @return A list of all guild channels under this identity.
     */
    List<IGuildChannel> getAllGuildChannels();

    /**
     * Get an AudioChannel by ID.
     *
     * @param id The channel ID.
     * @return The channel, or null if the ID does not match any audio channel.
     */
    IAudioChannel getAudioChannel(String id);

    /**
     * Get all AudioChannels, including all VoiceChannels and CallChannels.
     *
     * @return A list of all audio channels under this identity.
     */
    List<IAudioChannel> getAllAudioChannels();

    /**
     * Get a MessageChannel by ID.
     *
     * @param id The channel ID.
     * @return The channel, or null if the ID does not match any channel.
     */
    @Nullable
    IMessageChannel getMessageChannel(String id);

    /**
     * Get all MessageChannels, including all TextChannels and PrivateChannels.
     *
     * @return A list of channels.
     */
    List<IMessageChannel> getAllMessageChannels();

    /**
     * Get a TextChannel by ID.
     *
     * @param id The channel ID.
     * @return The channel, or null if the ID does not match any channel.
     */
    @Nullable
    ITextChannel getTextChannel(String id);

    /**
     * Get all TextChannels from all the guilds this identity is in.
     *
     * @return A list of channels.
     */
    List<ITextChannel> getAllTextChannels();

    /**
     * Get a VoiceChannel by ID.
     *
     * @param id The channel ID.
     * @return The channel, or null if the ID does not match any channel.
     */
    @Nullable
    IVoiceChannel getVoiceChannel(String id);

    /**
     * Get all VoiceChannels from all the guilds this identity is in.
     *
     * @return A list of channels.
     */
    List<IVoiceChannel> getAllVoiceChannels();

    /**
     * Get a PrivateChannel by ID.
     * Note that the private channel ID is not the user ID.
     *
     * @param id The channel ID.
     * @return The channel, or null if the ID does not match any channel.
     */
    @Nullable
    IPrivateChannel getPrivateChannel(String id);

    /**
     * Get a PrivateChannel by the user's by ID.
     *
     * @param userId The user ID of a PrivateChannel.
     * @return The channel, or null if the ID does not match any channel.
     */
    @Nullable
    IPrivateChannel getPrivateChannelByUserId(String userId);

    /**
     * Get a list of PrivateChannels that are open with the identity.
     * Note that this wont be a complete list of private channels that are opened with the identity,
     * since Discord does not provide a list of private channels.
     * The private channels included in the list will only be channels that had fired
     * {@link org.alienideology.jcord.event.channel.dm.PrivateChannelCreateEvent} during the socket connection.
     *
     * @return A list of channels.
     */
    List<IPrivateChannel> getPrivateChannels();

    /**
     * Get an invite by code.
     *
     * @exception org.alienideology.jcord.internal.exception.PermissionException
     *          If the identity does not have {@code Create Instant Invite} permission in the channel.
     *
     * @param code The invite code.
     * @return The invite, or null if no invite is found.
     */
    @Nullable
    IInvite getInvite(String code);

    enum Connection {
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
