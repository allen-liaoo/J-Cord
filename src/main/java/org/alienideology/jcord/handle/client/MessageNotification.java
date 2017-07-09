package org.alienideology.jcord.handle.client;

/**
 * MessageNotification - The message notification level on the client side.
 *
 * @author AlienIdeology
 */
public enum MessageNotification {

    /**
     * The notification setting is locked because the guild or channel is muted.
     */
    LOCKED_BY_MUTING (3),

    /**
     * Notification Setting: All message
     */
    ALL_MESSAGES(2),

    /**
     * Notification Setting: Only mentions
     */
    ONLY_MENTIONS(1),

    /**
     * Notification Setting: Nothing
     */
    NOTHING(0),

    /**
     * Notification Setting: Unknown. Please contact a contributor if you see this.
     */
    UNKNOWN(-1);

    public int key;

    MessageNotification(int key) {
        this.key = key;
    }

    public static MessageNotification getByKey(int key) {
        for (MessageNotification notification : values()) {
            if (notification.key == key) {
                return notification;
            }
        }

        return UNKNOWN;
    }
}
