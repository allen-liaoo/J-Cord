package org.alienideology.jcord.event.handler;

import org.alienideology.jcord.event.client.note.NoteAddEvent;
import org.alienideology.jcord.event.client.note.NoteRemoveEvent;
import org.alienideology.jcord.event.client.note.NoteUpdateEvent;
import org.alienideology.jcord.handle.client.INote;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.internal.object.ObjectBuilder;
import org.alienideology.jcord.internal.object.client.Client;
import org.alienideology.jcord.internal.object.client.Note;
import org.json.JSONObject;

import java.util.Objects;

/**
 * @author AlienIdeology
 */
public class UserNoteUpdateEventHandler extends EventHandler {

    public UserNoteUpdateEventHandler(IdentityImpl identity) {
        super(identity);
    }

    @Override
    public void dispatchEvent(JSONObject json, int sequence) {
        Client client = identity.getClient();
        String id = json.getString("id");
        String newNote = json.getString("note");
        INote oldNote = client.getNote(id);

        ObjectBuilder clientBuilder = new ObjectBuilder(client);
        if (oldNote == null) {
            dispatchEvent(new NoteAddEvent(client, sequence, clientBuilder.buildNote(id, newNote)));
        } else if (newNote == null || newNote.isEmpty()) {
            Note note = clientBuilder.buildNote(id, newNote);
            client.removeNote(id);
            dispatchEvent(new NoteRemoveEvent(client, sequence, note));
        } else if (!Objects.equals(oldNote.getContent(), newNote)) {
            Note note = clientBuilder.buildNote(id, newNote);
            client.updateNote(note);
            dispatchEvent(new NoteUpdateEvent(client, sequence, note, (Note) oldNote));
        }
    }

}
