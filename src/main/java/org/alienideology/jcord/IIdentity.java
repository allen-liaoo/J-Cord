package org.alienideology.jcord;

import com.sun.istack.internal.Nullable;
import org.alienideology.jcord.command.CommandFramework;
import org.alienideology.jcord.event.DispatcherAdaptor;
import org.alienideology.jcord.event.EventManager;
import org.alienideology.jcord.handle.channel.*;
import org.alienideology.jcord.handle.guild.*;
import org.alienideology.jcord.handle.user.*;
import org.alienideology.jcord.internal.object.Identity;
import org.alienideology.jcord.internal.object.user.User;

import java.io.IOException;
import java.util.List;

/**
 * Identity - The identity of a bot (without shards), a shard, or a human (client)
 * @author AlienIdeology
 */
public interface IIdentity {

    Identity revive() throws IOException;

    EventManager getEventManager();

    List<DispatcherAdaptor> getDispatchers ();

    List<Object> getSubscribers();

    List<CommandFramework> getFrameworks();

    String getToken();

    IUser getSelf();

    @Nullable
    IUser getUser(String id);

    List<IUser> getUsers();

    @Nullable
    IGuild getGuild(String id);

    List<IGuild> getGuilds();

    @Nullable
    IRole getRole(String id);

    List<IRole> getAllRoles();

    @Nullable
    IMessageChannel getMessageChannel(String id);

    List<IMessageChannel> getMessageChannels();

    @Nullable
    ITextChannel getTextChannel(String id);

    List<ITextChannel> getTextChannels();

    @Nullable
    IVoiceChannel getVoiceChannel(String id);

    List<IVoiceChannel> getVoiceChannels();

    @Nullable
    IPrivateChannel getPrivateChannel(String id);

    List<IPrivateChannel> getPrivateChannels();
    
}
