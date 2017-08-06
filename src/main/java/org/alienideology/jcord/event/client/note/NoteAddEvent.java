package org.alienideology.jcord.event.client.note;

import org.alienideology.jcord.handle.client.IClient;
import org.alienideology.jcord.handle.client.INote;

/**
 * @author AlienIdeology
 */
public class NoteAddEvent extends NoteEvent {

    public NoteAddEvent(IClient client, int sequence, INote note) {
        super(client, sequence, note);
    }

}
