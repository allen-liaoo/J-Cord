package org.alienideology.jcord.event.client.note;

import org.alienideology.jcord.event.client.ClientEvent;
import org.alienideology.jcord.internal.object.client.Client;
import org.alienideology.jcord.internal.object.client.Note;

/**
 * @author AlienIdeology
 */
public class NoteUpdateEvent extends ClientEvent {

    private final Note newNote;
    private final Note oldNote;

    public NoteUpdateEvent(Client client, int sequence, Note newNote, Note oldNote) {
        super(client, sequence);
        this.newNote = newNote;
        this.oldNote = oldNote;
    }

    public Note getNewNote() {
        return newNote;
    }

    public Note getOldNote() {
        return oldNote;
    }

}
