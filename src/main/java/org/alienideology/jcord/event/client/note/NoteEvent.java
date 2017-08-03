package org.alienideology.jcord.event.client.note;

import org.alienideology.jcord.event.client.ClientEvent;
import org.alienideology.jcord.internal.object.client.Client;
import org.alienideology.jcord.internal.object.client.Note;

/**
 * @author AlienIdeology
 */
public class NoteEvent extends ClientEvent {

    private final Note note;

    public NoteEvent(Client client, int sequence, Note note) {
        super(client, sequence);
        this.note = note;
    }

    public Note getNote() {
        return note;
    }
}
