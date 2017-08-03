package org.alienideology.jcord.handle.managers;

import org.alienideology.jcord.handle.client.IClientObject;

/**
 * IClientManager - A manager used to manage the Discord client.
 *
 * @author AlienIdeology
 */
public interface IClientManager extends IClientObject {

    /**
     * Modify a note for a user.
     * To remove a note, simple pass empty or {@code null} note as the parameter.
     *
     * @exception org.alienideology.jcord.internal.exception.ErrorResponseException
     *          If the user id provided is not valid.
     * @see org.alienideology.jcord.internal.gateway.ErrorResponse#UNKNOWN_USER
     *
     * @param userId The user to modify note.
     * @param note The note.
     */
    void modifyNote(String userId, String note);

}
