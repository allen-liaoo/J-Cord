package org.alienideology.jcord.util;

import java.util.Arrays;
import java.util.List;

public class UserUtil {

    private static final int MIN_USERNAME_LENGTH = 2;
    private static final int MAX_USERNAME_LENGTH = 32;
    private static final List<Character> DISSALOWED_CHARS = Arrays.asList('@', '`', ':', '#');
    private static final List<String> DISSALOWED_NAMES = Arrays.asList("DISCORDTAG", "EVERYONE", "HERE");

    public static boolean validUsername(String str) {
        return str.length() >= MIN_USERNAME_LENGTH && str.length() <= MAX_USERNAME_LENGTH && !contains(str, DISSALOWED_CHARS) && DISSALOWED_NAMES.contains(str.trim().toUpperCase());
    }

    private static boolean contains(String str, List<Character> arg) {
        for (char c : str.toCharArray()) {
            if (arg.contains(c))
                return true;
        }
        return false;
    }
}
