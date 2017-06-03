package org.alienideology.jcord.gateway;

import java.util.Arrays;

/**
 * OP Code sent by Discord GateWay server.
 * @author AlienIdeology
 */
public enum OPCode {

    DISPATCH (0),
    HEARTBEAT (1),
    IDENTIFY (2),
    STATUS_UPDATE (3),
    VOICE_STATE_UPDATE (4),
    VOICE_SERVER_PING (5),
    RESUME (6),
    RECONNECT (7),
    REQUEST_GUILD_MEMBERS (8),
    INVALID_SESSION (9),
    HELLO (10),
    HEARTBEAT_ACK (11),
    UNKNOWN (-1);

    public int key;

    OPCode(int key) {
        this.key = key;
    }

    public static OPCode getCode (int key) {
        if (key >= OPCode.values().length || key < 0) {
            return UNKNOWN;
        }
        return Arrays.stream(OPCode.values()).filter(op -> op.key == key).findFirst().get();
    }

}
