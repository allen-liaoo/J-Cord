package org.alienideology.jcord.internal.gateway;

/**
 * ErrorResponse - Json Error Responses
 * @author AlienIdeology
 */
public enum ErrorResponse {

    /* Unknown DiscordObject */
    UNKNOWN_ACCOUNT (10001, "Unknown account"),
    UNKNOWN_APPLICATION (10002, "Unknown application"),
    UNKNOWN_CHANNEL (10003, "Unknown channel"),
    UNKNOWN_GUILD (10004, "Unknown guild"),
    UNKNOWN_INTEGRATION (10005, "Unknown integration"),
    UNKNOWN_INVITE (10006, "Unknown invite"),
    UNKNOWN_MEMBER (10007, "Unknown member"),
    UNKNOWN_MESSAGE (10008, "Unknown message"),
    UNKNOWN_OVERWRITE (10009, "Unknown overwrite"),
    UNKNOWN_PROVIDER (10010, "Unknown provider"),
    UNKNOWN_ROLE (10011, "Unknown role"),
    UNKNOWN_TOKEN (10012, "Unknown token"),
    UNKNOWN_USER (10013, "Unknown user"),
    UNKNOWN_EMOJI (10014, "Unknown EmojiTable"),

    /* Invalid Endpoint */
    INVALID_ENDPOINT_FOR_BOT (20001, "Bots cannot use this endpoint"),
    ENDPOINT_FOR_BOT_ONLY (20002, "Only bots can use this endpoint"),

    /* Reach Max Limit */
    MAX_GUILD_REACHED (30001, "Maximum number of guilds reached (100)"),
    MAX_FRIEND_REACHED (30002, "Maximum number of friends reached (1000)"),
    MAX_PIN_REACHED (30003, "Maximum number of pins reached (50)"),
    MAX_ROLE_REACHED (30005, "Maximum number of guild roles reached (250)"),
    MAX_REACTION_REACHED (30010, "Too many reactions"),

    /* Unauthorized */
    UNAUTHORIZED (40001, "Unauthorized"),

    MISSING_ACCESS (50001, "Missing access"),
    INVALID_ACCOUNT_TYPE (50002, "Invalid account type"),
    INVALID_ACTION_IN_DM (50003, "Cannot execute action on a DM channel"),
    EMBED_DISABLED (50004, "Embed disabled"),
    EDIT_MESSAGE_BY_OTHER_USER (50005, "Cannot edit a message authored by another user"),
    EMPTY_MESSAGE (50006, "Cannot send an empty message"),
    CANNOT_SEND_MESSAGES_TO_THIS_USER (50007, "Cannot send messages to this user"),
    SEND_MESSAGES_IN_VOICE_CHANNEL (50008, "Cannot send messages in a voice channel"),
    CHANNEL_VERIFICATION_TOO_HIGH (50009, "Channel verification level is too high"),
    OAUTH2_DOES_NOT_HAVE_A_BOT (50010, "OAuth2 application does not have a bot"),
    OAUTH2_LIMIT_REACHED (50011, "OAuth2 application limit reached"),
    INVALID_OAUTH_STATE (50012, "Invalid OAuth state"),
    MISSING_PERMISSIONS (50013, "Missing permissions"),
    INVALID_AUTHENTICATION_TOKEN (50014, "Invalid authentication token"),
    NOTE_TOO_LONG (50015, "Note is too long"),
    INVALID_AMOUNT_OF_MESSAGES_TO_DELETE (50016, "Provided too few or too many messages to delete. " +
            "Must provide at least 2 and fewer than 100 messages to delete."),
    PINNED_MESSAGE_IN_DIFFERENT_CHANNEL (50019, "A message can only be pinned to the channel it was sent in"),
    INVALID_ACTION_ON_SYSTEM_MESSAGE (50021, "Cannot execute action on a system message"),
    MESSAGE_PROVIDED_TOO_OLD_TO_BULK_DELETE (50034, "A message provided was too old to bulk delete"),

    REACTION_BLOCKED (90001, "Reaction Blocked"),

    UNKNOWN (-1, "Unknown Error");

    public int key;
    public String message;

    ErrorResponse (int key, String message) {
        this.key = key;
        this.message = message;
    }

    public static ErrorResponse getByKey (int key) {
        for (ErrorResponse response : values()) {
            if (response.key == key) return response;
        }
        return UNKNOWN;
    }

}
