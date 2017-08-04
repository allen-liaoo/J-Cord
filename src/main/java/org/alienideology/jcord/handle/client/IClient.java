package org.alienideology.jcord.handle.client;

import org.alienideology.jcord.handle.IDiscordObject;
import org.alienideology.jcord.handle.channel.ICallChannel;
import org.alienideology.jcord.handle.channel.IGroup;
import org.alienideology.jcord.handle.client.app.IApplication;
import org.alienideology.jcord.handle.client.app.IAuthApplication;
import org.alienideology.jcord.handle.client.relation.IBlockedUser;
import org.alienideology.jcord.handle.client.relation.IFriend;
import org.alienideology.jcord.handle.client.relation.IRelationship;
import org.alienideology.jcord.handle.client.setting.IClientSetting;
import org.alienideology.jcord.handle.client.setting.IGuildSetting;
import org.alienideology.jcord.handle.client.setting.MessageNotification;
import org.alienideology.jcord.handle.managers.IClientManager;
import org.alienideology.jcord.handle.user.IConnection;
import org.alienideology.jcord.handle.user.IUser;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * IClient - Represents a Discord client.
 *
 * @author AlienIdeology
 */
public interface IClient extends IDiscordObject {

    /**
     * Get the client manager that manages this client.
     *
     * @return The client manager.
     */
    IClientManager getManager();

    /**
     * Get the profile (or self user) of this client.
     * Note that this will be a different instance from a self {@link IUser}.
     *
     * @return The profile.
     */
    IProfile getProfile();

    /**
     * Get a group by the group id.
     *
     * @param id The group id.
     * @return The group, or null if no group is found.
     */
    @Nullable
    IGroup getGroup(String id);

    /**
     * Get a group by name.
     * The group returned will be the first group matching the given name.
     *
     * @param name The group name.
     * @return The group, or null if:
     * <ul>
     *     <li>No group is found by the given name.</li>
     *     <li>The parameter is null.</li>
     * </ul>
     */
    @Nullable
    IGroup getGroupByName(String name);

    /**
     * Get a list of groups with the same owner.
     *
     * @param ownerId The owner Id.
     * @return A list of groups.
     */
    List<IGroup> getGroupsByOwner(String ownerId);

    /**
     * Get a list of groups that contains a given user.
     *
     * @param userId The user's Id.
     * @return A list of groups.
     */
    List<IGroup> getGroupsByUser(String userId);

    /**
     * Get a list of all groups that this clients participates in.
     *
     * @return A list of groups.
     */
    List<IGroup> getGroups();

    /**
     * Get a {@link ICallChannel} by Id.
     *
     * @param id The channel id.
     * @return The call channel, or null if no channel is not found.
     */
    default ICallChannel getCallChannel(String id) {
        return getCallChannels().stream().filter(c -> c.getId().equals(id)).findAny().orElse(null);
    }

    /**
     * Get a list of all call channels.
     *
     * @return A list of call channels.
     */
    default List<ICallChannel> getCallChannels() {
        List<ICallChannel> channels = new ArrayList<>(getGroups());
        channels.addAll(getIdentity().getPrivateChannels());
        return channels;
    }

    /**
     * Get a relationship by an user id.
     *
     * @param userId The user id.
     * @return The relationship, or null if no relationship is found.
     */
    @Nullable
    IRelationship getRelationship(String userId);

    /**
     * Get a list of all relationships that falls under a certain {@link IRelationship.Type}.
     *
     * @return The relationships.
     */
    List<IRelationship> getRelationships(IRelationship.Type type);

    /**
     * Get a list of all relationships.
     *
     * @return The relationships.
     */
    List<IRelationship> getRelationships();

    /**
     * Get a friend by an user id.
     *
     * @param userId The user id.
     * @return The friend, or null if no friend is found.
     */
    @Nullable
    default IFriend getFriend(String userId) {
        return getFriends().stream().filter(f -> f.getUser().getId().equals(userId)).findAny().orElse(null);
    }

    /**
     * Get a list of all friends.
     *
     * @return The friends.
     */
    default List<IFriend> getFriends() {
        return getRelationships().stream()
                .filter(r -> r instanceof IFriend)
                .map(f -> (IFriend) f)
                .collect(Collectors.toList());
    }

    /**
     * Get a blocked user by an user id.
     *
     * @param userId The user id.
     * @return The blocked user, or null if no blocked user is found.
     */
    @Nullable
    default IBlockedUser getBlockedUser(String userId) {
        return getBlockedUsers().stream().filter(bu -> bu.getUser().getId().equals(userId)).findAny().orElse(null);
    }

    /**
     * Get a list of all blocked users.
     *
     * @return The blocked users.
     */
    default List<IBlockedUser> getBlockedUsers() {
        return getRelationships().stream()
                .filter(r -> r instanceof IBlockedUser)
                .map(bu -> (IBlockedUser) bu)
                .collect(Collectors.toList());
    }

    /**
     * Get a list of all account connections.
     *
     * @return The connections.
     */
    List<IConnection> getConnections();

    /**
     * Get a note by an user id.
     *
     * @param userId The user id.
     * @return The note, or null if no note is found.
     */
    @Nullable
    INote getNote(String userId);

    /**
     * Get a list of all notes.
     *
     * @return The notes.
     */
    List<INote> getNotes();

    /**
     * Get the Discord client settings for this client.
     *
     * @return The settings.
     */
    IClientSetting getSetting();

    /**
     * Get a guild setting by guild ID.
     *
     * @param guildId the guild ID.
     * @return The guild setting, or null if no setting is found.
     */
    @Nullable
    IGuildSetting getGuildSetting(String guildId);

    /**
     * Get a list of muted guilds.
     *
     * @return The muted guilds.
     */
    default List<IGuildSetting> getMutedGuildSettings() {
        return getGuildSettings().stream().filter(IGuildSetting::isMuted).collect(Collectors.toList());
    }

    /**
     * Get a list of guild settings with the specified {@link MessageNotification}.
     *
     * @param notification The notification setting.
     * @return A list of guilds with that notification setting.
     */
    default List<IGuildSetting> getGuildSettingsWith(MessageNotification notification) {
        return getGuildSettings().stream().filter(gs -> gs.getNotificationSetting().equals(notification)).collect(Collectors.toList());
    }

    /**
     * Get a list of all guild settings.
     *
     * @return The guild settings.
     */
    List<IGuildSetting> getGuildSettings();

    /**
     * Get an application by Id.
     * This method performs an http request.
     *
     * @param id The application id.
     * @return The application, or null if no application is found.
     */
    @Nullable
    IApplication getApplication(String id);

    /**
     * Get a list of applications this client owns.
     *
     * @return The applications.
     */
    List<IApplication> getApplications();

    /**
     * Get an authorized application by Id.
     * Note that this id should be {@link IAuthApplication#getAuthorizeId()}, not the application Id.
     * This method performs an http request.
     *
     * @param id The application id.
     * @return The authorized application, or null if no application is found.
     */
    @Nullable
    IAuthApplication getAuthApplication(String id);

    /**
     * Get a list of applications this client authorized.
     *
     * @return The authorized applications.
     */
    List<IAuthApplication> getAuthApplications();

}
