package org.alienideology.jcord.gateway;

import java.util.Arrays;

/**
 * OP Code sent by Discord GateWay server.
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
    HEARTBEAT_ACK,
    UNKNOWN;

    public static int getInt (OPCode code) {
        return Arrays.asList(OPCode.values()).indexOf(code);
    }

    public static OPCode getCode (int value) {
        if (value >= OPCode.values().length || value < 0) return UNKNOWN;
        return OPCode.values()[value];
    }

}
