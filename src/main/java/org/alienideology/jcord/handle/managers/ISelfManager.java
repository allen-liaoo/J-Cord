package org.alienideology.jcord.handle.managers;

import org.alienideology.jcord.Identity;
import org.alienideology.jcord.handle.guild.IGuild;
import org.alienideology.jcord.handle.guild.IMember;
import org.alienideology.jcord.handle.user.IUser;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * ISelfManager - A manager that manages self user.
 * This managers is for both bot and client, which means setting email or password is not supported.
 *
 * @author AlienIdeology
 */
public interface ISelfManager {

    /**
     * Get the identity this self user is.
     *
     * @return The identity.
     */
    Identity getIdentity();

    /**
     * Get the self user.
     *
     * @return The user.
     */
    IUser getSelf();

    /**
     * Modify the user name of this identity.
     * Changing the username will cause the discriminator to randomize.
     *
     * @param name The new username.
     */
    void modifyUserName(String name);

    /**
     * Modify the avatar of this identity.
     *
     * @param image The buffered avatar.
     * @throws IOException When decoding image.
     */
    void modifyAvatar(BufferedImage image) throws IOException;

    /**
     * Modify the avatar of this identity.
     *
     * @param path The avatar file path.
     * @throws IOException When decoding image.
     */
    void modifyAvatar(String path) throws IOException;

    /**
     * Leave a guild.
     * Do not use {@link IGuildManager#kickMember(IMember)} to kick the self user,
     * use this method instead.
     *
     * @param guild The guild to leave.
     */
    void leaveGuild(IGuild guild);

}
