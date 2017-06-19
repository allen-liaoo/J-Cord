package org.alienideology.jcord;

import com.sun.istack.internal.Nullable;
import org.alienideology.jcord.command.CommandFramework;
import org.alienideology.jcord.event.DispatcherAdaptor;
import org.alienideology.jcord.event.EventManager;
import org.alienideology.jcord.handle.channel.IMessageChannel;
import org.alienideology.jcord.handle.channel.IPrivateChannel;
import org.alienideology.jcord.handle.channel.ITextChannel;
import org.alienideology.jcord.handle.channel.IVoiceChannel;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IRole;
import org.alienideology.jcord.handle.user.IUser;

import java.io.IOException;
import java.util.List;

/**
 * IdentityImpl - The identity of a bot (without shards), a shard, or a human (client)
 * @author AlienIdeology
 */
public interface Identity {

    /**
     * Revive the identity, reconnect to WebSocket.
     * This logout of the account, then log back in.
     *
     * @return The identity revived.
     * @throws IOException If fail to create WebSocket.
     */
    Identity revive() throws IOException;

    /**
     * Get the event manager of this identity.
     * @see EventManager
     *
     * @return The event manager.
     */
    EventManager getEventManager();

    /**
     * Get the {@link DispatcherAdaptor}s registered in the event manager.
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
     * Get the {@link CommandFramework}s registered in the event manager.
     * @see EventManager#getCommandFrameworks()
     *
     * @return A list of Command Frameworks
     */
    List<CommandFramework> getFrameworks();

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
     * Get the self user of this identity.
     *
     * @return The self user.
     */
    IUser getSelf();

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
     *
     * @return A list of channels.
     */
    List<IPrivateChannel> getPrivateChannels();
    
}
