package org.alienideology.jcord.event.client.note;

import org.alienideology.jcord.event.client.ClientEvent;
import org.alienideology.jcord.handle.client.IClient;
import org.alienideology.jcord.handle.client.INote;

/**
 * @author AlienIdeology
 */
public class NoteUpdateEvent extends ClientEvent {

    private final INote newNote;
    private final String oldContent;

    public NoteUpdateEvent(IClient client, int sequence, INote newNote, String oldContent) {
        super(client, sequence);
        this.newNote = newNote;
        this.oldContent = oldContent;
    }

    public INote getNote() {
        return newNote;
    }

    public String getNewContent() {
        return newNote.getContent();
    }

    public String getOldContent() {
        return oldContent;
    }

}
