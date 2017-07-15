package org.alienideology.jcord.handle.client;

import org.alienideology.jcord.handle.user.IUser;

/**
 * IRelationship - A connection between two Discord user accounts.
 *
 * @author AlienIdeology
 */
public interface IRelationship extends IClientObject {

    /**
     * Get the relationship type.
     *
     * @return The type of relationship.
     */
    IRelationship.Type getType();

    /**
     * Get the user of this relationship.
     *
     * @return The user.
     */
    IUser getUser();

    enum Type {

        NONE (0),
        FRIEND (1),
        BLOCK (2),
        FRIEND_REQUEST_IN (3),
        FRIEND_REQUEST_OUT (4),
        UNKNOWN (-1);

        public int key;

        Type(int key) {
            this.key = key;
        }

        public static Type getByKey(int key) {
            for (Type type : values()) {
                if (type.key == key) {
                    return type;
                }
            }

            return UNKNOWN;
        }

    }

}
