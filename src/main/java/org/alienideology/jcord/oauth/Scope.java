package org.alienideology.jcord.oauth;

import java.util.Arrays;
import java.util.Collection;

/**
 * Scope - Provides access to certain resources of a user account.
 *
 * @author AlienIdeology
 */
public enum Scope {

    BOT ("bot"),
    CONNECTIONS ("connections"),
    EMAIL ("email"),
    IDENTIFY ("identify"),
    GUILDS ("guilds"),
    GUILDS_JOIN ("guilds.join"),
    GDM_JOIN ("gdm.join"),
    MESSAGES_READ ("messages.read"),
    RPC ("rpc"),
    RPC_API ("rpc.api"),
    RPC_NOTIFICATIONS_READ ("rpc.notifications.read"),
    WEBHOOK_INCOMING ("webhook.incoming"),
    UNKNOWN (null);

    public String key;

    Scope(String key) {
        this.key = key;
    }

    public static boolean hasScope(Scope scope, Collection<Scope> scopes) {
        return scope != UNKNOWN && scopes.contains(scope);
    }

    public static boolean hasScope(Scope scope, Scope... scopes) {
        return hasScope(scope, Arrays.asList(scopes));
    }

}
