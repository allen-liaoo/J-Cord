package org.alienideology.jcord.internal.gateway;

/**
 * OPCode - OP Code sent by Discord GateWay server.
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
    GUILD_SYNC (12),
    UNKNOWN (-1);

    public int key;

    OPCode(int key) {
        this.key = key;
    }

    public static OPCode getByKey(int key) {
        for (OPCode op : values()) {
            if (op.key == key) return op;
        }
        return UNKNOWN;
    }

}
