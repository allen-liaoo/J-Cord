package org.alienideology.jcord.bot;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.apache.commons.logging.impl.SimpleLog;

/**
 * PostAgent - An agent used to post bot status to bots websites.
 * @author AlienIdeology
 */
// TODO: Customizable post agent, accept arguments. Feature: Automatically post status on startup and status change (User join and leave)
public class PostAgent {

    public static SimpleLog LOG = new SimpleLog("Post-Agent");

    private IdentityImpl identity;
    private final String id;
    private int serverCount;

    /* Discord Bots */
    private final String DiscordBotsURL;

    /* Discord Bots List */
    private final String DiscordBotListURL;

    /* DiscordList Bots */
    private final String DiscordListBots;

    public PostAgent(IdentityImpl identity) {
        this.identity = identity;
        this.id = identity.getSelf().getId();
        this.serverCount = identity.getGuilds().size();
        this.DiscordBotsURL = "https://bots.discord.pw/api/bots/"+ id + "/stats";
        this.DiscordBotListURL =  "https://discordbots.org/api/bots/"+ id + "/stats";
        this.DiscordListBots = "https://bots.discordlist.net/api";
    }

    /**
     * Post bot status to DiscordBots.
     * @see <a href="https://bots.discord.pw/">DiscordBots Website</a>
     * @see <a href="https://bots.discord.pw/api">DiscordBots API</a>
     * @return PostAgent for chaining.
     */
    public PostAgent toDiscordBots(final String apiToken) {
        try {
            String response = Unirest.post(DiscordBotsURL)
                    .header("Authorization", apiToken)
                    .header("Content-GameType ", "application/json")
                    .field("server_count", serverCount)
                    .asString().getBody();

            LOG.info("[To DiscordBots] " + response);
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Post bot status to Discord Bot List.
     * @see <a href="https://discordbots.org/">Discord Bot List Website</a>
     * @see <a href="https://discordbots.org/api/docs">Discord Bot List API</a>
     * @return PostAgent for chaining.
     */
    public PostAgent toDiscordBotList(final String apiToken) {
        try {
            String response = Unirest.post(DiscordBotListURL)
                    .header("Authorization", apiToken)
                    .field("server_count", serverCount)
                    .asJson().getStatusText();

            LOG.info("[To Discord Bot List] " + response);
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Post bot status to Discord List Bots.
     * @see <a href="https://bots.discordlist.net/">Discord List Bots Website</a>
     * @see <a href="https://bots.discordlist.net/docs">Discord List Bots API</a>
     * @return PostAgent for chaining.
     */
    public PostAgent toDiscordListBots(final String apiToken) {
        try {
            String response = Unirest.post(DiscordListBots)
                    .header("Authorization", apiToken)
                    .field("token", identity.getToken())
                    .field("servers", serverCount)
                    .asString().getStatusText();

            LOG.info("[To Discord List Bots] " + response);
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return this;
    }


}
