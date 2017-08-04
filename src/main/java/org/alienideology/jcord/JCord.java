package org.alienideology.jcord;

import com.mashape.unirest.http.Unirest;
import org.alienideology.jcord.util.log.Logger;

/**
 * JCord - Information about this library.
 *
 * @author AlienIdeology
 */
public final class JCord {

    /**
     * Name of the API
     */
    public final static String NAME = "J-Cord";

    /**
     * Version number of the API
     */
    public final static String VERSION = "0.1.3";

    /**
     * Github link of the API
     */
    public final static String GITHUB = "https://github.com/AlienIdeology/J-Cord";

    /**
     * Default user agent
     */
    public final static String USER_AGENT = "DiscordBot ("+GITHUB+", v"+VERSION+")";

    /**
     * Global logger for the API
     */
    public final static Logger LOG = new Logger("JCord");

    /**
     * The discord gateway version
     */
    public final static int DISCORD_GATEWAY_VERSION = 6;

    /**
     * The large threshold to distinguish between a large guild and a small guild.
     * Discord gateway will only send offline members if the guild is small.
     *
     * See <a href="https://discordapp.com/developers/docs/topics/gateway#gateway-identify-gateway-identify-structure>this documentation</a> (json key: "large_threshold")
     * for more information.
     */
    public final static int GUILD_MEMBERS_LARGE_THRESHOLD = 250;

    static {
        Unirest.setDefaultHeader("user-agent", USER_AGENT);
    }

}
