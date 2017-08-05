package org.alienideology.jcord.handle.channel;

import org.alienideology.jcord.handle.client.IClientObject;
import org.alienideology.jcord.handle.client.relation.IFriend;
import org.alienideology.jcord.handle.managers.IGroupManager;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.gateway.HttpPath;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * IGroup - A private channel with more than one user.
 * The private channel will not have any bot recipients, since bots may not join group channel.
 *
 * @author AlienIdeology
 */
public interface IGroup extends IClientObject, IMessageChannel, ICallChannel {

    /**
     * The maximum length of a group's name.
     */
    int NAME_LENGTH_MAX = 100;

    /**
     * Checks if an group's name is valid or not.
     *
     * Validations: <br />
     * <ul>
     *     <li>The name may be null or empty, used to reset group's name.</li>
     *     <li>The length of the name must be shorter than {@link #NAME_LENGTH_MAX}.</li>
     * </ul>
     *
     * @param name The name to be check with.
     * @return True if the name is valid.
     */
    static boolean isValidName(String name) {
        return name == null || name.isEmpty() || name.length() <= NAME_LENGTH_MAX;
    }

    /**
     * Leave this group.
     */
    default void leave() {
        getClient().getManager().leaveGroup(this);
    }

    /**
     * Get the manager that manages this group.
     *
     * @return The manager.
     */
    IGroupManager getManager();

    /**
     * Get the name of this group.
     * If the group does not have a name, then this returns null.
     *
     * @return The group name.
     */
    @Nullable
    String getName();

    /**
     * Get the name of this group.
     * If this group does not have a name, this will generates a default name,
     * which is formed by joining each recipient's name with {@code , } in between each name.
     * Note that the name will not contain the client's name.
     *
     * @return The group name.
     */
    @NotNull
    default String getAlternativeName() {
        return getName() == null ? getRecipients().stream().map(IUser::getName).collect(Collectors.joining(", ")) : getName();
    }

    /**
     * Get the icon hash of this group.
     *
     * @return The icon hash.
     */
    String getIconHash();

    /**
     * Get the icon url of this group.
     *
     * @return The icon url.
     */
    default String getIconUrl() {
        return String.format(HttpPath.EndPoint.GROUP_ICON, getId(), getIconHash());
    }

    /**
     * Get the owner of this group.
     *
     * @return The group owner.
     */
    IUser getOwner();

    /**
     * Get a recipient by user id.
     *
     * @param userId The user id.
     * @return The recipient, or null if no recipient is found.
     */
    @Nullable
    default IUser getRecipient(String userId) {
        return getRecipients().stream().filter(r -> r.getId().equals(userId)).findAny().orElse(null);
    }

    /**
     * Get a list of recipients of this group.
     *
     * @return A list of recipients.
     */
    List<IUser> getRecipients();

    /**
     * Get a list of recipients that are friends with the client.
     *
     * @return A list of friends.
     */
    default List<IFriend> getFriends() {
        return getRecipients().stream()
                .map(r -> getClient().getFriend(r.getId()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

}
