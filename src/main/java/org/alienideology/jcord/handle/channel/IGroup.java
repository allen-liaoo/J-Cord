package org.alienideology.jcord.handle.channel;

import org.alienideology.jcord.handle.client.IClientObject;
import org.alienideology.jcord.handle.user.IUser;
import org.alienideology.jcord.internal.gateway.HttpPath;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

/**
 * IGroup - A private channel with more than one user.
 * The private channel will not have any bot recipients, since bots may not join group channel.
 *
 * @author AlienIdeology
 */
public interface IGroup extends IClientObject, IMessageChannel, ICallChannel {

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
     * Get a list of recipients of this group.
     *
     * @return A list of recipients.
     */
    List<IUser> getRecipients();

}
