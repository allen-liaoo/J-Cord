package org.alienideology.jcord;

import com.mashape.unirest.http.Unirest;
import org.alienideology.jcord.handle.EmojiTable;
import org.alienideology.jcord.util.log.JCordLogger;
import org.alienideology.jcord.util.log.LogMode;

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
    public final static JCordLogger LOG = new JCordLogger("JCord");

    /**
     * Global Emoji Table
     */
    public final static EmojiTable EMOJI_TABLE = new EmojiTable();

    static {
        Unirest.setDefaultHeader("User-Agent", USER_AGENT);
    }

}
