package org.alienideology.jcord.util;

import java.util.regex.Pattern;

/**
 * MessageUtils - Message and String utilities
 * @author AlienIdeology
 */
public class MessageUtils {

    public static Pattern PATTERN_MENTION_USER = Pattern.compile("<@!?[0-9]+>");
    public static Pattern PATTERN_MENTION_CHANNEL = Pattern.compile("<#[0-9]+>");
    public static Pattern PATTERN_MENTION_ROLE = Pattern.compile("<@&[0-9]+>");
    public static Pattern PATTERN_MENTION_EMOJI = Pattern.compile("<:[A-z]+:[0-9]+>");

    public static Pattern PATTERN_IS_URL = Pattern.compile("(https|http|attachment)(://)(.+[^\\s])([.])(.+[^\\s])");

}
