package org.alienideology.jcord.gateway;

import com.mashape.unirest.http.HttpMethod;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import org.alienideology.jcord.Identity;
import org.alienideology.jcord.JCord;
import org.json.JSONObject;

import java.util.IllegalFormatException;

import static com.mashape.unirest.http.HttpMethod.*;

/**
 * HttpPath - Used to set HttpRequest Paths and request them
 * @author AlienIdeology
 */
public final class HttpPath {

    public final static String APIURL = "https://discordapp.com/api";

    public static class Gateway {
        public final static HttpPath GET_BOT = new HttpPath(GET, "/gateway/bot");
    }

    public static class Guild {

        /* Basic Action */
        public final static HttpPath GET_GUILD = new HttpPath(GET, "/guilds/{guild.id}");
        public final static HttpPath MODIFY_GUILD = new HttpPath(PATCH, "/guilds/{guild.id}");
        public final static HttpPath DELETE_GUILD = new HttpPath(DELETE, "/guilds/{guild.id}");

        /* Channel Action */
        public final static HttpPath GET_GUILD_CHANNELS = new HttpPath(GET, "/guilds/{guild.id}/channels");
        public final static HttpPath CREATE_GUILD_CHANNELS = new HttpPath(POST, "/guilds/{guild.id}/channels");
        public final static HttpPath MODIFY_GUILD_CHANNEL_POSITION = new HttpPath(PATCH, "/guilds/{guild.id}/channels");

        /* Member Action */
        public final static HttpPath GET_GUILD_MEMBER = new HttpPath(GET, "/guilds/{guild.id}/members/{user.id}");
        public final static HttpPath LIST_GUILD_MEMBERS = new HttpPath(GET, "/guilds/{guild.id}/members");
        public final static HttpPath ADD_GUILD_MEMBER = new HttpPath(PUT, "/guilds/{guild.id}/members/{user.id}");
        public final static HttpPath MODIFY_GUILD_MEMBER = new HttpPath(PATCH, "/guilds/{guild.id}/members/{user.id}");
        public final static HttpPath REMOVE_GUILD_MEMBER = new HttpPath(DELETE, "/guilds/{guild.id}/members/{user.id}");
        public final static HttpPath MODIFY_CURRENT_USER_NICK = new HttpPath(PATCH, "/guilds/{guild.id}/members/@me/nick");
        public final static HttpPath ADD_GUILD_MEMBER_ROLE = new HttpPath(PUT, "/guilds/{guild.id}/members/{user.id}/roles/{role.id}");
        public final static HttpPath REMOVE_GUILD_MEMBER_ROLE = new HttpPath(DELETE, "/guilds/{guild.id}/members/{user.id}/roles/{role.id}");

        /* Role Action */
        public final static HttpPath GET_GUILD_ROLES = new HttpPath(GET, "/guilds/{guild.id}/roles");
        public final static HttpPath CREATE_GUILD_ROLE = new HttpPath(POST, "/guilds/{guild.id}/roles");
        public final static HttpPath MODIFY_GUILD_ROLE_POSITIONS = new HttpPath(PATCH, "/guilds/{guild.id}/roles");
        public final static HttpPath MODIFY_GUILD_ROLE = new HttpPath(PATCH, "/guilds/{guild.id}/roles/{role.id}");
        public final static HttpPath DELETE_GUILD_ROLE = new HttpPath(DELETE, "/guilds/{guild.id}/roles/{role.id}");

        /* Ban Action */
        public final static HttpPath GET_GUILD_BANS = new HttpPath(GET, "/guilds/{guild.id}/bans");
        public final static HttpPath CREATE_GUILD_BANS = new HttpPath(PUT, "/guilds/{guild.id}/bans/{user.id}");
        public final static HttpPath REMOVE_GUILD_BANS = new HttpPath(DELETE, "/guilds/{guild.id}/bans/{user.id}");

        /* Prune Action */
        public final static HttpPath GET_GUILD_PRUNE_COUNT = new HttpPath(GET, "/guilds/{guild.id}/prune?days={days}");
        public final static HttpPath BEGIN_GUILD_PRUNE = new HttpPath(POST, "/guilds/{guild.id}/prune?days={days}");

