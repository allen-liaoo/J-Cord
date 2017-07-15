package org.alienideology.jcord.handle.client;

import org.alienideology.jcord.handle.user.IUser;

/**
 * IProfile - The client user, with Discord Nitro and more information.
 * Note that this will be a different instance from a self {@link IUser}.
 *
 * @author AlienIdeology
 */
public interface IProfile extends IClientObject, IUser {

    /**
     * Get if the user is on mobile or not.
     *
     * @return True if the user has logged in on mobile.
     */
    boolean onMobile();

    /**
     * Get if the user is a premium account.
     *
     * @return True if the user account is a premium account.
     */
    boolean isPremium();

}
