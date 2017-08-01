package org.alienideology.jcord.internal.gateway;

import com.mashape.unirest.http.HttpMethod;
import org.alienideology.jcord.JCord;

import static com.mashape.unirest.http.HttpMethod.*;

/**
 * HttpPath - Used to set HttpRequest Paths and request them.
 *
 * @author AlienIdeology
 */
// TODO: Implements all endpoints
public final class HttpPath {

    public final static String DISCORD_URL = "https://discordapp.com/";
    public final static String DISCORD_API_URL = DISCORD_URL+"api/v" + JCord.DISCORD_GATEWAY_VERSION;
    public final static String DISCORD_CDN_URL = "https://cdn.discordapp.com/";

    public static class EndPoint {

        /**
         * Application Icon - Parameters: Application ID, Icon Hash
         */
        public final static String APPLICATION_ICON = DISCORD_CDN_URL+"app-icons/%s/%s.png";

        /**
         * Avatar - Parameters: ID, Avatar Hash
         */
        public final static String AVATAR = DISCORD_CDN_URL+"avatars/%s/%s.%s";

        /**
         * Default Avatar - Parameter: Avatar Hash
         */
        public final static String DEFAULT_AVATAR = DISCORD_CDN_URL+"embed/avatars/%s.png";

        /**
         * Guild Icon - Parameters: ID, Icon Hash
         */
        public final static String GUILD_ICON = DISCORD_CDN_URL+"icons/%s/%s.png";

        /**
         * GuildEmoji Icon - Parameter: ID
         */
        public final static String EMOJI_ICON = DISCORD_CDN_URL+"EMOJIS/%s.png";

        /**
         * Discord Emojis Json
         */
        public final static String EMOJI_URL = "https://raw.githubusercontent.com/emojione/emojione/master/emoji.json";

    }

    public static class Gateway {

        public final static HttpPath GET_GATEWAY = new HttpPath(GET, "/gateway");
        public final static HttpPath GET_GATEWAY_BOT = new HttpPath(GET, "/gateway/bot");

    }

    public static class Guild {

        /* Basic Action */
        public final static HttpPath GET_GUILD = new HttpPath(GET, "/guilds/{guild.key}");
        public final static HttpPath MODIFY_GUILD = new HttpPath(PATCH, "/guilds/{guild.key}");
        public final static HttpPath DELETE_GUILD = new HttpPath(DELETE, "/guilds/{guild.key}"); // Client Only

        /* Channel Action */
        public final static HttpPath GET_GUILD_CHANNELS = new HttpPath(GET, "/guilds/{guild.key}/channels");
        public final static HttpPath CREATE_GUILD_CHANNEL = new HttpPath(POST, "/guilds/{guild.key}/channels");
        public final static HttpPath MODIFY_GUILD_CHANNEL_POSITION = new HttpPath(PATCH, "/guilds/{guild.key}/channels"); // Unused

        /* Member Action */
        public final static HttpPath GET_GUILD_MEMBER = new HttpPath(GET, "/guilds/{guild.key}/members/{user.key}"); // Unused
        public final static HttpPath LIST_GUILD_MEMBERS = new HttpPath(GET, "/guilds/{guild.key}/members");

        public final static HttpPath ADD_GUILD_MEMBER = new HttpPath(PUT, "/guilds/{guild.key}/members/{user.key}"); // OAuth Only
        public final static HttpPath MODIFY_GUILD_MEMBER = new HttpPath(PATCH, "/guilds/{guild.key}/members/{user.key}");
        public final static HttpPath REMOVE_GUILD_MEMBER = new HttpPath(DELETE, "/guilds/{guild.key}/members/{user.key}");
        public final static HttpPath MODIFY_CURRENT_USER_NICK = new HttpPath(PATCH, "/guilds/{guild.key}/members/@me/nick");
        public final static HttpPath ADD_GUILD_MEMBER_ROLE = new HttpPath(PUT, "/guilds/{guild.key}/members/{user.key}/roles/{role.key}"); // Unused
        public final static HttpPath REMOVE_GUILD_MEMBER_ROLE = new HttpPath(DELETE, "/guilds/{guild.key}/members/{user.key}/roles/{role.key}"); // Unused

