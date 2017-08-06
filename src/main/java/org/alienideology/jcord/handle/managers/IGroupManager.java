package org.alienideology.jcord.handle.managers;

import org.alienideology.jcord.handle.Icon;
import org.alienideology.jcord.handle.channel.IGroup;
import org.alienideology.jcord.handle.client.IClient;
import org.alienideology.jcord.handle.client.IClientObject;
import org.alienideology.jcord.handle.client.relation.IFriend;
import org.alienideology.jcord.handle.user.IUser;

/**
 * IGroupManager - A manager that manages a group.
 *
 * @author AlienIdeology
 */
public interface IGroupManager extends IClientObject {

    @Override
    default IClient getClient() {
        return getGroup().getClient();
    }

    /**
     * Get the group this manager manages.
     *
     * @return The group.
     */
    IGroup getGroup();

    /**
     * Modify the name of this group.
     * Use empty or {@code null} name to reset the name.
     *
     * @exception IllegalArgumentException
     *          If the group name is not valid. See {@link IGroup#isValidName(String)}.
     *
     * @param name The name.
     */
    default void modifyName(String name) {
        getGroup().getModifier().name(name).modify();
    }

    /**
     * Modify the icon of this group.
     *
     * @param icon The image file.
     */
    default void modifyIcon(Icon icon) {
        getGroup().getModifier().icon(icon).modify();
    }

    /**
     * Add a friend to this group.
     *
     * @param friend The friend.
     */
    default void add(IFriend friend) {
        remove(friend.getUser().getId());
    }

    /**
     * Add a friend to this group by id.
     *
     * @param friendId The friend's id.
     */
    void add(String friendId);

    /**
     * Remove an user from this group.
     *
     * @param user The user.
     */
    default void remove(IUser user) {
        remove(user.getId());
    }

    /**
     * Remove an user from this group by id.
     *
     *  @param userId The user's id.
     */
    void remove(String userId);

}