        /* Integration Action */
        public final static HttpPath GET_GUILD_INTEGRATIONS = new HttpPath(GET, "/guilds/{guild.id}/integrations");
        public final static HttpPath CREATE_GUILD_INTEGRATION = new HttpPath(POST, "/guilds/{guild.id}/integrations");
        public final static HttpPath MODIFY_GUILD_INTEGRATION = new HttpPath(PATCH, "/guilds/{guild.id}/integrations/{integration.id}");
        public final static HttpPath DELETE_GUILD_INTEGRATION = new HttpPath(DELETE, "/guilds/{guild.id}/integrations/{integration.id}");
        public final static HttpPath SYNC_GUILD_INTEGRATION = new HttpPath(GET, "/guilds/{guild.id}/integrations/{integration.id}/sync");

        /* Invite Action */
        public final static HttpPath GET_INVITE = new HttpPath(GET, "/invites/{invite.code}");
        public final static HttpPath DELETE_INVITE = new HttpPath(DELETE, "/invites/{invite.code}");
        public final static HttpPath ACCEPT_INVITE = new HttpPath(POST, "/invites/{invite.code}");

        /* Other Action */
        public final static HttpPath LIST_VOICE_REGIONS = new HttpPath(GET, "/voice/regions");
        public final static HttpPath GET_GUILD_VOICE_REGIONS = new HttpPath(GET, "/guilds/{guild.id}/regions");
        public final static HttpPath GET_GUILD_INVITES = new HttpPath(GET, "/guilds/{guild.id}/invites");
        public final static HttpPath GET_GUILD_EMBED = new HttpPath(GET, "/guilds/{guild.id}/embed");
        public final static HttpPath MODIFY_GUILD_EMBED = new HttpPath(PATCH, "/guilds/{guild.id}/embed");

    }

    public static class Channel {

        /* Basic Action */
        public final static HttpPath GET_CHANNEL = new HttpPath(GET, "/channels/{channel.id}");
        public final static HttpPath MODIFY_CHANNEL = new HttpPath(PATCH, "/channels/{channel.id}");
        public final static HttpPath DELETE_CHANNEL = new HttpPath(DELETE, "/channels/{channel.id}");

        /* Message Action */
        public final static HttpPath GET_CHANNEL_MESSAGES = new HttpPath(GET, "/channels/{channel.id}/messages?limit={limit}");
        public final static HttpPath GET_CHANNEL_MESSAGES_AROUND = new HttpPath(GET, "/channels/{channel.id}/messages?limit={limit}&around={around}");
        public final static HttpPath GET_CHANNEL_MESSAGES_BEFORE = new HttpPath(GET, "/channels/{channel.id}/messages?limit={limit}&before={before}");
        public final static HttpPath GET_CHANNEL_MESSAGES_AFTER = new HttpPath(GET, "/channels/{channel.id}/messages?limit={limit}&after={after}");
        public final static HttpPath GET_CHANNEL_MESSAGE = new HttpPath(GET, "/channels/{channel.id}/messages/{message.id}");
        public final static HttpPath CREATE_MESSAGE = new HttpPath(POST, "/channels/{channel.id}/messages");
        public final static HttpPath EDIT_MESSAGE = new HttpPath(PATCH, "/channels/{channel.id}/messages/{message.id}");
        public final static HttpPath EDIT_MESSAGE_CONTENT = new HttpPath(PATCH, "/channels/{channel.id}/messages/{message.id}?content={content}");
        public final static HttpPath EDIT_MESSAGE_EMBED = new HttpPath(PATCH, "/channels/{channel.id}/messages/{message.id}?embed={embed}");
        public final static HttpPath DELETE_MESSAGE = new HttpPath(DELETE, "/channels/{channel.id}/messages/{message.id}");
        public final static HttpPath BULK_DELETE_MESSAGE = new HttpPath(POST, "/channels/{channel.id}/messages/bulk-delete");

