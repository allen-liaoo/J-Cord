package org.alienideology.jcord.event.client.note;

import org.alienideology.jcord.internal.object.client.Client;
import org.alienideology.jcord.internal.object.client.Note;

/**
 * @author AlienIdeology
 */
public class NoteAddEvent extends NoteEvent {

    public NoteAddEvent(Client client, int sequence, Note note) {
        super(client, sequence, note);
    }

}
