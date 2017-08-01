package org.alienideology.jcord.handle.client;

import org.alienideology.jcord.handle.user.IUser;
import org.jetbrains.annotations.Nullable;

/**
 * INote - A note that the client can attach to any users. The note is only visible to the client.
 *
 * @author AlienIdeology
 */
public interface INote extends IClientObject {

    /**
     * Get the user this note is attached to.
     * The user may be null if the user is no longer visible to the client.
     *
     * @return The user.
     */
    @Nullable
    IUser getUser();

    /**
     * Get the note content.
     *
     * @return The note.
     */
    String getContent();

}