        /* Reaction Action */
        public final static HttpPath CREATE_REACTION = new HttpPath(PUT, "/channels/{channel.id}/messages/{message.id}/reactions/{emoji}/@me");
        public final static HttpPath DELETE_REACTION_BY_SELF = new HttpPath(DELETE, "/channels/{channel.id}/messages/{message.id}/reactions/{emoji}/@me");
        public final static HttpPath DELETE_REACTION_BY_USER = new HttpPath(DELETE, "/channels/{channel.id}/messages/{message.id}/reactions/{emoji}/{user.id}");
        public final static HttpPath GET_REACTIONS = new HttpPath(GET, "/channels/{channel.id}/messages/{message.id}/reactions/{emoji}");
        public final static HttpPath DELETE_REACTIOM_ALL = new HttpPath(DELETE, "/channels/{channel.id}/messages/{message.id}/reactions");

        /* Permission Action */
        public final static HttpPath EDIT_CHANNE_PERMISSIONS = new HttpPath(PUT, "/channels/{channel.id}/permissions/{overwrite.id}");
        public final static HttpPath DELETE_CHANNE_PERMISSION = new HttpPath(DELETE, "/channels/{channel.id}/permissions/{overwrite.id}");

        /* Invite Action */
        public final static HttpPath GET_CHANNE_INVITES = new HttpPath(GET, "/channels/{channel.id}/invites");
        public final static HttpPath CREATE_CHANNE_INVITE = new HttpPath(POST, "/channels/{channel.id}/invites");

        /* Pin Action */
        public final static HttpPath GET_PINNED_MESSAGES = new HttpPath(GET, "/channels/{channel.id}/pins");
        public final static HttpPath ADD_PINNED_MESSAGE = new HttpPath(PUT, "/channels/{channel.id}/pins/{message.id}");
        public final static HttpPath DELETE_PINNED_MESSAGE = new HttpPath(DELETE, "/channels/{channel.id}/pins/{message.id}");

    }

    public static class Group {

        /* Recipient Action */
        public final static HttpPath CREATE_GROUP_DM = new HttpPath(POST, "/users/@me/channels");
        public final static HttpPath ADD_RECIPIENT = new HttpPath(PUT, "/channels/{channel.id}/recipients/{user.id}");
        public final static HttpPath DELETE_RECIPIENT = new HttpPath(DELETE, "/channels/{channel.id}/recipients/{user.id}");

    }

    public static class User {

        /* Current User Action */
        public final static HttpPath GET_CURRENT_USER = new HttpPath(GET, "/users/@me");
        public final static HttpPath MODIFY_CURRENT_USER = new HttpPath(PATCH, "/users/@me");
        public final static HttpPath GET_CURRENT_USER_GUILDS = new HttpPath(GET, "/users/@me/guilds");
        public final static HttpPath LEAVE_GUILD = new HttpPath(DELETE, "/users/@me/guilds/{guild.id}");
        public final static HttpPath GET_CURRENT_USER_DM = new HttpPath(DELETE, "/users/@me/channels");
        public final static HttpPath GET_CURRENT_USER_CONNECTIONS = new HttpPath(GET, "/users/@me/connections");

        /* User Action */
        public final static HttpPath GET_USER = new HttpPath(GET, "/users/{user.id}");

    }

    private final HttpMethod method;
    private String path;

    public HttpPath(HttpMethod method, String path) {
        this.method = method;
        this.path = APIURL + path;
    }

    public HttpRequest request(Identity identity, String... params) {
        String processedPath;
        try {
            processedPath = path.replaceAll("\\{(.+?)}", "%s");
            processedPath = String.format(processedPath, (Object[]) params);
        } catch (IllegalFormatException ife) {
            throw new IllegalArgumentException("[INTERNAL] Cannot perform an HttpRequest due to unmatched parameters!");
        }

        HttpRequest request = null;
        switch (method) {
            case GET:
                request = Unirest.get(processedPath); break;
            case HEAD:
                request = Unirest.head(processedPath); break;
            case POST:
                request = Unirest.post(processedPath); break;
            case PUT:
                request = Unirest.put(processedPath); break;
            case PATCH:
                request = Unirest.patch(processedPath); break;
            case DELETE:
                request = Unirest.delete(processedPath); break;
            case OPTIONS:
                request = Unirest.options(processedPath); break;
        }

        request.header("Authorization", identity.getToken())
                .header("User-Agent", "DiscordBot ($"+processedPath+", $"+ JCord.VERSION+")");
        return request;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

}
