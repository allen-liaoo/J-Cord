package org.alienideology.jcord.internal.object;

import java.util.Locale;

/**
 * Voice Region (Guild) - Used for guild voice channel connection.
 * @author AlienIdeology
 */
public enum Region {

    AMSTERDAM ("Amsterdam"),
    BRAZIL ("Brazil"),
    EU_WEST ("EU West"),
    EU_CENTRAL ("EU Central"),
    FRANKFURT ("Frankfurt"),
    LONDON ("London"),
    SINGAPORE ("Singapore"),
    SYDNEY ("Sydney"),
    US_EAST ("US East"),
    US_WEST ("US West"),
    US_CENTRAL ("US Central"),
    US_SOUTH ("US South"),

    VIP_AMSTERDAM ("Amsterdam (VIP)"),
    VIP_BRAZIL ("Brazil (VIP)"),
    VIP_EU_WEST ("EU West (VIP)"),
    VIP_EU_CENTRAL ("EU Central (VIP)"),
    VIP_FRANKFURT ("Frankfurt (VIP)"),
    VIP_LONDON ("London (VIP)"),
    VIP_SINGAPORE ("Singapore (VIP)"),
    VIP_SYDNEY ("Sydney (VIP)"),
    VIP_US_EAST ("US East (VIP)"),
    VIP_US_WEST ("US West (VIP)"),
    VIP_US_CENTRAL ("US Central (VIP)"),
    VIP_US_SOUTH ("US South (VIP)"),

    UNKNOWN ("Unknown");

    public final String key;
    public final String name;
    public final boolean isVIP;

    Region(String name)
    {
        this.key = toString();
        this.name = name;
        isVIP = key.startsWith("vip");
    }

    public static Region getByName(String name) {
        for (Region region : Region.values()) {
            if (region.toString().equals(name))
                return region;
        }
        return UNKNOWN;
    }

    public static Region getByKey(String key) {
        for (Region region : Region.values()) {
            if (region.key.equals(key))
                return region;
        }
        return UNKNOWN;
    }

    @Override
    public String toString() {
        return name().replaceAll("_", "-").toLowerCase(Locale.ENGLISH);
    }
}