        /* Role Action */
        public final static HttpPath GET_GUILD_ROLES = new HttpPath(GET, "/guilds/{guild.key}/roles"); // Unused
        public final static HttpPath CREATE_GUILD_ROLE = new HttpPath(POST, "/guilds/{guild.key}/roles");
        public final static HttpPath MODIFY_GUILD_ROLE_POSITIONS = new HttpPath(PATCH, "/guilds/{guild.key}/roles");
        public final static HttpPath MODIFY_GUILD_ROLE = new HttpPath(PATCH, "/guilds/{guild.key}/roles/{role.key}");
        public final static HttpPath DELETE_GUILD_ROLE = new HttpPath(DELETE, "/guilds/{guild.key}/roles/{role.key}");

        /* Ban Action */
        public final static HttpPath GET_GUILD_BANS = new HttpPath(GET, "/guilds/{guild.key}/bans");
        public final static HttpPath CREATE_GUILD_BAN = new HttpPath(PUT, "/guilds/{guild.key}/bans/{user.key}");
        public final static HttpPath REMOVE_GUILD_BAN = new HttpPath(DELETE, "/guilds/{guild.key}/bans/{user.key}");

        /* Prune Action */
        public final static HttpPath GET_GUILD_PRUNE_COUNT = new HttpPath(GET, "/guilds/{guild.key}/prune?days={int}");
        public final static HttpPath BEGIN_GUILD_PRUNE = new HttpPath(POST, "/guilds/{guild.key}/prune?days={int}");

        /* GuildEmoji Action */
        // Client Only
        public final static HttpPath CREATE_EMOJI = new HttpPath(POST, "guilds/{guild_id}/EMOJIS");
        public final static HttpPath MODIFY_EMOJI = new HttpPath(PATCH, "guilds/{guild_id}/EMOJIS/{emote_id}");
        public final static HttpPath DELETE_EMOJI = new HttpPath(DELETE, "guilds/{guild_id}/EMOJIS/{emote_id}");

        /* Integration Action */
        public final static HttpPath GET_GUILD_INTEGRATIONS = new HttpPath(GET, "/guilds/{guild.key}/integrations");
        public final static HttpPath CREATE_GUILD_INTEGRATION = new HttpPath(POST, "/guilds/{guild.key}/integrations");
        public final static HttpPath MODIFY_GUILD_INTEGRATION = new HttpPath(PATCH, "/guilds/{guild.key}/integrations/{integration.key}");
        public final static HttpPath DELETE_GUILD_INTEGRATION = new HttpPath(DELETE, "/guilds/{guild.key}/integrations/{integration.key}");
        public final static HttpPath SYNC_GUILD_INTEGRATION = new HttpPath(GET, "/guilds/{guild.key}/integrations/{integration.key}/sync");

        /* Other Action */
        public final static HttpPath LIST_VOICE_REGIONS = new HttpPath(GET, "/voice/regions"); // Unused
        public final static HttpPath GET_GUILD_VOICE_REGIONS = new HttpPath(GET, "/guilds/{guild.key}/regions"); // Unused (Cached)
        public final static HttpPath GET_GUILD_EMBED = new HttpPath(GET, "/guilds/{guild.key}/embed"); // Unused (Cached)
        public final static HttpPath MODIFY_GUILD_EMBED = new HttpPath(PATCH, "/guilds/{guild.key}/embed");

    }

    public static class Channel {

        /* Basic Action */
        public final static HttpPath GET_CHANNEL = new HttpPath(GET, "/channels/{channel.key}");
        public final static HttpPath MODIFY_CHANNEL = new HttpPath(PATCH, "/channels/{channel.key}");
        public final static HttpPath DELETE_CHANNEL = new HttpPath(DELETE, "/channels/{channel.key}");
        public final static HttpPath TRIGGER_TYPING_INDICATOR = new HttpPath(POST, "/channels/{channel.key}/typing");

