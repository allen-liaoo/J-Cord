package org.alienideology.jcord.bot;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.body.MultipartBody;
import org.alienideology.jcord.Identity;
import org.alienideology.jcord.event.DispatcherAdaptor;
import org.alienideology.jcord.event.guild.GuildCreateEvent;
import org.alienideology.jcord.event.guild.GuildDeleteEvent;
import org.alienideology.jcord.event.guild.GuildUnavailableEvent;
import org.alienideology.jcord.util.log.LogLevel;
import org.alienideology.jcord.util.log.Logger;

import java.util.HashMap;
import java.util.function.Consumer;

/**
 * PostAgent - An agent used to post bot status to bots websites.
 *
 * @author AlienIdeology
 */
public class PostAgent {

    public static Logger LOG = new Logger("PostAgent");

    /**
     * Default API Post Agent for <a href="https://bots.discord.pw/">Discord Bots</a>.
     */
    public static PostAgent DISCORD_BOTS = new PostAgent()
            .setAPIName("Discord Bots")
            .setPostUrl("https://bots.discord.pw/api/bots/:bot:/stats")
            .setJsonShardIDKey("shard_id")
            .setJsonShardKey("shard_count")
            .setJsonServerKey("server_count");

    /**
     * Default API Post Agent for <a href="https://discordbots.org/">Discord Bot List</a>.
     */
    public static PostAgent DISCORD_BOT_LIST = new PostAgent()
            .setAPIName("Discord Bot List")
            .setPostUrl("https://discordbots.org/api/bots/:bot:/stats")
            .setJsonShardIDKey("shard_id")
            .setJsonShardKey("shard_count")
            .setJsonServerKey("server_count");

    /**
     * Default API Post Agent for <a href="https://bots.discordlist.net/">Discord List</a>.
     */
    public static PostAgent DISCORD_LIST = new PostAgent()
            .setAPIName("Discord List")
            .setPostUrl("https://bots.discordlist.net/api.php")
            .setJsonServerKey("servers");

    private Identity identity;
    private DispatcherAdaptor adaptor;

    private boolean autoPost;

    /* API */
    private String name;
    private String url;

    /* Customizable Fields */
    private String token;
    private String shard_id_key = "shard_id";
    private String shard_key = "shard_count";
    private String server_key = "server_count";
    private HashMap<String, Object> fields = new HashMap<>();

    public PostAgent() {
        this(null);
    }

    public PostAgent(Identity identity) {
        setIdentity(identity);
        adaptor = new DispatcherAdaptor() {
            @Override
            public void onGuildCreate(GuildCreateEvent event) {
                post();
            }

            @Override
            public void onGuildDelete(GuildDeleteEvent event) {
                post();
            }

            @Override
            public void onGuildUnavailable(GuildUnavailableEvent event) {
                post();
            }
        };
        setAutoPost(true);
    }

    /**
     * Set the identity of this post agent.
     * The identity is used to get shard, server count/status.
     *
     * @param identity The identity.
     * @return PostAgent for chaining.
     */
    public PostAgent setIdentity(Identity identity) {
        this.identity = identity;
        return this;
    }

    /**
     * Set if the post agent should automatically post status on events such as
     * <ul>
     *     <li>GuildCreateEvent</li>
     *     <li>GuildUnavailableEvent</li>
     *     <li>GuildDeleteEvent</li>
     * </ul>
     *
     * @param autoPost The boolean value.
     * @return PostAgent for chaining.
     */
    public PostAgent setAutoPost(boolean autoPost) {
        this.autoPost = autoPost;
        if (autoPost) {
            identity.getEventManager().registerDispatcherAdaptors(adaptor);
        } else {
            identity.getEventManager().deregisterDispatcherAdaptors(adaptor);
        }
        return this;
    }

    /**
     * Set the API service name. For bot, {@code Discord Bots}.
     * This event is used for logging.
     *
     * @param name The api name.
     * @return PostAgent for chaining.
     */
    public PostAgent setAPIName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Set the url the http post request will go to.
     * Use {@code :bot:} to automatically replace it with the ID.
     *
     * @param url The string url.
     * @return PostAgent for chaining.
     */
    public PostAgent setPostUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * Set the api token of the api service.
     *
     * @param token The string api token.
     * @return PostAgent for chaining.
     */
    public PostAgent setAPIToken(String token) {
        this.token = token;
        return this;
    }

    /**
     * Most api has a {@code shard key} field in their api docs.
     * Shard key is the 0 based ID of this identity.
     * You do not need to configure this if the identity is not sharded.
     * The default key will be {@code "shard_id"}.
     *
     * @param shard_id_key The new shard key json key.
     * @return PostAgent for chaining.
     */
    public PostAgent setJsonShardIDKey(String shard_id_key) {
        this.shard_id_key = shard_id_key;
        return this;
    }

    /**
     * Most api has a {@code shard count} field in their api docs.
     * Shard count is the total shards this bot have. You do not need to configure this if the identity is not sharded.
     * The default key will be {@code "shard_count"}.
     *
     * @param shard_key The new shard count json key.
     * @return PostAgent for chaining.
     */
    public PostAgent setJsonShardKey(String shard_key) {
        this.shard_key = shard_key;
        return this;
    }

    /**
     * Most api has a {@code server count} field in their api docs.
     * Server count is the total server this bot have in a shard (if sharded).
     * This key is required to send the status.
     * The default key will be {@code "server_count"}.
     *
     * @param server_key The new server count json key.
     * @return PostAgent for chaining.
     */
    public PostAgent setJsonServerKey(String server_key) {
        this.server_key = server_key;
        return this;
    }

    /**
     * Add a customized field to the post request.
     *
     * @param key The key of the field.
     * @param value The value, can be any object.
     * @return PostAgent for chaining.
     */
    public PostAgent addPostField(String key, Object value) {
        fields.put(key, value);
        return this;
    }

    /**
     * Post the bot status to the specified API.
     *
     * @param post The MultipartBody post request's consumer.
     *             The consumer can be used to update the post request and add fields.
     * @return PostAgent for chaining.
     */
    public PostAgent post(Consumer<MultipartBody> post) {
        try {
            url = url.replaceAll(":bot:", identity.getSelf().getId());
            MultipartBody body = Unirest.post(url)
                    .header("Authorization", token)
                    .header("Content-Type", "application/json")
                    .field(server_key, identity.getGuilds().size());

            /* Sharding */
            if (shard_key != null) {
//                if (identity.isSharded()) {
//                    body.field(shard_id_key, identity.getShardInt());
//                    body.field(shard_key, identity.getShards().size());
//                }
            }

            /* Custom Fields */
            for (String key : fields.keySet()) {
                body.field(key, fields.get(key));
            }

            /* Consumer */
            if (post != null) {
                post.accept(body);
            }

            String response = body.asString().getBody();
            LOG.log(LogLevel.INFO, "[RESPONSE] " + ((name == null) ? "" : "[API: " + name + "] ") + response);
        } catch (UnirestException e) {
            LOG.log(LogLevel.FETAL, e);
        }
        return this;
    }

    /**
     * Post the bot status to the specified API.
     *
     * @return PostAgent for chaining.
     */
    public PostAgent post() {
        return post(null);
    }

}
