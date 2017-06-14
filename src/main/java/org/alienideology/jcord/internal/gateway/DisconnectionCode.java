package org.alienideology.jcord.internal.gateway;

/**
 * DisconnectionCode - Disconnection Code sent when Discord gateway closed
 * @author AlienIdeology
 */
public enum DisconnectionCode {

    UNKNOWN (4000, "Unknown error. Try connecting?"),
    UNKNOWN_OPCODE (4001, "Invalid OP Code"),
    DECODE_ERROR (4002, "Invalid Payload"),
    NOT_AUTHENTICATED (4003, "Identification payload sent"),
    AUTHENTICATION_FAILED (4004, "Invalid Token"),
    ALREADY_AUTHENTICATED (4005, "More than 1 identify payload sent"),
    INVALID_SEQUENCE (4007, "Invalid sequence for resuming session"),
    RATE_LIMITED (4008, "Payloads are sent too quickly"),
    SESSION_TIMEOUT (4009, "Session timeout. Try reconnect and start new section"),
    INVALID_SHARD (4010, "Invalid shard when identifying"),
    SHARDING_REQUIRED (4011, "Requires Sharding");

    public final int code;
    public final String message;

    DisconnectionCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static DisconnectionCode getCode (int code) {
        for (DisconnectionCode disconnection : values()) {
            if (disconnection.code == code) return disconnection;
        }
        return UNKNOWN;
    }

}
