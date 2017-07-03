package org.alienideology.jcord;

import org.alienideology.jcord.handle.EmojiTable;
import org.alienideology.jcord.util.log.JCordLogger;
import org.alienideology.jcord.util.log.LogMode;

/**
 * JCord - Information about this library.
 *
 * @author AlienIdeology
 */
public final class JCord {

    public final static String VERSION = "0.0.7";
    public final static JCordLogger LOGGER = new JCordLogger("JCord");

    public final static EmojiTable EMOJI_TABLE = new EmojiTable();

}
