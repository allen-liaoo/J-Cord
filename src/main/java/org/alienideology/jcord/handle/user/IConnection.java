package org.alienideology.jcord.handle.user;

import org.alienideology.jcord.handle.ISnowFlake;
import org.alienideology.jcord.handle.client.IClientObject;
import org.alienideology.jcord.handle.guild.IIntegration;

import java.util.List;

/**
 * IConnection - A connection to a third party account.
 *
 * @author AlienIdeology
 */
public interface IConnection extends IClientObject, ISnowFlake {

    /**
     * Get the user this connection belongs to.
     *
     * @return The user.
     */
    IUser getUser();

    /**
     * Get the account name of this connection.
     *
     * @return The name.
     */
    String getName();

    /**
     * Get the connection type.
     *
     * @return The type of connection.
     */
    IConnection.Type getType();

    /**
     * Check if this connection is being displayed on the user profile.
     *
     * @return True if the connection is being displayed on the user profile.
     */
    boolean displayOnProfile();

    /**
     * Check if the friend sync is enabled for this connection.
     *
     * @return True if the friend sync is enabled.
     */
    boolean isFriendSyncEnabled();

    /**
     * Check if this account connection is verified.
     *
     * @return True if this account connection is verified.
     */
    boolean isVerified();

    /**
     * Check if this account connection is revoked.
     *
     * @return True if this account connection is revoked.
     */
    boolean isRevoked();

    /**
     * Get a list of guild integrations that used this connection.
     *
     * @return A list of guild integrations.
     */
    List<IIntegration> getIntegrations();

    /**
     * Different connection types
     */
    enum Type {

        YOUTUBE ("youtube"),
        TWITCH ("twitch"),
        SKYPE ("skype"),
        STEAM ("steam"),
        REDDIT ("reddit"),
        TWITTER ("twitter"),
        LEAGUE_OF_LEGENDS("leagueoflegends"),
        UNKNOWN ("unknown");

        public final String key;

        Type (String key) {
            this.key = key;
        }

        public static Type getByKey(String key) {
            for (Type type : values()) {
                if (type.key.equals(key))
                    return type;
            }
            return UNKNOWN;
        }
    }

}
