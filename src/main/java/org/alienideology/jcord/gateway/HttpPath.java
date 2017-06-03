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

    private static String apiURL = "https://discordapp.com/api";

    public static class Guild {

        public final static HttpPath GET_GUILD = new HttpPath(GET, "/guilds/{guild.id}");

        public final static HttpPath CREATE_GUILD_ROLE = new HttpPath(POST, "/guilds/{guild.id}/roles");
        public final static HttpPath MODIFY_GUILD_ROLE = new HttpPath(PATCH, "/guilds/{guild.id}/roles/{role.id}");

    }

    private final HttpMethod method;
    private String path;

    public HttpPath(HttpMethod method, String path) {
        this.method = method;
        this.path = apiURL + path;
    }

    public HttpPath useId(String... ids) {
        String processedPath = path.replaceAll("\\{(.+?)}", "%s");
        processedPath = String.format(processedPath, (Object[]) ids);
        path = processedPath;
        return this;
    }

    public HttpRequest request(Identity identity) {
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

    public static String getApiURL() {
        return apiURL;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

}
