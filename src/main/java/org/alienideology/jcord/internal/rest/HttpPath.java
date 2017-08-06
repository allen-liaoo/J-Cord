package org.alienideology.jcord.internal.rest;

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
         * Avatar - Parameters: ID, Avatar Hash
         */
        public final static String AVATAR = DISCORD_CDN_URL+"avatars/%s/%s.%s";

        /**
         * Default Avatar - Parameter: Avatar Hash
         */
        public final static String DEFAULT_AVATAR = DISCORD_CDN_URL+"embed/avatars/%s.png";

        /**
         * Application Icon - Parameters: Application ID, Icon Hash
         */
        public final static String APPLICATION_ICON = DISCORD_CDN_URL+"app-icons/%s/%s.png";

        /**
         * Guild Icon - Parameters: ID, Icon Hash
         */
        public final static String GUILD_ICON = DISCORD_CDN_URL+"icons/%s/%s.png";

        /**
         * GuildEmoji Icon - Parameter: ID
         */
        public final static String EMOJI_ICON = DISCORD_CDN_URL+"emojis/%s.png";

        /**
         * Group DM Icon - Parameters: ID, Icon Hash
         */
        public final static String GROUP_ICON = DISCORD_CDN_URL+"channel-icons/%s/%s.jpg";

        /**
         * Discord Emojis Json
         */
        public final static String EMOJI_URL = "https://raw.githubusercontent.com/emojione/emojione/master/emoji.json";

    }

    public static class Gateway {

        public final static HttpPath GET_GATEWAY = new HttpPath(GET, "/gateway");
        public final static HttpPath GET_GATEWAY_BOT = new HttpPath(GET, "/gateway/bot");

    }

    public static class User {

        public final static HttpPath GET_USER = new HttpPath(GET, "/users/{user.id}");
        public final static HttpPath MODIFY_CURRENT_USER = new HttpPath(PATCH, "/users/@me");
        public final static HttpPath LEAVE_GUILD = new HttpPath(DELETE, "/users/@me/guilds/{guild.id}");
        public final static HttpPath CREATE_DM = new HttpPath(POST, "/users/@me/channels");

    }

    // Client Only
    public static class Client {

        public final static HttpPath GET_USER = new HttpPath(GET, "/users/@me"); // Unused
        public final static HttpPath GET_PROFILE = new HttpPath(GET, "/users/{user_id}/profile"); // Unused
        public final static HttpPath GET_USER_GUILDS = new HttpPath(GET, "/users/@me/guilds"); // Unused

        public final static HttpPath GET_USER_CONNECTIONS = new HttpPath(GET, "/users/@me/connections");
        public final static HttpPath GET_FRIEND_SUGGESTIONS = new HttpPath(GET, "/friend-suggestions");
        public final static HttpPath GET_RECENT_MENTIONS = new HttpPath(GET, "/users/@me/mentions");

        public final static HttpPath GET_USER_SETTINGS = new HttpPath(GET, "/users/@me/settings"); // Unused
        public final static HttpPath MODIFY_USER_SETTINGS = new HttpPath(PATCH, "/users/@me/settings");
        public final static HttpPath MODIFY_GUILD_SETTINGS = new HttpPath(PATCH, "/users/@me/guilds/{guild_id}/settings");

        /* Relationship */
        public final static HttpPath GET_RELATIONSHIPS = new HttpPath(GET, "/users/@me/relationships"); // Unused
        public final static HttpPath GET_RELATIONSHIP = new HttpPath(GET, "/users/@me/relationships/{user_id}"); // Unused
        public final static HttpPath ADD_RELATIONSHIP = new HttpPath(PUT, "/users/@me/relationships/{user_id}");
        public final static HttpPath SEND_FRIEND_REQUEST = new HttpPath(POST, "/users/@me/relationships");
        public final static HttpPath DELETE_RELATIONSHIP = new HttpPath(DELETE, "/users/@me/relationships/{user_id}");
        
        /* Note */
        public final static HttpPath GET_NOTE = new HttpPath(GET, "/users/@me/notes/{user_id}"); // Unused
        public final static HttpPath SET_NOTE = new HttpPath(PUT, "/users/@me/notes/{user_id}");

    }

    public static class Guild {

        /* Basic Action */
        public final static HttpPath GET_GUILD = new HttpPath(GET, "/guilds/{guild.id}");
        public final static HttpPath CREATE_GUILD = new HttpPath(POST, "/guilds");
        public final static HttpPath MODIFY_GUILD = new HttpPath(PATCH, "/guilds/{guild.id}");
        public final static HttpPath DELETE_GUILD = new HttpPath(DELETE, "/guilds/{guild.id}"); // Client Only

        /* Channel Action */
        public final static HttpPath GET_GUILD_CHANNELS = new HttpPath(GET, "/guilds/{guild.id}/channels");
        public final static HttpPath CREATE_GUILD_CHANNEL = new HttpPath(POST, "/guilds/{guild.id}/channels");
        public final static HttpPath MODIFY_GUILD_CHANNEL_POSITION = new HttpPath(PATCH, "/guilds/{guild.id}/channels"); // Unused

        /* Member Action */
        public final static HttpPath GET_GUILD_MEMBER = new HttpPath(GET, "/guilds/{guild.id}/members/{user.id}"); // Unused
        public final static HttpPath LIST_GUILD_MEMBERS = new HttpPath(GET, "/guilds/{guild.id}/members");

        public final static HttpPath ADD_GUILD_MEMBER = new HttpPath(PUT, "/guilds/{guild.id}/members/{user.id}"); // OAuth Only
        public final static HttpPath MODIFY_GUILD_MEMBER = new HttpPath(PATCH, "/guilds/{guild.id}/members/{user.id}");
        public final static HttpPath REMOVE_GUILD_MEMBER = new HttpPath(DELETE, "/guilds/{guild.id}/members/{user.id}");
        public final static HttpPath MODIFY_CURRENT_USER_NICK = new HttpPath(PATCH, "/guilds/{guild.id}/members/@me/nick");
        public final static HttpPath ADD_GUILD_MEMBER_ROLE = new HttpPath(PUT, "/guilds/{guild.id}/members/{user.id}/roles/{role.id}"); // Unused
        public final static HttpPath REMOVE_GUILD_MEMBER_ROLE = new HttpPath(DELETE, "/guilds/{guild.id}/members/{user.id}/roles/{role.id}"); // Unused

        /* Role Action */
        public final static HttpPath GET_GUILD_ROLES = new HttpPath(GET, "/guilds/{guild.id}/roles"); // Unused
        public final static HttpPath CREATE_GUILD_ROLE = new HttpPath(POST, "/guilds/{guild.id}/roles");
        public final static HttpPath MODIFY_GUILD_ROLE_POSITIONS = new HttpPath(PATCH, "/guilds/{guild.id}/roles");
        public final static HttpPath MODIFY_GUILD_ROLE = new HttpPath(PATCH, "/guilds/{guild.id}/roles/{role.id}");
        public final static HttpPath DELETE_GUILD_ROLE = new HttpPath(DELETE, "/guilds/{guild.id}/roles/{role.id}");

        /* Ban Action */
        public final static HttpPath GET_GUILD_BANS = new HttpPath(GET, "/guilds/{guild.id}/bans");
        public final static HttpPath CREATE_GUILD_BAN = new HttpPath(PUT, "/guilds/{guild.id}/bans/{user.id}");
        public final static HttpPath REMOVE_GUILD_BAN = new HttpPath(DELETE, "/guilds/{guild.id}/bans/{user.id}");

        /* Prune Action */
        public final static HttpPath GET_GUILD_PRUNE_COUNT = new HttpPath(GET, "/guilds/{guild.id}/prune?days={int}");
        public final static HttpPath BEGIN_GUILD_PRUNE = new HttpPath(POST, "/guilds/{guild.id}/prune?days={int}");

        /* GuildEmoji Action */
        // Client Only
        public final static HttpPath CREATE_EMOJI = new HttpPath(POST, "/guilds/{guild_id}/emojis");
        public final static HttpPath MODIFY_EMOJI = new HttpPath(PATCH, "/guilds/{guild_id}/emojis/{emote_id}");
        public final static HttpPath DELETE_EMOJI = new HttpPath(DELETE, "/guilds/{guild_id}/emojis/{emote_id}");

        /* Integration Action */
        public final static HttpPath GET_GUILD_INTEGRATIONS = new HttpPath(GET, "/guilds/{guild.id}/integrations");
        public final static HttpPath CREATE_GUILD_INTEGRATION = new HttpPath(POST, "/guilds/{guild.id}/integrations");
        public final static HttpPath MODIFY_GUILD_INTEGRATION = new HttpPath(PATCH, "/guilds/{guild.id}/integrations/{integration.id}");
        public final static HttpPath DELETE_GUILD_INTEGRATION = new HttpPath(DELETE, "/guilds/{guild.id}/integrations/{integration.id}");
        public final static HttpPath SYNC_GUILD_INTEGRATION = new HttpPath(GET, "/guilds/{guild.id}/integrations/{integration.id}/sync");

        /* Guild Embed */
        public final static HttpPath GET_GUILD_EMBED = new HttpPath(GET, "/guilds/{guild.id}/embed"); // Unused
        public final static HttpPath MODIFY_GUILD_EMBED = new HttpPath(PATCH, "/guilds/{guild.id}/embed");
        public final static HttpPath GET_GUILD_EMED_WIDGET = new HttpPath(GET, "/guilds/{guild.id}/widget.json"); // Does not require authorization

        /* Voice Region */
        public final static HttpPath LIST_VOICE_REGIONS = new HttpPath(GET, "/voice/regions"); // Unused
        public final static HttpPath GET_GUILD_VOICE_REGIONS = new HttpPath(GET, "/guilds/{guild.id}/regions"); // Unused

        // Client Only
        public final static HttpPath SEARCH_MESSAGE = new HttpPath(GET, "/guilds/{guild_id}/messages/search");

    }

    public static class Channel {

        /* Basic Action */
        public final static HttpPath GET_CHANNEL = new HttpPath(GET, "/channels/{channel.id}");
        public final static HttpPath MODIFY_CHANNEL = new HttpPath(PATCH, "/channels/{channel.id}");
        public final static HttpPath DELETE_CHANNEL = new HttpPath(DELETE, "/channels/{channel.id}");
        public final static HttpPath TRIGGER_TYPING_INDICATOR = new HttpPath(POST, "/channels/{channel.id}/typing");

        /* Message Action */
        public final static HttpPath GET_CHANNEL_MESSAGES = new HttpPath(GET, "/channels/{channel.id}/messages?limit={int}");
        public final static HttpPath GET_CHANNEL_MESSAGES_AROUND = new HttpPath(GET, "/channels/{channel.id}/messages?limit={int}&around={message.id}");
        public final static HttpPath GET_CHANNEL_MESSAGES_BEFORE = new HttpPath(GET, "/channels/{channel.id}/messages?limit={int}&before={message.id}");
        public final static HttpPath GET_CHANNEL_MESSAGES_AFTER = new HttpPath(GET, "/channels/{channel.id}/messages?limit={int}&after={message.id}");
        public final static HttpPath GET_CHANNEL_MESSAGE = new HttpPath(GET, "/channels/{channel.id}/messages/{message.id}"); // Bot Only
        public final static HttpPath CREATE_MESSAGE = new HttpPath(POST, "/channels/{channel.id}/messages");
        public final static HttpPath EDIT_MESSAGE = new HttpPath(PATCH, "/channels/{channel.id}/messages/{message.id}");
        public final static HttpPath EDIT_MESSAGE_CONTENT = new HttpPath(PATCH, "/channels/{channel.id}/messages/{message.id}?content={string}"); // Unused
        public final static HttpPath EDIT_MESSAGE_EMBED = new HttpPath(PATCH, "/channels/{channel.id}/messages/{message.id}?embed={object}"); // Unused
        public final static HttpPath DELETE_MESSAGE = new HttpPath(DELETE, "/channels/{channel.id}/messages/{message.id}");
        public final static HttpPath BULK_DELETE_MESSAGE = new HttpPath(POST, "/channels/{channel.id}/messages/bulk-delete"); // Bot Only

        /* Reaction Action */
        public final static HttpPath CREATE_REACTION = new HttpPath(PUT, "/channels/{channel.id}/messages/{message.id}/reactions/{emoji}/@me");
        public final static HttpPath DELETE_REACTION_BY_SELF = new HttpPath(DELETE, "/channels/{channel.id}/messages/{message.id}/reactions/{emoji}/@me");
        public final static HttpPath DELETE_REACTION_BY_USER = new HttpPath(DELETE, "/channels/{channel.id}/messages/{message.id}/reactions/{emoji}/{user.id}");
        public final static HttpPath GET_REACTIONS = new HttpPath(GET, "/channels/{channel.id}/messages/{message.id}/reactions/{emoji}");
        public final static HttpPath DELETE_REACTION_ALL = new HttpPath(DELETE, "/channels/{channel.id}/messages/{message.id}/reactions");

        /* Permission Action */
        public final static HttpPath EDIT_CHANNEL_PERMISSIONS = new HttpPath(PUT, "/channels/{channel.id}/permissions/{overwrite.id}");
        public final static HttpPath DELETE_CHANNEL_PERMISSION = new HttpPath(DELETE, "/channels/{channel.id}/permissions/{overwrite.id}");

        /* Pin Action */
        public final static HttpPath GET_PINNED_MESSAGES = new HttpPath(GET, "/channels/{channel.id}/pins");
        public final static HttpPath ADD_PINNED_MESSAGE = new HttpPath(PUT, "/channels/{channel.id}/pins/{message.id}");
        public final static HttpPath DELETE_PINNED_MESSAGE = new HttpPath(DELETE, "/channels/{channel.id}/pins/{message.id}");

        /* Recipients */
        // Client Only
        public final static HttpPath GET_RECIPIENTS = new HttpPath(GET, "/channels/{channel_id}/recipients"); // Unused
        public final static HttpPath GET_RECIPIENT = new HttpPath(GET, "/channels/{channel_id}/recipients/{user_id}"); // Unused
        public final static HttpPath ADD_RECIPIENT = new HttpPath(PUT, "/channels/{channel_id}/recipients/{user_id}");
        public final static HttpPath REMOVE_RECIPIENT = new HttpPath(DELETE, "/channels/{channel_id}/recipients/{user_id}");

        /* Call */
        // Client only
        // Endpoint not available
        public final static HttpPath START_CALL = new HttpPath(POST, "/channels/{channel_id}/call/ring");
        public final static HttpPath STOP_CALL = new HttpPath(POST, "/channels/{channel_id}/call/stop_ringing"); // Deny/End Call
    }

    public static class Invite {

        public final static HttpPath GET_GUILD_INVITES = new HttpPath(GET, "/guilds/{guild.id}/invites");
        public final static HttpPath GET_CHANNEL_INVITES = new HttpPath(GET, "/channels/{channel.id}/invites");
        public final static HttpPath GET_INVITE = new HttpPath(GET, "/invites/{invite.code}");

        public final static HttpPath CREATE_CHANNEL_INVITE = new HttpPath(POST, "/channels/{channel.id}/invites");
        public final static HttpPath DELETE_INVITE = new HttpPath(DELETE, "/invites/{invite.code}");
        public final static HttpPath ACCEPT_INVITE = new HttpPath(POST, "/invites/{invite.code}"); // OAuth Only

    }

    public static class Audit {

        public final static HttpPath GET_GUILD_AUDIT_LOG = new HttpPath(GET, "/guilds/{guild.id}/audit-logs?limit={int}");
        public final static HttpPath GET_GUILD_AUDIT_LOG_BEFORE = new HttpPath(GET, "/guilds/{guild.id}/audit-logs" +
                "?before={audit_entry.id}" +
                "&limit={int}");
        public final static HttpPath GET_GUILD_AUDIT_LOG_FULL = new HttpPath(GET, "/guilds/{guild.id}/audit-log" + // Unused
                "?user_id={user.id}" +
                "&action_type={int}" +
                "&before={audit_entry.id}" +
                "&limit={int}");

    }

    public static class Application {

        // Bot Only
        public final static HttpPath GET_BOT_APPLICATION = new HttpPath(GET, "/oauth2/applications/@me");

        // Client Only
        public final static HttpPath GET_APPLICATIONS = new HttpPath(GET,    "/oauth2/applications");
        public final static HttpPath CREATE_APPLICATION = new HttpPath(POST,   "/oauth2/applications");
        public final static HttpPath GET_APPLICATION = new HttpPath(GET,    "/oauth2/applications/{app.id}");
        public final static HttpPath MODIFY_APPLICATION = new HttpPath(PUT,    "/oauth2/applications/{app.id}");
        public final static HttpPath DELETE_APPLICATION = new HttpPath(DELETE, "/oauth2/applications/{app.id}");

        public final static HttpPath CREATE_BOT_USER = new HttpPath(POST,   "/oauth2/applications/{app.id}/bot");

        public final static HttpPath RESET_APPLICATION_SECRET = new HttpPath(POST,   "/oauth2/applications/{app.id}/reset");
        public final static HttpPath RESET_BOT_TOKEN = new HttpPath(POST,   "/oauth2/applications/{app.id}/bot/reset");

        public final static HttpPath GET_AUTHORIZED_APPLICATIONS = new HttpPath(GET,    "/oauth2/tokens");
        public final static HttpPath GET_AUTHORIZED_APPLICATION = new HttpPath(GET,    "/oauth2/tokens/{auth.id}");
        public final static HttpPath DELETE_AUTHORIZED_APPLICATION = new HttpPath(DELETE, "/oauth2/tokens/{auth.id}");

    }

    public static class OAuth {

        public final static String AUTHORIZATION = DISCORD_API_URL + "/oauth2/authorize";

        public final static HttpPath TOKEN = new HttpPath(POST,
                "/oauth2/token?client_id={client.id}&client_secret={client.secret}" +
                "&code={authorize.code}&redirect_uri={callback.uri}&grant_type=authorization_code");

        public final static HttpPath REVOCATION = new HttpPath(POST,
                "/oauth2/token/revoke?client_id={client.id}&client_secret={client.secret}" +
                "&refresh_token={refresh.token}&grant_type=refresh_token");

    }

    public static class Webhook {

        /* Webhook Getters */
        public final static HttpPath GET_GUILD_WEBHOOKS = new HttpPath(GET, "/guilds/{channel.id}/webhooks"); // Unused
        public final static HttpPath GET_CHANNEL_WEBHOOKS = new HttpPath(GET, "/channels/{channel.id}/webhooks");
        public final static HttpPath GET_WEBHOOK = new HttpPath(GET, "/webhooks/{webhook.id}");
        public final static HttpPath GET_WEBHOOK_WITH_TOKEN = new HttpPath(GET, "/webhooks/{webhook.id}/{webhook.token}"); // Unused

        /* Webhook Actions */
        public final static HttpPath CREATE_WEBHOOK = new HttpPath(POST, "/channels/{channel.id}/webhooks");
        public final static HttpPath MODIFY_WEBHOOK = new HttpPath(PATCH, "/webhooks/{webhook.id}");
        public final static HttpPath MODIFY_WEBHOOK_WITH_TOKEN = new HttpPath(PATCH, "/webhooks/{webhook.id}/{webhook.token}"); // Unused
        public final static HttpPath EXECUTE_WEBHOOK = new HttpPath(POST, "/webhooks/{webhook.id}/{webhook.token}");
        public final static HttpPath DELETE_WEBHOOK = new HttpPath(DELETE, "/webhooks/{webhook.id}");
        public final static HttpPath DELETE_WEBHOOK_WITH_TOKEN = new HttpPath(DELETE, "/webhooks/{webhook.id}/{webhook.token}"); // Unused

    }

    public static class Empty
    {
        public final static HttpPath GET_PATH = new HttpPath(GET, "{}");
        public final static HttpPath POST_PATH = new HttpPath(POST, "{}");
        public final static HttpPath PUT_PATH = new HttpPath(PUT, "{}");
        public final static HttpPath PATCH_PATH = new HttpPath(PATCH, "{}");
        public final static HttpPath DELETE_PATH = new HttpPath(DELETE, "{}");

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
