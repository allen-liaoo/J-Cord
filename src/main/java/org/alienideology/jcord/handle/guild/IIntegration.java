package org.alienideology.jcord.handle.guild;

import org.alienideology.jcord.handle.IDiscordObject;
import org.alienideology.jcord.handle.ISnowFlake;
import org.alienideology.jcord.handle.user.IConnection;
import org.alienideology.jcord.handle.user.IUser;

import java.time.OffsetDateTime;

/**
 * IIntegration - A guild integration that enables user to integrate tools from third party accounts.
 *
 * @author AlienIdeology
 */
public interface IIntegration extends IDiscordObject, ISnowFlake {

    /**
     * Get the name of this integration.
     *
     * @return The name.
     */
    String getName();

    /**
     * Get the type of this integration.
     *
     * @return The integration type.
     */
    IConnection.Type getType();

    /**
     * Get the author of this integration.
     *
     * @return The author.
     */
    IUser getUser();

    /**
     * Get the account of this integration.
     *
     * @return The account.
     */
    IIntegration.Account getAccount();

    /**
     * Get the role subscribers will be assign to.
     *
     * @return The subscriber role.
     */
    IRole getSubscriberRole();

    /**
     * Get the time this integration was synced.
     *
     * @return The last synced time.
     */
    OffsetDateTime getLastSynced();

    /**
     * Check if this integration is enabled or not.
     *
     * @return True if this integration is enabled.
     */
    boolean isEnabled();

    /**
     * Check if this integration is syncing or not.
     *
     * @return True if this integration is syncing.
     */
    boolean isSyncing();

    /**
     * An integration account.
     */
    class Account implements ISnowFlake {

        private final String id;
        private String name;

        public Account(String id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String getId() {
            return id;
        }

        /**
         * Get the name of this integration account.
         *
         * @return The name.
         */
        public String getName() {
            return name;
        }

    }

}
