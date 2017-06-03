package org.alienideology.jcord.gateway;

import com.mashape.unirest.http.HttpMethod;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequest;
import org.alienideology.jcord.Identity;

import static com.mashape.unirest.http.HttpMethod.*;

/**
 * Http Requests (GET, POST, etc) for Gateway
 * @author AlienIdeology
 */
public class HttpPath {

    public static String APIURL = "https://discordapp.com/api";

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
        public final static HttpPath GET_GUILD_PRUNE_COUNT = new HttpPath(GET, "/guilds/{guild.id}/prune");
        public final static HttpPath BEGIN_GUILD_PRUNE = new HttpPath(POST, "/guilds/{guild.id}/prune");

        /* Integration Action */
        public final static HttpPath GET_GUILD_INTEGRATIONS = new HttpPath(GET, "/guilds/{guild.id}/integrations");
        public final static HttpPath CREATE_GUILD_INTEGRATION = new HttpPath(POST, "/guilds/{guild.id}/integrations");
        public final static HttpPath MODIFY_GUILD_INTEGRATION = new HttpPath(PATCH, "/guilds/{guild.id}/integrations/{integration.id}");
        public final static HttpPath DELETE_GUILD_INTEGRATION = new HttpPath(DELETE, "/guilds/{guild.id}/integrations/{integration.id}");
        public final static HttpPath SYNC_GUILD_INTEGRATION = new HttpPath(GET, "/guilds/{guild.id}/integrations/{integration.id}/sync");

        /* Other Action */
        public final static HttpPath GET_GUILD_VOICE_REGIONS = new HttpPath(GET, "/guilds/{guild.id}/regions");
        public final static HttpPath GET_GUILD_INVITES = new HttpPath(GET, "/guilds/{guild.id}/invites");
        public final static HttpPath GET_GUILD_EMBED = new HttpPath(GET, "/guilds/{guild.id}/embed");
        public final static HttpPath MODIFY_GUILD_EMBED = new HttpPath(PATCH, "/guilds/{guild.id}/embed");

    }

    private final HttpMethod method;
    private String path;

    public HttpPath(HttpMethod method, String path) {
        this.method = method;
        this.path = APIURL + path;
    }

    public HttpRequest request(Identity identity, String... ids) {
        String processedPath = path.replaceAll("\\{(.+?)}", "%s");
        processedPath = String.format(processedPath, (Object[]) ids);
        path = processedPath;

        HttpRequest request = null;
        switch (method) {
            case GET:
                request = Unirest.get(path); break;
            case HEAD:
                request = Unirest.head(path); break;
            case POST:
                request = Unirest.post(path); break;
            case PUT:
                request = Unirest.put(path); break;
            case PATCH:
                request = Unirest.patch(path); break;
            case DELETE:
                request = Unirest.delete(path); break;
            case OPTIONS:
                request = Unirest.options(path); break;
        }
        request.header("Authorization", identity.getToken())
                .header("User-Agent", "DiscordBot ($"+path+", $"+")");
        return request;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

}
