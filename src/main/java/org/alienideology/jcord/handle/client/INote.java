package org.alienideology.jcord.handle.client;

import org.alienideology.jcord.handle.ISnowFlake;
import org.alienideology.jcord.handle.user.IUser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * INote - A note that the client can attach to any users. The note is only visible to the client.
 *
 * @author AlienIdeology
 */
public interface INote extends IClientObject, ISnowFlake {

    /**
     * Get the user id of this note.
     * The user may not be visible ({@code null}), but this id is always not null.
     *
     * @return The id.
     */
    @Override
    @NotNull
    String getId();

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

    /**
     * Modify this note.
     *
     * @param note The new note content.
     */
    default void modify(String note) {
        getClient().getManager().modifyNote(getId(), note);
    }

}