        /* Message Action */
        public final static HttpPath GET_CHANNEL_MESSAGES = new HttpPath(GET, "/channels/{channel.key}/messages?limit={int}");
        public final static HttpPath GET_CHANNEL_MESSAGES_AROUND = new HttpPath(GET, "/channels/{channel.key}/messages?limit={int}&around={snowflake.message}");
        public final static HttpPath GET_CHANNEL_MESSAGES_BEFORE = new HttpPath(GET, "/channels/{channel.key}/messages?limit={int}&before={snowflake.message}");
        public final static HttpPath GET_CHANNEL_MESSAGES_AFTER = new HttpPath(GET, "/channels/{channel.key}/messages?limit={int}&after={snowflake.message}");
        public final static HttpPath GET_CHANNEL_MESSAGE = new HttpPath(GET, "/channels/{channel.key}/messages/{snowflake.message}");
        public final static HttpPath CREATE_MESSAGE = new HttpPath(POST, "/channels/{channel.key}/messages");
        public final static HttpPath EDIT_MESSAGE = new HttpPath(PATCH, "/channels/{channel.key}/messages/{message.key}");
        public final static HttpPath EDIT_MESSAGE_CONTENT = new HttpPath(PATCH, "/channels/{channel.key}/messages/{message.key}?content={string}"); // Unused
        public final static HttpPath EDIT_MESSAGE_EMBED = new HttpPath(PATCH, "/channels/{channel.key}/messages/{message.key}?embed={object}"); // Unused
        public final static HttpPath DELETE_MESSAGE = new HttpPath(DELETE, "/channels/{channel.key}/messages/{message.key}");
        public final static HttpPath BULK_DELETE_MESSAGE = new HttpPath(POST, "/channels/{channel.key}/messages/bulk-delete");

        /* Reaction Action */
        public final static HttpPath CREATE_REACTION = new HttpPath(PUT, "/channels/{channel.key}/messages/{message.key}/reactions/{emoji}/@me");
        public final static HttpPath DELETE_REACTION_BY_SELF = new HttpPath(DELETE, "/channels/{channel.key}/messages/{message.key}/reactions/{emoji}/@me");
        public final static HttpPath DELETE_REACTION_BY_USER = new HttpPath(DELETE, "/channels/{channel.key}/messages/{message.key}/reactions/{emoji}/{user.key}");
        public final static HttpPath GET_REACTIONS = new HttpPath(GET, "/channels/{channel.key}/messages/{message.key}/reactions/{emoji}");
        public final static HttpPath DELETE_REACTION_ALL = new HttpPath(DELETE, "/channels/{channel.key}/messages/{message.key}/reactions");

        /* Permission Action */
        public final static HttpPath EDIT_CHANNEL_PERMISSIONS = new HttpPath(PUT, "/channels/{channel.key}/permissions/{overwrite.key}");
        public final static HttpPath DELETE_CHANNEL_PERMISSION = new HttpPath(DELETE, "/channels/{channel.key}/permissions/{overwrite.key}");

        /* Pin Action */
        public final static HttpPath GET_PINNED_MESSAGES = new HttpPath(GET, "/channels/{channel.key}/pins");
        public final static HttpPath ADD_PINNED_MESSAGE = new HttpPath(PUT, "/channels/{channel.key}/pins/{message.key}");
        public final static HttpPath DELETE_PINNED_MESSAGE = new HttpPath(DELETE, "/channels/{channel.key}/pins/{message.key}");

    }

    public static class Group {

        /* Recipient Action */
        public final static HttpPath CREATE_GROUP_DM = new HttpPath(POST, "/users/@me/channels");
        public final static HttpPath ADD_RECIPIENT = new HttpPath(PUT, "/channels/{channel.key}/recipients/{user.key}");
        public final static HttpPath DELETE_RECIPIENT = new HttpPath(DELETE, "/channels/{channel.key}/recipients/{user.key}");

    }

    public static class User {

        /* Current User Action */
        // Client Only
        public final static HttpPath GET_CURRENT_USER = new HttpPath(GET, "/users/@me"); // Unused
        public final static HttpPath MODIFY_CURRENT_USER = new HttpPath(PATCH, "/users/@me");
        public final static HttpPath GET_CURRENT_USER_GUILDS = new HttpPath(GET, "/users/@me/guilds");
        public final static HttpPath LEAVE_GUILD = new HttpPath(DELETE, "/users/@me/guilds/{guild.key}");
        public final static HttpPath CREATE_DM = new HttpPath(POST, "/users/@me/channels");
        public final static HttpPath GET_CURRENT_USER_CONNECTIONS = new HttpPath(GET, "/users/@me/connections");

