package org.alienideology.jcord.event.client.note;

import org.alienideology.jcord.event.client.ClientEvent;
import org.alienideology.jcord.handle.client.IClient;
import org.alienideology.jcord.handle.client.INote;

/**
 * @author AlienIdeology
 */
public class NoteEvent extends ClientEvent {

    private final INote note;

    public NoteEvent(IClient client, int sequence, INote note) {
        super(client, sequence);
        this.note = note;
    }

    public INote getNote() {
        return note;
    }
}
