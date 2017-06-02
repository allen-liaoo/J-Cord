package org.alienideology.jcord.gateway;

import java.util.Arrays;

/**
 * @author AlienIdeology
 */
public enum OPCode {

    DISPATCH,
    HEARTBEAT,
    IDENTIFY,
    STATUS_UPDATE,
    VOICE_STATE_UPDATE,
    VOICE_SERVER_PING,
    RESUME,
    RECONNECT,
    REQUEST_GUILD_MEMBERS,
    INVALID_SESSION,
    HELLO,
    HEARTBEAT_ACK;

    public static int getInt (OPCode code) {
        return Arrays.asList(OPCode.values()).indexOf(code);
    }

    public static OPCode getCode (int value) {
        return OPCode.values()[value];
    }

}