        /* User Action */
        public final static HttpPath GET_USER = new HttpPath(GET, "/users/{user.key}");

    }

    public static class Invite {

        public final static HttpPath GET_GUILD_INVITES = new HttpPath(GET, "/guilds/{guild.key}/invites");
        public final static HttpPath GET_CHANNEL_INVITES = new HttpPath(GET, "/channels/{channel.key}/invites");
        public final static HttpPath GET_INVITE = new HttpPath(GET, "/invites/{invite.code}");

        public final static HttpPath CREATE_CHANNEL_INVITE = new HttpPath(POST, "/channels/{channel.key}/invites");
        public final static HttpPath DELETE_INVITE = new HttpPath(DELETE, "/invites/{invite.code}");
        public final static HttpPath ACCEPT_INVITE = new HttpPath(POST, "/invites/{invite.code}"); // OAuth Only

    }

    public static class Audit {

        public final static HttpPath GET_GUILD_AUDIT_LOG = new HttpPath(GET, "/guilds/{guild.id}/audit-logs?limit={int}");
        public final static HttpPath GET_GUILD_AUDIT_LOG_BEFORE = new HttpPath(GET, "/guilds/{guild.id}/audit-logs" +
                "?before={snowflake.audit_entry}" +
                "&limit={int}");
        public final static HttpPath GET_GUILD_AUDIT_LOG_FULL = new HttpPath(GET, "/guilds/{guild.id}/audit-log" + // Unused
                "?user_id={snowflake.user}" +
                "&action_type={int}" +
                "&before={snowflake.audit_entry}" +
                "&limit={int}");

    }

    public static class OAuth {

        public final static HttpPath GET_CURRENT_APPLICATION_INFORMATION = new HttpPath(GET, "/oauth2/applications/@me");

        public final static String AUTHORIZATION = DISCORD_API_URL + "/oauth2/authorize";

        public final static HttpPath TOKEN = new HttpPath(POST,
                "/oauth2/token?client_id={client.key}&client_secret={client.secret}" +
                "&code={authorize.code}&redirect_uri={callback.uri}&grant_type=authorization_code");

        public final static HttpPath REVOCATION = new HttpPath(POST,
                "/oauth2/token/revoke?client_id={client.key}&client_secret={client.secret}" +
                "&refresh_token={refresh.token}&grant_type=refresh_token");

    }

    public static class Webhook {

        /* Webhook Getters */
        public final static HttpPath GET_GUILD_WEBHOOKS = new HttpPath(GET, "/guilds/{channel.key}/webhooks"); // Unused
        public final static HttpPath GET_CHANNEL_WEBHOOKS = new HttpPath(GET, "/channels/{channel.key}/webhooks");
        public final static HttpPath GET_WEBHOOK = new HttpPath(GET, "/webhooks/{webhook.key}");
        public final static HttpPath GET_WEBHOOK_WITH_TOKEN = new HttpPath(GET, "/webhooks/{webhook.key}/{webhook.token}"); // Unused

        /* Webhook Actions */
        public final static HttpPath CREATE_WEBHOOK = new HttpPath(POST, "/channels/{channel.key}/webhooks");
        public final static HttpPath MODIFY_WEBHOOK = new HttpPath(PATCH, "/webhooks/{webhook.key}");
        public final static HttpPath MODIFY_WEBHOOK_WITH_TOKEN = new HttpPath(PATCH, "/webhooks/{webhook.key}/{webhook.token}"); // Unused
        public final static HttpPath EXECUTE_WEBHOOK = new HttpPath(POST, "/webhooks/{webhook.key}/{webhook.token}");
        public final static HttpPath DELETE_WEBHOOK = new HttpPath(DELETE, "/webhooks/{webhook.key}");
        public final static HttpPath DELETE_WEBHOOK_WITH_TOKEN = new HttpPath(DELETE, "/webhooks/{webhook.key}/{webhook.token}"); // Unused

    }

    private final HttpMethod method;
    private String path;

    public HttpPath(HttpMethod method, String path) {
        this.method = method;
        this.path = DISCORD_API_URL + path;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "HttpPath{" +
                "event=" + method +
                ", path='" + path + '\'' +
                '}';
    }
}
